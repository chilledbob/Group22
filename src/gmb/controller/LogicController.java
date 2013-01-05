package gmb.controller;

import java.io.UnsupportedEncodingException;

import gmb.model.CDecimal;
import gmb.model.GmbDecoder;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;
import gmb.model.request.ExternalTransactionRequest;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.salespointframework.core.user.Capability;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;




@Controller
public class LogicController {
		
	
	@RequestMapping(value="/editUser",method=RequestMethod.GET)
	public ModelAndView editUser(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Member currentMember = GmbPersistenceManager.get(uid);
		if(currentMember.getType().name().equals("Customer")){
			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
			mav.addObject("currentUser", currentUser);
			mav.addObject("comment", "Führen Sie Ihre Änderungswünsche im entsprechenden Feld aus !");  
			mav.setViewName("customer/accountStuff/editCustomerUserData");
		}
		else if(currentMember.getType().name().equals("Notary")){
			mav.setViewName("editNotary");
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		}
		else{
			mav.setViewName("editEmployee");
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		}	
		
		return mav;	
	}
	
	@RequestMapping(value="/changeCustomerUserData",method=RequestMethod.POST)
	public ModelAndView changeCustomer(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("email") String email,
			@RequestParam("street") String street,
			@RequestParam("hNumber") String hNumber,
			@RequestParam("plz") String plz,
			@RequestParam("city") String city) {
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		boolean changeTest = false;
		if(!vname.equals(currentUser.getMemberData().getFirstName())) {changeTest = true;} 
		if(!nname.equals(currentUser.getMemberData().getLastName())) {changeTest = true;}  
		if(!email.equals(currentUser.getMemberData().getEMail())) {changeTest = true;} 
		if(!street.equals(currentUser.getMemberData().getAdress().getStreetName())) {changeTest = true;} 
		if(!hNumber.equals(currentUser.getMemberData().getAdress().getHouseNumber())) {changeTest = true;}  
		if(!plz.equals(currentUser.getMemberData().getAdress().getPostCode())) {changeTest = true;} 
		if(!city.equals(currentUser.getMemberData().getAdress().getTownName())) {changeTest = true;}  
		
		if(changeTest){
			Adress newAdress = GmbFactory.new_Adress(street,hNumber,plz,city);
			MemberData newMemberData = GmbFactory.new_MemberData(vname,nname, currentUser.getMemberData().getBirthDate(),currentUser.getMemberData().getPhoneNumber(), email, newAdress);
			currentUser.sendDataUpdateRequest(newMemberData, "Hello, please accept my update. Thanks.").accept();
			mav.addObject("comment","Ihr Änderungswunsch wird von einem Mitarbeiter bearbeitet.");
		}else{
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
			
		mav.addObject("currentUser", currentUser);
		mav.setViewName("customer/accountStuff/editCustomerUserData");
		return mav;
	}
	
	@RequestMapping(value="/editCustomerPassword",method=RequestMethod.GET)
	public ModelAndView editCustomerPassword(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		mav.addObject("currentUser", currentUser);
		mav.addObject("comment", "Bitte geben Sie Ihr altes und Ihr neues Passwort ein !");  
		mav.setViewName("customer/accountStuff/editCustomerPassword");
		return mav;
	}

	@RequestMapping(value="/changeCustomerPassword",method=RequestMethod.POST)
	public ModelAndView changeCustomerPassword(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		if((oldPassword == "" || newPassword == "" )){
			mav.addObject("comment", "Sie müssen alle Felder Füllen !");
		}
		else if(currentUser.verifyPassword(oldPassword)){
			currentUser.changePassword(newPassword);
			GmbPersistenceManager.update(currentUser);
			mav.addObject("comment", "Ihr Passwort wurde geändert.");
		}
		else{mav.addObject("comment", "Sie haben ein falsches Passwort eingegeben !");}
			
		mav.addObject("currentUser", currentUser);
		mav.setViewName("customer/accountStuff/editCustomerPassword");
		return mav;
	}
	
	@RequestMapping(value="/editCustomerRealAccount",method=RequestMethod.GET)
	public ModelAndView editCustomerRealAccount(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException {
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);		
		mav.addObject("currentUser", currentUser);
		mav.addObject("comment", "Führen Sie Ihre Änderungswünsche im entsprechenden Feld aus !");
		mav.setViewName("customer/accountStuff/editCustomerRealAccount");
		return mav;
	}
	
	@RequestMapping(value="/changeCustomerRealAccount",method=RequestMethod.POST)
	public ModelAndView changeCustomerRealAccount(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("newBankCode") String newBankCode,
			@RequestParam("newAccountNumber") String newAccountNumber) {
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		boolean changeTest = false;
		if(!newBankCode.equals(currentUser.getBankAccount().getRealAccountData().getBankCode())){changeTest=true;}
		if(!newAccountNumber.equals(currentUser.getBankAccount().getRealAccountData().getAccountNumber())){changeTest=true;}
		System.out.println(changeTest);
		
		if((newBankCode == "" || newAccountNumber == "" )){
			mav.addObject("comment", "Sie müssen alle Felder ausüllen !");
		}
		else if(changeTest){
			RealAccountData rad = GmbFactory.new_RealAccountData(newBankCode, newAccountNumber);
			currentUser.getBankAccount().sendDataUpdateRequest(GmbFactory.new_RealAccountData(newBankCode, newAccountNumber), "Bitte aendern.").accept();
			mav.addObject("comment","Ihr Änderungswunsch wird von einem Mitarbeiter bearbeitet.");
		} 
		else if(!changeTest){
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
		
		mav.addObject("currentUser", currentUser);
		mav.setViewName("customer/accountStuff/editCustomerRealAccount");
		return mav;
	}
	
	@RequestMapping(value="/cancelEditingCustomer",method=RequestMethod.GET)
	public ModelAndView cancelEditingCustomer(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		mav.setViewName("customer/customer");
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/bankingCustomer",method=RequestMethod.GET)
	public ModelAndView bankingCustomer(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		//LotteryBankAccount acc = (LotteryBankAccount) GmbPersistenceManager.get(LotteryBankAccount.class, currentUser.getBankAccount().getId());
		mav.setViewName("customer/accountStuff/bankingCustomer");
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/loadingBankAccount",method=RequestMethod.POST)
	public ModelAndView loadingBankAccount(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("load") String load){

		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		ExternalTransactionRequest currentRequest = currentUser.getBankAccount().sendExternalTransactionRequest(new CDecimal(load), "druff").var2;
		currentRequest.accept();
	//	System.out.println(currentUser.getBankAccount().getCredit().toString());
		mav.setViewName("bankingCustomer");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/chargingBankAccount",method=RequestMethod.POST)
	public ModelAndView chargingBankAccount(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("load") String load){
		System.out.println("----loadingBankAccount----");
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		ExternalTransactionRequest currentRequest = currentUser.getBankAccount().sendExternalTransactionRequest(new CDecimal("-"+load), "druff").var2;
		currentRequest.accept();
		mav.setViewName("bankingCustomer");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpSession session,ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("password") String pw) {
		Member user = GmbPersistenceManager.get(uid);
		
		if(uid.toString() == ""){
			mav.addObject("failureText", "Bitte geben Sie ihren Nutzernamen ein !");	
			mav.addObject("uidInput", uid.toString());
			mav.setViewName("index");
			return mav;
		}
		
		if(user != null) {
			if(pw==""){
				mav.addObject("failureText", "Bitte geben Sie ein Passwort ein !");	
				mav.addObject("uidInput", uid.toString());
				mav.setViewName("index");
				return mav;
			}
			if(user.verifyPassword(pw)) {
				GmbPersistenceManager.login(user, session);
				Capability adminCAP = new Capability("admin");
				Capability employeeCAP = new Capability("employee");
				Capability customerCAP = new Capability("customer");
				if(user.hasCapability(adminCAP)){
					mav.setViewName("employee/employee");
				}
				else if(user.hasCapability(employeeCAP)){
					mav.setViewName("employee/employee");
				}
				else if(user.hasCapability(customerCAP)){
					mav.setViewName("customer/customer");
				}
				else{
					mav.setViewName("notary/notary");
					int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
					mav.addObject("draw", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
					mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
					}		
				mav.addObject("currentUser",user);
				return mav;
			}
			else{
				mav.setViewName("index");
				mav.addObject("failureText", "Das eingegebene Passwort ist falsch !");	
				mav.addObject("uidInput", user.getIdentifier().toString());
				return mav;
			}
		} 
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		GmbPersistenceManager.logout(session);
		return "redirect:/";
	}	
	
	@RequestMapping(value="/register", method=RequestMethod.GET)
	public ModelAndView register(ModelAndView mav) {
		mav.addObject("vname", "");
		mav.addObject("nname", "");
		mav.addObject("password", "");
		mav.addObject("uid", "");
		mav.addObject("email", "");
		mav.addObject("street", "");
		mav.addObject("hNumber", "");
		mav.addObject("plz", "");
		mav.addObject("city", "");
		mav.addObject("uidFail", "true");
		mav.addObject("fail", "");
		mav.addObject("comment", "");
		mav.setViewName("register");
		return mav;
		
	}
	
	@RequestMapping(value="/createUser", method=RequestMethod.POST)
	public ModelAndView createUser(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("password") String password,
			@RequestParam("email") String email,
			@RequestParam("street") String street,
			@RequestParam("hNumber") String hNumber,
			@RequestParam("plz") String plz,
			@RequestParam("city") String city,
			@RequestParam("accountNumber") String accountNumber,
			@RequestParam("bankCode") String bankCode,
			@RequestParam("age") String age) {
		if(age.equals(new String("false"))){
			mav.setViewName("register");
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("password", password);
			mav.addObject("uid", uid);
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("fail", "- Fehler");
			mav.addObject("comment", new String("Sie müssen mindestens 18 Jahre alt sein um an Gewinnspielen teilnehmen zu können!"));
			return mav;
		}
		//Username schon vorhanden
		if(GmbPersistenceManager.get(uid)!= null){
			mav.setViewName("register"); 
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("password", password);
			mav.addObject("uid", "");
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("uidFail", "fail");
			mav.addObject("fail", "- Fehler");
			mav.addObject("comment", new String("Die UID wird schon verwendet !"));
		}
		else if(this.testeEingabeFehler(vname,nname,uid.getIdentifier(), password,email,street,hNumber,plz,city, accountNumber,bankCode)){
			mav.setViewName("register"); 
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("password", password);
			mav.addObject("uid", uid.toString());
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("fail", "- Fehler");
			mav.addObject("comment", new String("Alle Felder fuellen !!!"));
		}
		

		else{
			Adress adress = GmbFactory.new_Adress(street, hNumber, plz, city);
			MemberData memberData = GmbFactory.new_MemberData(vname, nname, new DateTime(1970,10,16,0,0), "0735643", email, adress);
			RealAccountData rad = GmbFactory.new_RealAccountData(bankCode, accountNumber);
			LotteryBankAccount lba = GmbFactory.new_LotteryBankAccount(rad);
			Customer user = new Customer(uid.toString(), password, memberData,lba);
			Lottery.getInstance().getMemberManagement().addMember(user);
			lba.setOwner(user);
			user.getBankAccount().setCredit(new CDecimal(5000));
			user.activateAccount();
			mav.setViewName("redirect:/");
		}
		return mav;
				
	}
	
	private boolean testeEingabeFehler(
		String vname,String nname,String uid,String password,String email,
		String street,String hNumber,String plz,String city, String accountNumber, String bankCode){
		Boolean erg = false;
		if(vname==""){	erg = true;}
		if(nname==""){	erg = true;}
		if(uid==""){	erg = true;}
		if(password==""){		erg = true;}
		if(email==""){	erg = true;}
		if(street==""){	erg = true;}
		if(hNumber==""){erg = true;}
		if(plz==""){	erg = true;}
		if(city==""){	erg = true;}
		if(accountNumber ==""){erg = true;}
		if(bankCode ==""){erg = true;}
		return erg;
	}

}
