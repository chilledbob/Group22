package gmb.controller;

import javax.servlet.http.HttpSession;

import gmb.model.member.*;

import org.salespointframework.core.user.Capability;
import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class LogicController {
		
	private final PersistentUserManager pManager = new PersistentUserManager();
	
		@RequestMapping(value="/newUser/makeUser",method=RequestMethod.POST)
		public ModelAndView makeUser(  
			 @RequestParam("vname") String vname,
	         @RequestParam("nname") String nname,
	         @RequestParam("uid") UserIdentifier uid,
	         @RequestParam("password") String pw) 
			 {
				ModelAndView modelAndView = new ModelAndView();
				if(pManager.contains(uid)){
					modelAndView.setViewName("/newUser"); 
					modelAndView.addObject("vorname", vname);
					modelAndView.addObject("nachname", nname);
					modelAndView.addObject("password", pw);
					modelAndView.addObject("uid", uid);
					modelAndView.addObject("uidFail", "fail");
					modelAndView.addObject("fail", "- Fehler");
					modelAndView.addObject("comment", new String("Die UID wird schon verwendet !"));
				}
	
					
				else if(this.testeEingabeFehler(vname, nname, uid.getIdentifier(), pw)){
					modelAndView.setViewName("/newUser"); 
					modelAndView.addObject("vorname", vname);
					modelAndView.addObject("nachname", nname);
					modelAndView.addObject("password", pw);
					modelAndView.addObject("uid", uid);
					modelAndView.addObject("fail", "- Fehler");
					modelAndView.addObject("comment", new String("Alle Felder füllen !!!"));
				}
				
				else{
				//Customer user = new Customer(uid, pw, vname,nname);
				//user.addCapability(new Capability("customer"));
				//pManager.add(user);	
				modelAndView.setViewName("redirect:/");
				//modelAndView.addObject("userListe", pManager.find(MyUser.class));
				}
				return modelAndView;
	}

		
			
	@RequestMapping(value="/removeUser",method=RequestMethod.GET)
	public ModelAndView removeUser(	
			  @RequestParam("uid") UserIdentifier uid) {
		ModelAndView modelAndView = new ModelAndView();
		pManager.remove(uid);
		modelAndView.setViewName("prototype");
		modelAndView.addObject("userListe", pManager.find(Member.class));
		return modelAndView;
	}

		
	@RequestMapping(value="/editUser",method=RequestMethod.GET)
	public ModelAndView edit(
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("scope") String scope) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("edit");
		modelAndView.addObject("currentUser",pManager.get(Member.class, uid));
		modelAndView.addObject("scope", scope);
		modelAndView.addObject("userID",uid);
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="/customerChangeUser",method=RequestMethod.POST)
	public ModelAndView customerChangeUser(
			@RequestParam("vname") String newVname,
	         @RequestParam("nname") String newNname,
	         @RequestParam("uid") UserIdentifier uid) 
			 {
		//String confirm = "Ihre Anfrage wurde entgegengenommen...";
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("customer");
		modelAndView.addObject("currentUser",pManager.get(Customer.class, uid));
		//modelAndView.addObject("userID",uid);
		//modelAndView.addObject("confirm",confirm);
		modelAndView.addObject("scope", "Customer");
		return modelAndView;
	}
	
	@RequestMapping(value="/changeUser",method=RequestMethod.POST)
	public ModelAndView changeUser(
			@RequestParam("vname") String newVname,
	         @RequestParam("nname") String newNname,
	         @RequestParam("uid") UserIdentifier uid,
	         @RequestParam("scope") String scope) {
		Member tempUser = pManager.get(Member.class, uid);
		//tempUser.setVorname(newVname);
		//tempUser.setNachname(newNname);
		pManager.update(tempUser);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("stuffMember");
		modelAndView.addObject("currentUser", tempUser);
		modelAndView.addObject("scope", scope);
		return modelAndView;
	}
	
	
	
	@RequestMapping("/newUser")
	public ModelAndView newUser() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("newUser");
		modelAndView.addObject("vorname", "");
		modelAndView.addObject("nachname", "");
		modelAndView.addObject("password", "");
		modelAndView.addObject("uid", "");
		modelAndView.addObject("uidFail", "true");
		modelAndView.addObject("fail", "");
		modelAndView.addObject("comment", "");
		return modelAndView;
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public ModelAndView login(HttpSession session,ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("password") String pw) {
		PersistentUser user = pManager.get(PersistentUser.class, uid);
		if(user != null) {
			if(user.verifyPassword(pw)) {
				pManager.login(user, session);
				Member foo = pManager.get(Member.class, uid);
				Capability capManager = new Capability("manager");
				//Capability capCustomer = new Capability("customer");
				Capability capStuff = new Capability("stuffMember");
				System.out.println(session.getAttributeNames().toString());
				
				if(foo.hasCapability(capManager)){
					mav.setViewName("user/employee");
					mav.addObject("sub_navi_aktiv", false);
				}	
					else if(foo.hasCapability(capStuff)){
						mav.setViewName("user/employee");
						mav.addObject("sub_navi_aktiv", false);
				}	
					else{mav.setViewName("user/customer");
					}		
				mav.addObject("currentUser",foo);
				

				return mav;
			}
		} 
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		System.out.println("test");
		pManager.logout(session);
		return "redirect:/";
	}
	
	@RequestMapping(value="/redirectCustomer",method=RequestMethod.GET)
	public ModelAndView redirectCustomer(@RequestParam("uid") UserIdentifier uid) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("customer");
		modelAndView.addObject("currentUser", pManager.get(Customer.class, uid));
		modelAndView.addObject("scope", "Customer");
		return modelAndView;
	}
	
	
	@RequestMapping(value="/redirectStuff",method=RequestMethod.GET)
	public ModelAndView redirectStuff(
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("scope")String scope) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("stuffMember");
		modelAndView.addObject("currentUser", pManager.get(Employee.class, uid));
		modelAndView.addObject("scope", scope);
		return modelAndView;
	}
	
	private boolean testeEingabeFehler(String vname, String nname, String uid, String pw){
		Boolean erg = false;
		if(vname==""){	erg = true;}
		if(nname==""){	erg = true;}
		if(uid==""){	erg = true;}
		if(pw==""){		erg = true;}
		return erg;
		}
	
	
}
