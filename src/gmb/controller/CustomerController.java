package gmb.controller;
import java.util.LinkedList;
import java.util.List;

import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.request.RequestState;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import org.salespointframework.core.user.PersistentUserManager;
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
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		mav.setViewName("customer/tips/tip_customerTips");
		
		LinkedList<WeeklyLottoSTT> weeklySTTList = new LinkedList<WeeklyLottoSTT>();
		LinkedList<TotoSTT> totoSTTList = new LinkedList<TotoSTT>();
		LinkedList<DailyLottoSTT> dailyLottoSTTList = new LinkedList<DailyLottoSTT>();
		
		for(WeeklyLottoSTT wLSTT : currentCustomer.getWeeklyLottoSTTs()){
			if(!wLSTT.getTip().getDraw().getEvaluated())
				weeklySTTList.add(wLSTT);
		}
		for(TotoSTT tSTT : currentCustomer.getTotoSTTs()){
			if(!tSTT.getTip().getDraw().getEvaluated())
				totoSTTList.add(tSTT);
		}
		for(DailyLottoSTT dLSTT : currentCustomer.getDailyLottoSTTs()){
			if(!dLSTT.getTip().getDraw().getEvaluated())
				dailyLottoSTTList.add(dLSTT);
		}
		//System.out.println(weeklySTTList.get(0).getTip().getTip().length);
		mav.addObject("weeklySTTList", (weeklySTTList.size() > 0) ? weeklySTTList : null);
		mav.addObject("totoSTTList", (totoSTTList.size() > 0) ? totoSTTList : null );
		mav.addObject("dailySTTList", (dailyLottoSTTList.size() > 0) ? dailyLottoSTTList : null);
		mav.addObject("currentUser", currentCustomer);
		return mav;	
	}	
	
//-------------------------------------Groups---------------------------------------------------	
	
	@RequestMapping(value="/customerGroups",method=RequestMethod.GET)
	public ModelAndView customerGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		mav.setViewName("customer/groups/groups_start");
		//---> GroupController
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
}