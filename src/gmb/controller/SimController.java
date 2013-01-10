package gmb.controller;

import gmb.model.Lottery;
import gmb.model.Timer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimController {
	
	@RequestMapping("/SimControllerMi")
	public ModelAndView plusMinute(ModelAndView mav){
		Lottery.getInstance().getTimer().addMinutes(5);
		mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerH")
	public ModelAndView plusHoure(ModelAndView mav){
		Lottery.getInstance().getTimer().addHours(1);
		mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerD")
	public ModelAndView plusDay(ModelAndView mav){
		Lottery.getInstance().getTimer().addDays(1);
		mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerW")
	public ModelAndView plusWeek(ModelAndView mav){
		Lottery.getInstance().getTimer().addWeeks(1);
		mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerMo")
	public ModelAndView plusMonth(ModelAndView mav){
		Lottery.getInstance().getTimer().addMonths(1);
		mav.addObject("time", Lottery.getInstance().getTimer().getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}

}
