package gmb.controller;

import gmb.model.GmbPersistenceManager;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

	

@Controller	
	public class CustomerController {
	

//-------------------------------------Tipps---------------------------------------------------	

	@RequestMapping(value="/customerTipManagement",method=RequestMethod.GET)
	public ModelAndView customerTipManagement(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.setViewName("customer/tips/tip_navigation");
		return mav;
	}
	
	@RequestMapping(value="/customerTips",method=RequestMethod.GET)
	public ModelAndView customerTips(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/tips/tip_customerTips");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/createSingleTip",method=RequestMethod.GET)
	public ModelAndView createSingleTip(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/tips/tip_createSingleTip");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/createPermaTip",method=RequestMethod.GET)
	public ModelAndView createPermaTip(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/tips/tip_createPermaTip");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
	
//-------------------------------------Groups---------------------------------------------------	
	
	@RequestMapping(value="/customerGroups",method=RequestMethod.GET)
	public ModelAndView customerGroups(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/groups/groups_navigation");
		return mav;
	}
	
	@RequestMapping(value="/myGroups",method=RequestMethod.GET)
	public ModelAndView myGroups(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/groups/groups_myGroups");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/allGroups",method=RequestMethod.GET)
	public ModelAndView allGroups(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/newGroup",method=RequestMethod.GET)
	public ModelAndView newGroup(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/groups/groups_newGroup");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
}