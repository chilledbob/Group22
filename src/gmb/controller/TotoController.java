package gmb.controller;



import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.SingleTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javax.xml.rpc.ServiceException;

import org.eclipse.persistence.annotations.ReplicationPartitioning;
import org.joda.time.DateTime;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import de.msiggi.Sportsdata.Webservices.Matchdata;
import de.msiggi.Sportsdata.Webservices.SportsdataLocator;
import de.msiggi.Sportsdata.Webservices.SportsdataSoap;
import de.msiggi.Sportsdata.Webservices.SportsdataSoapProxy;



@Controller
public class TotoController {
	
	SportsdataSoap sportDataSoup = new SportsdataSoapProxy().getSportsdataSoap();
	Matchdata[] data;
	
	//@SuppressWarnings("unchecked")
	//Set<Integer> lottozahlen=lottomap.keySet();
	//Set<Integer> lottozahlen;
	
	List<String> teams=new LinkedList<String>();
	int[] tore;
	List<Integer> tips=new ArrayList<Integer>();
	List<Integer> betraege=new LinkedList<Integer>();
	List<Integer> ids=new LinkedList<Integer>();
	List<String> dates=new LinkedList<String>();
	ArrayList<FootballGameData> results=new ArrayList<FootballGameData>();
	ArrayList<TotoTip>tototiplist=new ArrayList<TotoTip>();
	
