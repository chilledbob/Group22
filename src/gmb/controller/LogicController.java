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
	
	private static UserIdentifier UID;
	
//------------------------------EDIT CUSTOMER-----------------------------------
	
	@RequestMapping(value="/editUser",method=RequestMethod.GET)
	public ModelAndView editUser(ModelAndView mav){
		
		Member currentMember = GmbPersistenceManager.get(UID);
		if(currentMember.getType().name().equals("Customer")){
			Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
			mav.addObject("currentUser", currentUser);
			mav.addObject("comment", "Fuehren Sie Ihre Aenderungswuensche im entsprechenden Feld aus !");  
			mav.setViewName("customer/accountStuff/editCustomerUserData");
		}
		else if(currentMember.getType().toString().equals("Notary")){
			mav.setViewName("editNotary");
			mav.addObject("currentUser", currentMember);
		}
		else{
			mav.setViewName("editEmployee");
			mav.addObject("currentUser", currentMember);
		}	
		
		return mav;	
	}
	
	@RequestMapping(value="/changeCustomerUserData",method=RequestMethod.POST)
	public ModelAndView changeCustomer(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("email") String email,
			@RequestParam("street") String street,
			@RequestParam("hNumber") String hNumber,
			@RequestParam("plz") String plz,
			@RequestParam("city") String city) {
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
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
			currentUser.sendDataUpdateRequest(newMemberData, "Hello, please accept my update. Thanks.");
			mav.addObject("comment","Ihr Änderungswunsch wird von einem Mitarbeiter bearbeitet.");
		}else{
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
			
		mav.addObject("currentUser", currentUser);
		mav.setViewName("customer/accountStuff/editCustomerUserData");
		return mav;
	}
	
	@RequestMapping(value="/editCustomerPassword",method=RequestMethod.GET)
	public ModelAndView editCustomerPassword(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		mav.addObject("currentUser", currentUser);
		mav.addObject("comment", "Bitte geben Sie Ihr altes und Ihr neues Passwort ein !");  
		mav.setViewName("customer/accountStuff/editCustomerPassword");
		return mav;
	}

	@RequestMapping(value="/changeCustomerPassword",method=RequestMethod.POST)
	public ModelAndView changeCustomerPassword(ModelAndView mav,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
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
	public ModelAndView editCustomerRealAccount(ModelAndView mav) {
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);		
		mav.addObject("currentUser", currentUser);
		mav.addObject("comment", "Führen Sie Ihre Änderungswünsche im entsprechenden Feld aus !");
		mav.setViewName("customer/accountStuff/editCustomerRealAccount");
		return mav;
	}
	
	@RequestMapping(value="/changeCustomerRealAccount",method=RequestMethod.POST)
	public ModelAndView changeCustomerRealAccount(ModelAndView mav,
			@RequestParam("newBankCode") String newBankCode,
			@RequestParam("newAccountNumber") String newAccountNumber) {
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		boolean changeTest = false;
		if(!newBankCode.equals(currentUser.getBankAccount().getRealAccountData().getBankCode())){changeTest=true;}
		if(!newAccountNumber.equals(currentUser.getBankAccount().getRealAccountData().getAccountNumber())){changeTest=true;}
		
		if((newBankCode == "" || newAccountNumber == "" )){
			mav.addObject("comment", "Sie müssen alle Felder ausfüllen !");
		}
		else if(changeTest){
			currentUser.getBankAccount().sendDataUpdateRequest(GmbFactory.new_RealAccountData(newBankCode, newAccountNumber), "Bitte ändern.").accept();
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
	public ModelAndView cancelEditingCustomer(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		mav.setViewName("customer/customer");
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
		
//------------------------------BANKING---------------------------------------
	
	@RequestMapping(value="/bankingCustomer",method=RequestMethod.GET)
	public ModelAndView bankingCustomer(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);	
		
		mav.setViewName("customer/accountStuff/bankingCustomer");
		mav.addObject("currentUser", currentUser);
		mav.addObject("comment", "Bitte geben Sie die erforderlichen Daten ein !");
		return mav;	
	}	
	
	@RequestMapping(value="/doBanking",method=RequestMethod.POST)
	public ModelAndView loadingBankAccount(ModelAndView mav,
			@RequestParam("load") String load,
			@RequestParam("bankCode") String bankCode,
			@RequestParam("mode") String mode,
			@RequestParam("accountNumber") String accountNumber){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);

		if(accountNumber.equals("") || bankCode.equals("") || load.equals("")){
			mav.addObject("comment", "Ihre Eingaben sind nicht vollständig !");
			mav.addObject("bankCode", bankCode);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("load", load);
		}
		
		if(!bankCode.equals(currentUser.getBankAccount().getRealAccountData().getBankCode()) ||
				!accountNumber.equals(currentUser.getBankAccount().getRealAccountData().getAccountNumber())){
			mav.addObject("comment", "Ihre Eingaben sind nicht korrekt !");
			mav.addObject("bankCode", bankCode);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("load", load);
		}
		
		if(bankCode.equals(currentUser.getBankAccount().getRealAccountData().getBankCode()) ||
		   accountNumber.equals(currentUser.getBankAccount().getRealAccountData().getAccountNumber())){
			
			if(load.matches("\\d+([.]{1}\\d+)?")){
				
				if(mode.equals("load")){
					ExternalTransactionRequest currentRequest = currentUser.getBankAccount().sendExternalTransactionRequest(new CDecimal(load), "druff").var2;
					currentRequest.accept();
					mav.addObject("comment", load+" € wurden aufgeladen !");
				}
				else{
					CDecimal c1 = currentUser.getBankAccount().getCredit();
					CDecimal c2 = new CDecimal(load);

					if(c1.compareTo(c2) != -1){
						ExternalTransactionRequest currentRequest = currentUser.getBankAccount().sendExternalTransactionRequest(new CDecimal("-"+load), "druff").var2;
						currentRequest.accept();
						mav.addObject("comment", load+" € wurden abgebucht !");
					}else {
						mav.addObject("comment", "Sie haben zu wenig Geld auf Ihrem Lotteriekonto !");
					}
				}
			}
			else{
				mav.addObject("comment", "Geben Sie bitte bei Betrag eine Zahl ein !");
				mav.addObject("bankCode", bankCode);
				mav.addObject("accountNumber", accountNumber); 
				mav.addObject("load", "");
			}
		}
				
		mav.setViewName("customer/accountStuff/bankingCustomer");
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	
	//geändert
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
		
		
		if(GmbPersistenceManager.get(uid) != null && user.getActivationState()) {
			if(pw==""){
				mav.addObject("failureText", "Bitte geben Sie ein Passwort ein !");	
				mav.addObject("uid", uid.toString());
				mav.setViewName("index");
				return mav;
			}
			if(user.verifyPassword(pw)) {
				
				GmbPersistenceManager.login(user, session);
				UID = uid;
				if(user.hasCapability(new Capability("admin"))){
					mav.setViewName("admin/admin");
					mav.addObject("currentUser",user);
				}
				else if(user.hasCapability(new Capability("employee"))){
					mav.setViewName("admin/admin");
					mav.addObject("currentUser",user);
				}
				else if(user.hasCapability(new Capability("customer"))){
					mav.setViewName("customer/customer");
					mav.addObject("currentUser",(Customer) user);
				}
				else{
					mav.setViewName("notary/notary");
					mav.addObject("currentUser",user);
					int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
					mav.addObject("draw", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
					}		
				return mav;
			}
			else{
				mav.setViewName("index");
				mav.addObject("failureText", "Das eingegebene Passwort ist falsch !");	
				mav.addObject("uid", uid.toString());
				return mav;
			}
		}else { mav.addObject("failureText", "Der Account ist nicht vorhanden !");}
		
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		GmbPersistenceManager.logout(session);
		UID = null;
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
		mav.addObject("comment", "Bitte geben Sie ihre Daten ein.");
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
			@RequestParam("age") boolean age) throws UnsupportedEncodingException {
		
		if(!age){
			mav.setViewName("register"); 
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("uid", uid.toString());
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("comment", new String("Sie müssen mindestens 18 Jahre sein!"));
			return mav;
		}
		
		//Username schon vorhanden
		if(GmbPersistenceManager.get(uid)!= null){
			mav.setViewName("register"); 
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("uid", "");
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("comment", "Die UID wird schon verwendet !");
		}

		else if(this.testeEingabeFehler(vname,nname,uid.getIdentifier(), password,email,street,hNumber,plz,city, accountNumber,bankCode)){
			mav.setViewName("register"); 
			mav.addObject("vname", vname);
			mav.addObject("nname", nname);
			mav.addObject("uid", uid.toString());
			mav.addObject("email", email);
			mav.addObject("street", street);
			mav.addObject("hNumber", hNumber);
			mav.addObject("plz", plz);
			mav.addObject("city", city);
			mav.addObject("accountNumber", accountNumber);
			mav.addObject("bankCode", bankCode);
			mav.addObject("comment", "Sie müssen alle Felder füllen !!!");
		}
		

		else{
			Adress adress = GmbFactory.new_Adress(street, hNumber, plz, city);
			MemberData memberData = GmbFactory.new_MemberData(vname, nname, new DateTime(1970,10,16,0,0), "0735643", email, adress);
			RealAccountData rad = GmbFactory.new_RealAccountData(bankCode, accountNumber);
			LotteryBankAccount lba = GmbFactory.new_LotteryBankAccount(rad);
			Customer user = new Customer(uid.toString(), password, memberData,lba);
			Lottery.getInstance().getMemberManagement().addMember(user);
			lba.setOwner(user);
			user.getBankAccount().setCredit(new CDecimal(0));
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
