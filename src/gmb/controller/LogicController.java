package gmb.controller;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;

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
		

	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpSession session,ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("password") String pw) {
		Member user = GmbPersistenceManager.get(uid);
		if(user != null) {
			if(user.verifyPassword(pw)) {
				GmbPersistenceManager.login(user, session);
				Capability adminCAP = new Capability("admin");
				Capability employeeCAP = new Capability("employee");
				System.out.println(user.getCapabilities());
				if(user.hasCapability(adminCAP)){
					mav.setViewName("employee/employee");
				}	
				else if(user.hasCapability(employeeCAP)){
						mav.setViewName("employee/employee");
				}	
				else{
					mav.setViewName("customer/customer");
					}		
				mav.addObject("currentUser",user);
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
			@RequestParam("city") String city) {
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
			mav.addObject("uidFail", "fail");
			mav.addObject("fail", "- Fehler");
			mav.addObject("comment", new String("Die UID wird schon verwendet !"));
		}

			
		else if(this.testeEingabeFehler(vname,nname,uid.getIdentifier(), password,email,street,hNumber,plz,city)){
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
			mav.addObject("fail", "- Fehler");
			mav.addObject("comment", new String("Alle Felder fuellen !!!"));
		}
		
		else{
			Adress stdAdress = new Adress(street, hNumber, plz, city);
			MemberData stdData = new MemberData(vname, nname, new DateTime(1970,10,16,0,0), "0735643", email, stdAdress);
			LotteryBankAccount stdbankacc = new LotteryBankAccount(new RealAccountData("0303003", "0340400"));
			Customer user = new Customer(uid.toString(), password, stdData,stdbankacc);
			Lottery.getInstance().getMemberManagement().addMember(user);
			mav.setViewName("redirect:/");
		}
		return mav;
		
	}
	private boolean testeEingabeFehler(
			String vname,String nname,String uid,String password,String email,
			String street,String hNumber,String plz,String city){
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
		return erg;
		}

}
