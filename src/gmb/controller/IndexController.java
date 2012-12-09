package gmb.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.joda.time.DateTime;
import org.salespointframework.core.database.Database;
import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.user.Admin;
import gmb.model.user.Adress;
import gmb.model.user.GroupManagement;
import gmb.model.user.Member;
import gmb.model.user.MemberData;
import gmb.model.user.MemberManagement;

	

@Controller	
	public class IndexController {
		
		@RequestMapping("/")
	    public ModelAndView index() {
			ModelAndView mav = new ModelAndView();

			mav.addObject("userListe", Lottery.getInstance().getMemberManagement().getMembers());
			mav.setViewName("index");
			return mav;
	    }
		

		@RequestMapping("/index")
		public String index2() {
			
			Adress a = new Adress("m","n","o","p");
			DateTime d = new DateTime();
			MemberData md = new MemberData("q","j",d,"k","l",a);
			Admin user = new Admin("bobob","pw",md);

			GmbPersistenceManager.add(a);
			GmbPersistenceManager.add(md);
			
			Lottery.getInstance().getMemberManagement().addMember(user);
			
			GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
			
			return "index";
		}
		
		@RequestMapping("/home")
		public ModelAndView home() {
			ModelAndView mav = new ModelAndView();
			mav.addObject("userListe", Lottery.getInstance().getMemberManagement().getMembers());
			mav.setViewName("index");
			return mav;
	    }
		
		
		
		
	}


