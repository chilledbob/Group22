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
		Timer timer = Lottery.getInstance().getTimer();
		timer.addMinutes(5);
		mav.addObject("time", timer.getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerH")
	public ModelAndView plusHoure(ModelAndView mav){
		Timer timer = Lottery.getInstance().getTimer();
		timer.addHours(1);
		mav.addObject("time", timer.getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerD")
	public ModelAndView plusDay(ModelAndView mav){
		Timer timer = Lottery.getInstance().getTimer();
		timer.addDays(1);
		mav.addObject("time", timer.getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerW")
	public ModelAndView plusWeek(ModelAndView mav){
		Timer timer = Lottery.getInstance().getTimer();
		timer.addWeeks(1);
		mav.addObject("time", timer.getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}
	
	@RequestMapping("/SimControllerMo")
	public ModelAndView plusMonth(ModelAndView mav){
		Timer timer = Lottery.getInstance().getTimer();
		timer.addMonths(1);
		mav.addObject("time", timer.getDateTime());
		mav.setViewName("/notary/notary");
		return mav;
	}

}
