package gmb.controller;

import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.SingleTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import java.util.ArrayList;
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

	ArrayList<Integer> zahlen=new ArrayList<Integer>();
	
	@RequestMapping(value="/customerNumeral",method=RequestMethod.GET)
	public ModelAndView customerNumeral(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		zahlen.clear();
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		int latest = Lottery.getInstance().getTipManagement().getDailyLottoDrawings().size()-1;
		mav.addObject("draw", Lottery.getInstance().getTipManagement().getDailyLottoDrawings().get(latest));
		
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
				mav.addObject("failureComment", "Es werden 10 Zahlen für einen gültigen Tip benötigt!");
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
		
		ReturnBox<Integer, DailyLottoSTT> rb = GmbFactory.createAndPurchase_DailyLottoSTT(currentUser);
		if(rb.var1 == 0 ){
			DailyLottoSTT ticket = rb.var2;
			int last = Lottery.getInstance().getTipManagement().getDailyLottoDrawings().size()-1;
			DailyLottoDraw draw = Lottery.getInstance().getTipManagement().getDailyLottoDrawings().get(last);
			draw.createAndSubmitSingleTip(ticket, zahlen);
		}
		
		
		zahlen=new ArrayList<Integer>();
		}else{
			mav.setViewName("customer/tips/tip_numeral");
			mav.addObject("confirm", false);
			mav.addObject("zahlenliste",zahlen);			
			mav.addObject("failureComment", "Es werden 10 Zahlen für einen gültigen Tip benötigt.");
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
		
		tip.validateTip(zahlen);
		
		zahlen=new ArrayList<Integer>();
		}else{
			mav.setViewName("customer/tips/tip_numeral");
			mav.addObject("confirm", false);
			mav.addObject("zahlenliste",zahlen);			
			mav.addObject("failureComment", "Es werden 10 Zahlen für einen gültigen Tip benötigt!");
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
	
	tip.validateTip(zahlen);
	
	zahlen=new ArrayList<Integer>();
	}else{
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		mav.addObject("zahlenliste",zahlen);			
		mav.addObject("failureComment", "Es werden 10 Zahlen für einen gültigen Tip benötigt!");
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
	
	tip.validateTip(zahlen);
	
	zahlen=new ArrayList<Integer>();
	}else{
		mav.setViewName("customer/tips/tip_numeral");
		mav.addObject("confirm", false);
		mav.addObject("zahlenliste",zahlen);			
		mav.addObject("failureComment", "Es werden 10 Zahlen für einen gültigen Tip benötigt!");
	}
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	return mav;
}

//-----------------------------------------for groups-------------------------------
@RequestMapping("/new_dailyLotto_GroupTip")
public ModelAndView new_weeklyLotto_GroupTip(ModelAndView mav,
		@RequestParam("uid") UserIdentifier uid,
		@RequestParam("groupName") String groupName){
	
	mav.addObject("confirm", false);
	mav.addObject("drawType", "Nummernlotto");
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
	int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
	mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
	
	mav.setViewName("customer/tips/grouptip");
	return mav;
}

@RequestMapping("/number_Group")
public ModelAndView number_group(ModelAndView mav,
		@RequestParam("number") int number,
		@RequestParam("uid") UserIdentifier uid,
		@RequestParam("groupName") String groupName
		){
	int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
	mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	mav.addObject("drawType", "Nummernlotto");
	mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
	mav.setViewName("customer/tips/grouptip");
	mav.addObject("zahlenliste",zahlen);
	mav.addObject("confirm", false);
	if(1<=number && number<=9 && zahlen.size()<=9){
	zahlen.add(number);
	
	return mav;}
	
	else{
		if(zahlen.size()==10){		
			mav.addObject("failureComment", "Es können nur 10 Zahlen gewählt werden.");
		}
		else{
		mav.addObject("failureComment", "Es können nur Zahlen zwischen 1 und 9 getipt werden.");
		}
		return mav;
	}
}

@RequestMapping("/NumberConfirmSingle_Group")
public ModelAndView NumberConfirmSingle_Group(ModelAndView mav,
		@RequestParam("uid") UserIdentifier uid,
		@RequestParam("groupName") String groupName){
	Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
	Group currentGroup = GmbPersistenceManager.getGroup(groupName);
	mav.setViewName("customer/tips/grouptip");
	mav.addObject("drawType", "Nummernlotto");
	mav.addObject("zahlenliste",zahlen);
	
	if(zahlen.size()==10){
	mav.addObject("confirm", true);
	
	int last = Lottery.getInstance().getTipManagement().getDailyLottoDrawings().size()-1;
	DailyLottoDraw draw = Lottery.getInstance().getTipManagement().getDailyLottoDrawings().get(last);
	
	if(currentGroup.getGroupAdmin().equals(currentUser)){
		DailyLottoGroupTip dlgt = GmbFactory.new_DailyLottoGroupTip(draw, currentGroup, 1, 1);
	
		ReturnBox<Integer, DailyLottoSTT> rb = GmbFactory.createAndPurchase_DailyLottoSTT(currentUser);
		if(rb.var1 == 0 ){
			SingleTT ticket = rb.var2;	
	
			LinkedList<ArrayList<Integer>> cus_tipTips = new LinkedList<ArrayList<Integer>>();
			cus_tipTips.add(zahlen);
	
			LinkedList<TipTicket> cusWLSTTs = new LinkedList<TipTicket>();
			cusWLSTTs.add(ticket);
	
			dlgt.createAndSubmitSingleTipList(cusWLSTTs, cus_tipTips);
			
		}
	}
	
	zahlen=new ArrayList<Integer>();
	}else{
		mav.addObject("confirm", false);		
		mav.addObject("failureComment", "Es werden 10 Zahlen benötigt.");
	}
	mav.addObject("currentUser", GmbPersistenceManager.get(uid));
	return mav;
}

}

