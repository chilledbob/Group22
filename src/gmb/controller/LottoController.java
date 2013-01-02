package gmb.controller;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.SingleTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import org.salespointframework.core.user.Capability;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LottoController {

	ArrayList<Integer> lottoliste=new ArrayList<Integer>();
	static int index=0;
	
	
	//-------------------------------for singletips----------------------------------
	@RequestMapping(value="/customerLotto",method=RequestMethod.GET)
	public ModelAndView customerLotto(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		lottoliste.clear();
		index = 0;
		mav.setViewName("customer/tips/tip_lotto");
		mav.addObject("confirm", false);
		mav.addObject("zahlen",lottoliste);
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
		return mav;	
	}
	
	
	@RequestMapping("/LottoZahl")
	public ModelAndView lottozahl(ModelAndView mav,
			@RequestParam("Zahl") int zahl,
			@RequestParam("uid") UserIdentifier uid
			)throws ServiceException, RemoteException{
		if(lottoliste.contains(new Integer(zahl))){
			lottoliste.remove(zahl);
			index--;
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottoliste);
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		if(index<7){
			lottoliste.add(zahl);
			index++;
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottoliste);
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		else{
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottoliste);
			mav.addObject("failureComment", "Es sind nur 7 Zahlen erlaubt.");
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		
	}

	@RequestMapping("/LottoConfirm")
	public ModelAndView LottoConfirm (ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("tipType") String tipType
			){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		if(index == 7){
		mav.setViewName("customer/tips/tip_lotto");
		mav.addObject("confirm", true);
		
		if(tipType.equals(new String("single"))){
			ReturnBox<Integer, WeeklyLottoSTT> rb = GmbFactory.createAndPurchase_WeeklyLottoSTT(currentUser);
			if(rb.var1 == 0 ){
				SingleTT ticket = rb.var2;
				int last = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
				WeeklyLottoDraw draw = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(last);
				SingleTip tip=draw.createAndSubmitSingleTip(ticket, lottoliste).var2;
			}
			else{
				switch(tipType){
					case "halfyear" : GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Halfyear); break;
					case "year" : GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Year); break;
					default : GmbFactory.createAndPurchase_DailyLottoPTT(currentUser, PTTDuration.Month); break;
				}
			}
		}
		index = 0;
		}else{
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("failureComment", "Es werden 7 Zahlen benötigt!");
		}
		mav.addObject("zahlen",lottoliste);
		mav.addObject("currentUser", currentUser);
		return mav;
	}
	
	
	//-----------------------------------------for grouptips------------------------------
	@RequestMapping("/new_weeklyLotto_GroupTip")
	public ModelAndView new_weeklyLotto_GroupTip(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		lottoliste.clear();
		index = 0;
		
		mav.addObject("confirm", false);
		mav.addObject("numbers",lottoliste);
		mav.addObject("drawType", "6 aus 49");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
		int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
		
		mav.setViewName("customer/tips/grouptip");
		return mav;
	}
	
	@RequestMapping("/LottoNumberSelectGroup")
	public ModelAndView lottoNumberSelectGroup(ModelAndView mav,
			@RequestParam("number") int number,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName
			)throws ServiceException, RemoteException{
		
		mav.setViewName("customer/tips/grouptip");
		mav.addObject("confirm", false);
		mav.addObject("drawType", "6 aus 49");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
		int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
		
		if(lottoliste.contains(number)){
			lottoliste.remove(new Integer(number));
			index--;
		}
		if(index<7){
			lottoliste.add(number);
			index++;
		}
		else{
			mav.addObject("failureComment", "Es sind nur 7 Zahlen erlaubt.");
		}
		mav.addObject("numbers",lottoliste);
		return mav;
	}
	
	@RequestMapping("/LottoGroupConfirm")
	public ModelAndView LottoGroupConfirm (ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName
			){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		Group currentGroup = GmbPersistenceManager.getGroup(groupName);
		if(index == 7){
		mav.setViewName("customer/groups/currentGroupViewTips_Admin");
		mav.addObject("confirm", true);
		
		mav.addObject("numbers",lottoliste);
		int last = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		WeeklyLottoDraw draw = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(last);
		Group group = GmbPersistenceManager.getGroup(groupName);
		if(currentGroup.getGroupAdmin().equals(currentUser)){
			WeeklyLottoGroupTip wlgt = GmbFactory.new_WeeklyLottoGroupTip(draw, group, 1, 1);
		
			ReturnBox<Integer, WeeklyLottoSTT> rb = GmbFactory.createAndPurchase_WeeklyLottoSTT(currentUser);
			if(rb.var1 == 0 ){
				SingleTT ticket = rb.var2;
//				SingleTip tip=draw.createAndSubmitSingleTip(ticket, tips).var2;
		
		
				LinkedList<ArrayList<Integer>> cus_tipTips = new LinkedList<ArrayList<Integer>>();
				cus_tipTips.add(lottoliste);
		
				LinkedList<TipTicket> cusWLSTTs = new LinkedList<TipTicket>();
				cusWLSTTs.add(ticket);
		
				wlgt.createAndSubmitSingleTipList(cusWLSTTs, cus_tipTips);
				
			}
		}
		
		index = 0;
		}else{
			mav.setViewName("customer/tips/grouptip");
			mav.addObject("confirm", false);
			mav.addObject("drawType", "6 aus 49");
			int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
			mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));

			mav.addObject("numbers",lottoliste);
			mav.addObject("failureComment", "Es werden 7 Zahlen benötigt!");
		}
		mav.addObject("currentUser", currentUser);
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("weeklySTTList", GmbPersistenceManager.getGroup(groupName).getWeeklyLottoGroupTips());
		return mav;
	}
	
	//---------------------------------for the notary--------------------------------------
	@RequestMapping("/WeeklyLottoDrawResult")
	public ModelAndView weeklyLottoDraw(ModelAndView mav,
			@RequestParam("drawID") int id,
			@RequestParam("number1") int n1,
			@RequestParam("number2") int n2,
			@RequestParam("number3") int n3,
			@RequestParam("number4") int n4,
			@RequestParam("number5") int n5,
			@RequestParam("number6") int n6,
			@RequestParam("extraNumber") int en,
			@RequestParam("superNumber") int sn){
		WeeklyLottoDraw draw = (WeeklyLottoDraw) GmbPersistenceManager.get(WeeklyLottoDraw.class, id);
		ArrayList<Integer> result = new ArrayList<Integer>(8);
		result.add(n1);
		result.add(n2);
		result.add(n3);
		result.add(n4);
		result.add(n5);
		result.add(n6);
		result.add(en);
		result.add(sn);
		for(int j = 0; j < 8; j++){
			if(result.get(j)==0){
				mav.setViewName("notary/notary");
				mav.addObject("failureComment", "Die Zahl an der Stelle "+j+" wurde nicht eingetragen");
				mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
				return mav;
			}
		}
		for(int l = 0; l < 7; l++){
			if((result.get(l) < 1) || (result.get(l) > 49)){
				mav.setViewName("notary/notary");
				mav.addObject("failureComment", "Die Zahl an der Stelle "+l+" war nicht zwischen 1 und 49");
				mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
				return mav;
			}
		}
		if((result.get(7) < 1) || (result.get(7) > 9)){
			mav.setViewName("notary/notary");
			mav.addObject("failureComment", "Die Superahl war nicht zwischen 1 und 9");
			mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
			return mav;
		}
		for(int i = 0; i < 7; i++){
			for(int k = i+1; k < 7; k++){
				if(result.get(i) == result.get(k)){
					mav.setViewName("notary/notary");
					mav.addObject("failureComment", "Die Zahl an der Stelle "+i+" war gleich der Zahl an der stelle "+k);
					mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
					return mav;
				}
			}
		}
		if(draw.evaluate(result)){
			mav.setViewName("notary/done");
			WeeklyLottoDraw nextDraw = GmbFactory.new_WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
		}
		else{
			mav.setViewName("notary/notary");
			mav.addObject("failureComment", "hat nicht geklappt :(");
		}
		return mav;
	}
	
	
}
