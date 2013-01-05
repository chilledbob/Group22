package gmb.controller;

import java.rmi.RemoteException;

import gmb.model.GmbPersistenceManager;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import de.msiggi.Sportsdata.Webservices.Matchdata;
import de.msiggi.Sportsdata.Webservices.SportsdataSoap;
import de.msiggi.Sportsdata.Webservices.SportsdataSoapProxy;

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
			GmbPersistenceManager.getGroup("TrollScheiße");
			return "index";
		}
		
		@RequestMapping("/home")
		public ModelAndView home(){
			ModelAndView mav = new ModelAndView();
			mav.setViewName("home");
			return mav;
	    }
		
		
		
		
	}


