package gmb.controller;
import gmb.model.member.*;

import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

	

@Controller	
	public class EmployeeController {
	

	private final PersistentUserManager pManager = new PersistentUserManager();
		
	
	@RequestMapping(value="/employeeNavigation",method=RequestMethod.GET)
	public ModelAndView employeeNavigation(
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("top_navi_name") String topNaviName,
			@RequestParam("sub_navi") String subNavi,
			@RequestParam("sub_navi_name") String subNaviName,
			@RequestParam("content") String content,
			@RequestParam("content_active") Boolean contentActive){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("user/employee");
		Member user = pManager.get(Member.class, uid);
		modelAndView.addObject("currentUser",user);
		modelAndView.addObject("sub_navi_active",true);
		modelAndView.addObject("top_navi_name",topNaviName);
		modelAndView.addObject("sub_navi",subNavi);
		modelAndView.addObject("sub_navi_name",subNaviName);
		modelAndView.addObject("content",content);
		modelAndView.addObject("content_active",contentActive);
		return modelAndView;
	}
}