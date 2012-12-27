package gmb.controller;

import java.util.LinkedList;
import java.util.List;

import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.group.Group;
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
			@RequestParam("uid") UserIdentifier uid){
		System.out.println("----editUser----");
		Member currentUser = GmbPersistenceManager.get(uid);
		if(currentUser.getType().name().equals("Customer")){
			mav.setViewName("editCustomer");
		}
		else if(currentUser.getType().name().equals("Notary")){
			mav.setViewName("editNotary");
		}
		else{
			mav.setViewName("editEmployee");
		}	
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/changeCustomer",method=RequestMethod.POST)
	public ModelAndView changeCustomer(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("email") String email,
			@RequestParam("street") String street,
			@RequestParam("hNumber") String hNumber,
			@RequestParam("plz") String plz,
			@RequestParam("city") String city,
			@RequestParam("accountNumber") String accountNumber,
			@RequestParam("bankCode") String bankCode){
		System.out.println("----changeCustomer----");
		System.out.println("vorher "+Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests().size());
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		Adress newAdress = GmbFactory.new_Adress(street,hNumber,plz,city);
		MemberData newMemberData = GmbFactory.new_MemberData(vname,nname, currentUser.getMemberData().getBirthDate(),currentUser.getMemberData().getPhoneNumber(), email, newAdress);
		currentUser.getBankAccount().sendDataUpdateRequest(GmbFactory.new_RealAccountData(accountNumber, bankCode), "Hello, please accept my update. Thanks.").accept();
		currentUser.sendDataUpdateRequest(newMemberData, "Hello, please accept my update. Thanks.");
		System.out.println("danach "+Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests().size());
		
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.setViewName("editCustomer");
		return mav;	
	}
	
	@RequestMapping(value="/bankingCustomer",method=RequestMethod.GET)
	public ModelAndView bankingCustomer(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		System.out.println("----bankingCustomer----");
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
//		System.out.println(currentUser.getBankAccount().getCredit().toString()+"test");
		LotteryBankAccount acc = (LotteryBankAccount) GmbPersistenceManager.get(LotteryBankAccount.class, currentUser.getBankAccount().getId());
//		currentUser.getBankAccount().getRealAccountData().getAccountNumber();
//		currentUser.getBankAccount().getRealAccountData().getBankCode();
//		currentUser.getBankAccount().getCredit();
//		System.out.println(acc.getCredit().toString());
		mav.setViewName("bankingCustomer");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/loadingBankAccount",method=RequestMethod.POST)
	public ModelAndView loadingBankAccount(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("load") String load){
		System.out.println("----loadingBankAccount----");
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
		
		if(pw==""){
			mav.setViewName("index");
		}
		
		Member user = GmbPersistenceManager.get(uid);
		if(user != null) {
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
					}		
				mav.addObject("currentUser",user);
				return mav;
			}
			else{
				mav.setViewName("index");
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
			@RequestParam("bankCode") String bankCode) {
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
			RealAccountData rad = GmbFactory.new_RealAccountData(accountNumber,bankCode);
			LotteryBankAccount lba = GmbFactory.new_LotteryBankAccount(rad);
			Customer user = new Customer(uid.toString(), password, memberData,lba);
			Lottery.getInstance().getMemberManagement().addMember(user);
//			lba.setOwner(user);
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
