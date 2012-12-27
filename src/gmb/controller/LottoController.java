package gmb.controller;

import java.rmi.RemoteException;
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

	HashMap<Integer,Integer> zahlenmap=new HashMap<Integer,Integer>();
	HashMap<Integer,Integer> lottomap=new HashMap<Integer,Integer>();
	static int index=0;
	
	
	//-------------------------------for singletips----------------------------------
	@RequestMapping(value="/customerLotto",method=RequestMethod.GET)
	public ModelAndView customerLotto(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		lottomap.clear();
		index = 0;
		mav.setViewName("customer/tips/tip_lotto");
		mav.addObject("confirm", false);
		mav.addObject("zahlen",lottomap.keySet());
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
		if(lottomap.containsKey(zahl)){
			lottomap.remove(zahl);
			index--;
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottomap.keySet());
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		if(index<7){
			lottomap.put(zahl, zahl);
			index++;
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottomap.keySet());
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		else{
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottomap.keySet());
			mav.addObject("failureComment", "Es sind nur 7 Zahlen erlaubt.");
			mav.addObject("currentUser", GmbPersistenceManager.get(uid));
			return mav;
		}
		
	}

	@RequestMapping("/LottoConfirm")
	public ModelAndView LottoConfirm (ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		if(index == 7){
		mav.setViewName("customer/tips/tip_lotto");
		mav.addObject("confirm", true);
		
		
		
		mav.addObject("zahlen",lottomap.keySet());
		Set<Integer> zahlen=lottomap.keySet();
		Iterator<Integer> iter=zahlen.iterator();
		int tips[] = new int[lottomap.keySet().size()];
		int i=0;
		while(iter.hasNext()){
		tips[i]=iter.next().intValue();
		i++;
		}
		ReturnBox<Integer, WeeklyLottoSTT> rb = GmbFactory.createAndPurchase_WeeklyLottoSTT(currentUser);
		if(rb.var1 == 0 ){
			SingleTT ticket = rb.var2;
			int last = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
			WeeklyLottoDraw draw = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(last);
			SingleTip tip=draw.createAndSubmitSingleTip(ticket, tips).var2;
		}
		
		index = 0;
		}else{
			mav.setViewName("customer/tips/tip_lotto");
			mav.addObject("confirm", false);
			mav.addObject("zahlen",lottomap.keySet());
			mav.addObject("failureComment", "Es werden 7 Zahlen benötigt!");
		}
		mav.addObject("currentUser", currentUser);
		return mav;
	}
	
	
	//-----------------------------------------for grouptips------------------------------
	@RequestMapping("/new_weeklyLotto_GroupTip")
	public ModelAndView new_weeklyLotto_GroupTip(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		lottomap.clear();
		index = 0;
		
		mav.addObject("confirm", false);
		mav.addObject("numbers",lottomap.keySet());
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
		
		if(lottomap.containsKey(number)){
			lottomap.remove(number);
			index--;
		}
		if(index<7){
			lottomap.put(number, number);
			index++;
		}
		else{
			mav.addObject("failureComment", "Es sind nur 7 Zahlen erlaubt.");
		}
		mav.addObject("numbers",lottomap.keySet());
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
		
		mav.addObject("numbers",lottomap.keySet());
		Set<Integer> zahlen=lottomap.keySet();
		Iterator<Integer> iter=zahlen.iterator();
		int tips[] = new int[lottomap.keySet().size()];
		int i=0;
		while(iter.hasNext()){
		tips[i]=iter.next().intValue();
		i++;
		}
		int last = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		WeeklyLottoDraw draw = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(last);
		Group group = GmbPersistenceManager.getGroup(groupName);
		if(currentGroup.getGroupAdmin().equals(currentUser)){
			WeeklyLottoGroupTip wlgt = GmbFactory.new_WeeklyLottoGroupTip(draw, group, 1, 1);
		
			ReturnBox<Integer, WeeklyLottoSTT> rb = GmbFactory.createAndPurchase_WeeklyLottoSTT(currentUser);
			if(rb.var1 == 0 ){
				SingleTT ticket = rb.var2;
//				SingleTip tip=draw.createAndSubmitSingleTip(ticket, tips).var2;
		
		
				LinkedList<int[]> cus_tipTips = new LinkedList<int[]>();
				cus_tipTips.add(tips);
		
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

			mav.addObject("numbers",lottomap.keySet());
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
		int result[] = new int[]{n1,n2,n3,n4,n5,n6,en,sn};
		for(int j = 0; j < 8; j++){
			if(result[j]==0){
				mav.setViewName("notary/notary");
				mav.addObject("failureComment", "Die Zahl an der Stelle "+j+" wurde nicht eingetragen");
				mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
				return mav;
			}
		}
		for(int l = 0; l < 7; l++){
			if((result[l] < 1) || (result[l] > 49)){
				mav.setViewName("notary/notary");
				mav.addObject("failureComment", "Die Zahl an der Stelle "+l+" war nicht zwischen 1 und 49");
				mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
				return mav;
			}
		}
		if((result[7] < 1) || (result[7] > 9)){
			mav.setViewName("notary/notary");
			mav.addObject("failureComment", "Die Superahl war nicht zwischen 1 und 9");
			mav.addObject("draw", GmbPersistenceManager.get(WeeklyLottoDraw.class, id));
			return mav;
		}
		for(int i = 0; i < 7; i++){
			for(int k = i+1; k < 7; k++){
				if(result[i] == result[k]){
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
