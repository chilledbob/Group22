package gmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import gmb.model.user.Admin;
import gmb.model.user.MemberManagement;

	

@Controller	
	public class IndexController {
		
		@RequestMapping("/")
	    public ModelAndView index() {
			ModelAndView mav = new ModelAndView();
			mav.setViewName("index");
			return mav;
	    }
		

		@RequestMapping("/index")
		public String index2() {
			return "index";
		}
		
		@RequestMapping("/home")
		public ModelAndView home() {
			ModelAndView mav = new ModelAndView();
			MemberManagement mm = new MemberManagement();
			mav.addObject("userListe", mm.find(Admin.class));
			mav.setViewName("index");
			return mav;
	    }
		
		
		
		
	}