	int goid;
	String ls;
	
	
	@RequestMapping(value="/customerToto",method=RequestMethod.GET)
	public ModelAndView customerToto(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid
			){
		ls = "2012";
		int j = 0;
		LinkedList<TotoEvaluation> evaList = new LinkedList<TotoEvaluation>();
		for(TotoEvaluation evas : Lottery.getInstance().getTipManagement().getTotoEvaluations()){
			if(evas.isEvaluated())
				j++;
		}
		for(int i = j; i < 34; i++){
			evaList.add(Lottery.getInstance().getTipManagement().getTotoEvaluations().get(i));
		}
		mav.addObject("totoList", evaList);
		mav.setViewName("customer/tips/tip_toto");
		mav.addObject("confirm1", true);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
	
	
	@RequestMapping("/Matches")
	public ModelAndView matches ( // erstellt die Matches f�r die erste Bundesliga an dem Spieltag "GroupOrderID" 
								 // und dem Saison Jahr "LeagueSaison" (f�r 2012/2013 -> 2012)
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("TotoEvaID") int totoEvaId,
			ModelAndView mav){
		TotoEvaluation eva = (TotoEvaluation) GmbPersistenceManager.get(Draw.class, totoEvaId);
		mav.setViewName("customer/tips/tip_toto");
		mav.addObject("eva", eva);
		int i = 1;
		for(TotoEvaluation e : Lottery.getInstance().getTipManagement().getTotoEvaluations()){
			if(e.getId() == eva.getId())
				break;
			i++;
		}
		mav.addObject("spieltag", i);
		mav.addObject("spielliste",eva.getGameData());
		mav.addObject("confirm1", false);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
	return mav;
	}
//	
//	
//
//	
//	@RequestMapping("/TotoErgebnisse")
//	public ModelAndView totoschein ( @RequestParam("uid") UserIdentifier uid,ModelAndView mav)throws ServiceException, RemoteException{
//		Matchdata[] data=sportDataSoup.getMatchdataByLeagueSaison("bl1", "2012");
//		 DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
//		 for (Matchdata matchdata : data) {
//			 dates.add(formatter.format(matchdata.getMatchDateTime().getTime()));}
//		 mav.setViewName("Games/totoergebnisse");
//		 mav.addObject("spielliste",data);
//		 mav.addObject("dates",dates);
//		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
//		 return mav;
//		
//	}
	
	@RequestMapping("/TotoConfirm")
	public ModelAndView totoschein ( 
			@RequestParam("0") int erg0,
			@RequestParam("1") int erg1,
			@RequestParam("2") int erg2,
			@RequestParam("3") int erg3,
			@RequestParam("4") int erg4,
			@RequestParam("5") int erg5,
			@RequestParam("6") int erg6,
			@RequestParam("7") int erg7,
			@RequestParam("8") int erg8,
			@RequestParam("GroupOrderID") int groupOrderID,
			@RequestParam("uid") UserIdentifier uid,
			ModelAndView mav){
//			Integer erg0=new Integer(er0);
//			Integer erg1=new Integer(er1);
//			Integer erg2=new Integer(er2);
//			Integer erg3=new Integer(er3);
//			Integer erg4=new Integer(er4);
//			Integer erg5=new Integer(er5);
//			Integer erg6=new Integer(er6);
//			Integer erg7=new Integer(er7);
//			Integer erg8=new Integer(er8);
			
			TotoEvaluation eva = (TotoEvaluation) GmbPersistenceManager.get(Draw.class, groupOrderID);
			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
			mav.addObject("currentUser", currentUser);
			mav.setViewName("customer/tips/tip_toto");
			mav.addObject("spielliste",eva.getGameData());
			int[] tip = new int[]{erg0,erg1,erg2,erg3,erg4,erg5,erg6,erg7,erg8};
			for(int j = 0; j < 9; j++)
				tips.add(tip[j]);
			for(int i = 0; i < 9; i++){
				if((tip[i] != 0) && (tip[i] != 1) && (tip[i] != 2)){
					mav.addObject("confirm", false);
					mav.addObject("tips",tips);
					mav.addObject("failureComment", "Sie können nur 0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden) tippen! <br> (Tip an der Stelle "+i+" war nicht zwischen 0 und 2)");
					return mav;
				}
			}

				ReturnBox<Integer, TotoSTT> rb = GmbFactory.createAndPurchase_TotoSTT(currentUser);
				if(rb.var1 == 0){
					eva.createAndSubmitSingleTip(rb.var2, tip);
				}
				else{
					mav.addObject("confirm", false);
					mav.addObject("tips",tips);
					mav.addObject("failureComment", "Sie benötigen mehr Geld um ein Tipticket zu erwerben. Bitte <a href='bankingCustomer?uid="+currentUser.getIdentifier()+"'>Konto aufladen</a>!");
					return mav;
				}
				
				
				mav.addObject("confirm", true);
				mav.addObject("tips",tips);
				return mav;
	}
	
	//-----------------------------------------for groups-------------------------------
	@RequestMapping("/new_toto_GroupTip")
	public ModelAndView new_weeklyLotto_GroupTip(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		ls = "2012";
		mav.addObject("totoList", Lottery.getInstance().getTipManagement().getTotoEvaluations());
		mav.setViewName("customer/tips/grouptip");
		mav.addObject("confirm1", true);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
		mav.addObject("drawType", "Fußballtoto");
		return mav;	
	}
	
	@RequestMapping("/GroupMatches")
	public ModelAndView matchesGroup ( 
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("TotoEvaID") int totoEvaId,
			@RequestParam("groupName") String groupName,
			ModelAndView mav){
		TotoEvaluation eva = (TotoEvaluation) GmbPersistenceManager.get(Draw.class, totoEvaId);
		mav.setViewName("customer/tips/grouptip");
		mav.addObject("eva", eva);
		int i = 1;
		for(TotoEvaluation e : Lottery.getInstance().getTipManagement().getTotoEvaluations()){
			if(e.getId() == eva.getId())
				break;
			i++;
		}
		mav.addObject("spieltag", i);
		mav.addObject("spielliste",eva.getGameData());
		mav.addObject("confirm1", false);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
		mav.addObject("drawType", "Fußballtoto");
	return mav;
	}
	
	@RequestMapping("/TotoConfirmGroup")
	public ModelAndView totoConfirmGroup ( 
			@RequestParam("0") int erg0,
			@RequestParam("1") int erg1,
			@RequestParam("2") int erg2,
			@RequestParam("3") int erg3,
			@RequestParam("4") int erg4,
			@RequestParam("5") int erg5,
			@RequestParam("6") int erg6,
			@RequestParam("7") int erg7,
			@RequestParam("8") int erg8,
			@RequestParam("GroupOrderID") int groupOrderID,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName,
			ModelAndView mav){
		
			TotoEvaluation eva = (TotoEvaluation) GmbPersistenceManager.get(Draw.class, groupOrderID);
			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
			Group currentGroup = GmbPersistenceManager.getGroup(groupName);
			mav.addObject("currentUser", currentUser);
			mav.setViewName("customer/tips/grouptip");
			mav.addObject("currentGroup", currentGroup);
			mav.addObject("drawType", "Fußballtoto");
			mav.addObject("spielliste",eva.getGameData());
			int[] tip = new int[]{erg0,erg1,erg2,erg3,erg4,erg5,erg6,erg7,erg8};
			for(int j = 0; j < 9; j++)
				tips.add(tip[j]);
			for(int i = 0; i < 9; i++){
				if((tip[i] != 0) && (tip[i] != 1) && (tip[i] != 2)){
					mav.addObject("confirm", false);
					mav.addObject("tips",tips);
					mav.addObject("failureComment", "Sie können nur 0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden) tippen! <br> (Tip an der Stelle "+i+" war nicht zwischen 0 und 2)");
					return mav;
				}
			}
			if(eva.isTimeLeftUntilEvaluationForSubmission()){	
				if(currentGroup.getGroupAdmin().equals(currentUser)){
					TotoGroupTip tgt = GmbFactory.new_TotoGroupTip(eva, currentGroup, 1, 1);
				
					ReturnBox<Integer, TotoSTT> rb = GmbFactory.createAndPurchase_TotoSTT(currentUser);
					if(rb.var1 == 0 ){
						SingleTT ticket = rb.var2;				
				
						LinkedList<int[]> cus_tipTips = new LinkedList<int[]>();
						cus_tipTips.add(tip);
				
						LinkedList<TipTicket> cusTotoSTTs = new LinkedList<TipTicket>();
						cusTotoSTTs.add(ticket);
				
						tgt.createAndSubmitSingleTipList(cusTotoSTTs, cus_tipTips);
						mav.addObject("confirm", true);
						mav.addObject("tips",tips);
						return mav;
					}
				}
			}
			mav.addObject("confirm", false);
			mav.addObject("tips",tips);
			mav.addObject("failureComment", "Sie können für diesen Spieltag keine Tips mehr abgeben!");
			return mav;

	}
	

}
