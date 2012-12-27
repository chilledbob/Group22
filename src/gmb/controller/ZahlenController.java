package gmb.controller;

import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.member.Customer;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.single.DailyLottoSTT;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
@Controller
public class ZahlenController {

	List<Integer> zahlen=new LinkedList<Integer>();
	
	@RequestMapping(value="/customerNumeral",method=RequestMethod.GET)
	public ModelAndView customerNumeral(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		
		
		return mav;	
	}
	
	//monatlich, halb und ganzjährig
	
	@RequestMapping("/Zahl")
	public ModelAndView zahl(ModelAndView mav,
			@RequestParam("Zahl") int zahl1,
			@RequestParam("uid") UserIdentifier uid
			){
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		if(1<=zahl1 && zahl1<=9 && zahlen.size()<=9){
		zahlen.add(zahl1);
		
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("zahlenliste",zahlen);
		mav.addObject("confirm", false);
		return mav;}
		
		else{
			if(zahlen.size()==10){
				mav.setViewName("customer/tips/tip_numeral");
				mav.addObject("confirm", false);
				mav.addObject("zahlenliste",zahlen);			
				mav.addObject("failureComment", "Du kannst nur 10 Zahlen tippen, du Troll.");
			}
			else{
			mav.setViewName("customer/tips/tip_numeral");
			mav.addObject("confirm", false);
			mav.addObject("zahlenliste",zahlen);
			mav.addObject("failureComment", "du kannst nur Zahlen zweischen 1 und 9 eintragen");
			}
			return mav;
		}
	}
	
	@RequestMapping("/ZahlenConfirmSingle")
	public ModelAndView zahlenConfirmSingle(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		if(zahlen.size()==10){
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", true);
		mav.addObject("zahlenliste",zahlen);		
		
		DailyLottoSTT ticket = GmbFactory.createAndPurchase_DailyLottoSTT(currentUser).var2;
		DailyLottoDraw draw=GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
		
		int[] zahlena = new int[zahlen.size()];
		for(int i=0;i==zahlen.size();i++){
			zahlena[i]=zahlen.get(i);
		}
		SingleTip tip=draw.createAndSubmitSingleTip(ticket, zahlena).var2;
		
		
		zahlen=new LinkedList<Integer>();
		}else{
			mav.setViewName("customer/tips/tip_numeral");
			mav.addObject("confirm", false);
			mav.addObject("zahlenliste",zahlen);			
			mav.addObject("failureComment", "Du brauchst 10 Zahlen du Troll.");
		}
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;
	}
	

	@RequestMapping("/ZahlenConfirmPermaMonth")
	public ModelAndView zahlenConfirmSigleMonth (ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		if(zahlen.size()==10){
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", true);
		mav.addObject("zahlenliste",zahlen);		
		
		DailyLottoPTT ticket = GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Month).var2;
		DailyLottoDraw draw=GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
		DailyLottoTip tip=GmbFactory.new_DailyLottoTip(ticket, draw);
		int[] zahlena = new int[zahlen.size()];
		for(int i=0;i==zahlen.size();i++){
			zahlena[i]=zahlen.get(i);
		}
		
		tip.validateTip(zahlena);
		
		zahlen=new LinkedList<Integer>();
		}else{
			mav.setViewName("customer/tips/tip_numeral");
			mav.addObject("confirm", false);
			mav.addObject("zahlenliste",zahlen);			
			mav.addObject("failureComment", "Du brauchst 10 Zahlen du Troll.");
		}
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;
	}


@RequestMapping("/ZahlenConfirmPermaHalf")
public ModelAndView zahlenConfirmSigleHalf (ModelAndView mav,
		@RequestParam("uid") UserIdentifier uid){
	Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
	if(zahlen.size()==10){
	mav.setViewName("customer/tips/tip_numeral");
	mav.addObject("confirm", true);
	mav.addObject("zahlenliste",zahlen);		
	
	DailyLottoPTT ticket = GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Halfyear).var2;
	DailyLottoDraw draw=GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
	DailyLottoTip tip=GmbFactory.new_DailyLottoTip(ticket, draw);
	int[] zahlena = new int[zahlen.size()];
	for(int i=0;i==zahlen.size();i++){
		zahlena[i]=zahlen.get(i);
	}
	
	tip.validateTip(zahlena);
	
	zahlen=new LinkedList<Integer>();
	}else{
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		mav.addObject("zahlenliste",zahlen);			
		mav.addObject("failureComment", "Du brauchst 10 Zahlen du Troll.");
	}
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	return mav;
}

@RequestMapping("/ZahlenConfirmPermaYear")
public ModelAndView zahlenConfirmSigleYear (ModelAndView mav,
		@RequestParam("uid") UserIdentifier uid){
	Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
	if(zahlen.size()==10){
	mav.setViewName("customer/tips/tip_numeral");
	mav.addObject("confirm", true);
	mav.addObject("zahlenliste",zahlen);		
	
	DailyLottoPTT ticket = GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Year).var2;
	DailyLottoDraw draw=GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
	DailyLottoTip tip=GmbFactory.new_DailyLottoTip(ticket, draw);
	int[] zahlena = new int[zahlen.size()];
	for(int i=0;i==zahlen.size();i++){
		zahlena[i]=zahlen.get(i);
	}
	
	tip.validateTip(zahlena);
	
	zahlen=new LinkedList<Integer>();
	}else{
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		mav.addObject("zahlenliste",zahlen);			
		mav.addObject("failureComment", "Du brauchst 10 Zahlen du Troll.");
	}
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	return mav;
}
}

