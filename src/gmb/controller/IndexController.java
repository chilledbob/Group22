package gmb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

//geändert

@Controller	
	public class IndexController {
		
	@RequestMapping("/")
	public ModelAndView index() {
	ModelAndView mav = new ModelAndView();
	
	mav.addObject("failureText", "");	
	mav.addObject("uid","");
	mav.setViewName("index");
	return mav;
	}


	@RequestMapping("/index")
	public ModelAndView index2() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("failureText", "");	
		mav.addObject("uidInput","");
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping("/home")
	public ModelAndView home() {
	ModelAndView mav = new ModelAndView();
	mav.setViewName("index");
	return mav;
	}		
}
