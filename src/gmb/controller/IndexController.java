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
import gmb.model.group.GroupManagement;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.member.MemberType;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;

	

@Controller	
	public class IndexController {
		
		@RequestMapping("/")
	    public ModelAndView index() {
			ModelAndView mav = new ModelAndView();
			mav.addObject("userListe", Lottery.getInstance().getMemberManagement().getMembers());
			mav.addObject("groupList", Lottery.getInstance().getGroupManagement().getGroups());
			mav.addObject("group", GmbPersistenceManager.getGroup("The Savages"));
			mav.setViewName("index");
			return mav;
	    }
		

		@RequestMapping("/index")
		public String index2() {
			
			Adress a = new Adress("m","n","o","p");
			DateTime d = new DateTime();
			MemberData md = new MemberData("q","j",d,"k","l",a);
			Member user = new Member("bobob","pw",md,MemberType.Admin);

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


