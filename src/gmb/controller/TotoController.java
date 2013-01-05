package gmb.controller;



import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.member.Customer;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
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
		mav.setViewName("customer/tips/tip_toto");
		mav.addObject("confirm1", true);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
	
	
	@RequestMapping("/Matches")
	public ModelAndView matches ( // erstellt die Matches f�r die erste Bundesliga an dem Spieltag "GroupOrderID" 
								 // und dem Saison Jahr "LeagueSaison" (f�r 2012/2013 -> 2012)
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("GroupOrderID") int groupOrderID,
			ModelAndView mav)throws ServiceException, RemoteException{
		goid=groupOrderID;
		Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(groupOrderID, "bl1", ls);
		mav.setViewName("customer/tips/tip_toto");
		mav.addObject("goid", goid);
		mav.addObject("spielliste",data);
		mav.addObject("confirm1", false);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
	return mav;
	}
	
	

	
	@RequestMapping("/TotoErgebnisse")
	public ModelAndView totoschein ( @RequestParam("uid") UserIdentifier uid,ModelAndView mav)throws ServiceException, RemoteException{
		Matchdata[] data=sportDataSoup.getMatchdataByLeagueSaison("bl1", "2012");
		 DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
		 for (Matchdata matchdata : data) {
			 dates.add(formatter.format(matchdata.getMatchDateTime().getTime()));}
		 mav.setViewName("Games/totoergebnisse");
		 mav.addObject("spielliste",data);
		 mav.addObject("dates",dates);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		 return mav;
		
	}
	
	@RequestMapping("/TotoConfirm")
	public ModelAndView totoschein ( 
			@RequestParam("0") String er0,
			@RequestParam("1") String er1,
			@RequestParam("2") String er2,
			@RequestParam("3") String er3,
			@RequestParam("4") String er4,
			@RequestParam("5") String er5,
			@RequestParam("6") String er6,
			@RequestParam("7") String er7,
			@RequestParam("8") String er8,
			@RequestParam("GroupOrderID") int groupOrderID,
			@RequestParam("uid") UserIdentifier uid,
			ModelAndView mav)throws ServiceException, RemoteException{
			Integer erg0=new Integer(er0);
			Integer erg1=new Integer(er1);
			Integer erg2=new Integer(er2);
			Integer erg3=new Integer(er3);
			Integer erg4=new Integer(er4);
			Integer erg5=new Integer(er5);
			Integer erg6=new Integer(er6);
			Integer erg7=new Integer(er7);
			Integer erg8=new Integer(er8);
			goid = groupOrderID;
			Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
			mav.addObject("currentUser", currentUser);
			if(0<=erg0&& erg0<=2){
				tips.add(erg0);
				if(0<=erg1&& erg1<=2){
					tips.add(erg1);}
					if(0<=erg2&& erg2<=2){
						tips.add(erg2);}
						if(0<=erg3&& erg3<=2){
							tips.add(erg3);}
							if(0<=erg4&& erg4<=2){
								tips.add(erg4);}
								if(0<=erg5&& erg5<=2){
									tips.add(erg5);}
									if(0<=erg6&& erg6<=2){
										tips.add(erg6);}
										if(0<=erg7&& erg7<=2){
											tips.add(erg7);}
											if(0<=erg8&& erg8<=2){
												tips.add(erg8);}
			int a=0;
			for(int i=1;i==data.length;i++){
				
				FootballGameData footballdata=new FootballGameData(new DateTime(data[i].getMatchDateTime()),data[i].getNameTeam1(),data[i].getNameTeam2());
				results.add(footballdata);
				tore[a]=data[i].getPointsTeam1();a++;
				tore[a]=data[i].getPointsTeam2();a++;
			}
			mav.setViewName("customer/tips/tip_toto");
			mav.addObject("confirm", true);
			mav.addObject("tips",tips);
			int[] tiparray = new int[9];
			for(int i = 0; i < 9;i++){
				tiparray[i] = tips.get(i);
			}
			
			TotoSTT ticket = GmbFactory.createAndPurchase_TotoSTT(currentUser).var2;
			TotoEvaluation eva= GmbFactory.new_TotoEvaluation(new DateTime(), results) ;
			eva.createAndSubmitSingleTip(ticket, tiparray);
		//	eva.evaluate(tore);
			
//			Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
			mav.addObject("spielliste",data);
			
			return mav;}
			
			else{
				mav.setViewName("customer/tips/tip_toto");
				mav.addObject("confirm", false);
				mav.addObject("tips",tips);
				mav.addObject("currentUser", GmbPersistenceManager.get(uid));
//				Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
				mav.addObject("spielliste",data);
				mav.addObject("failureComment", "du kannst nur,0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden), eintragen");
				return mav;
			}
	}
	
	//-----------------------------------------for groups-------------------------------
	@RequestMapping("/new_toto_GroupTip")
	public ModelAndView new_weeklyLotto_GroupTip(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){		
		mav.addObject("confirm", false);

		mav.addObject("drawType", "Fußballtoto");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		mav.addObject("currentGroup", GmbPersistenceManager.getGroup(groupName));
		int latest = Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().size()-1;
		mav.addObject("drawtime", Lottery.getInstance().getTipManagement().getWeeklyLottoDrawings().get(latest));
		
		mav.setViewName("customer/tips/grouptip");
		return mav;
	}
	
	
	
	
//	@RequestMapping("/TotoConfirmSingle")
//	public ModelAndView totoscheinsingle ( 
//			@RequestParam("0") String er0,
//			@RequestParam("1") String er1,
//			@RequestParam("2") String er2,
//			@RequestParam("3") String er3,
//			@RequestParam("4") String er4,
//			@RequestParam("5") String er5,
//			@RequestParam("6") String er6,
//			@RequestParam("7") String er7,
//			@RequestParam("8") String er8,
//			@RequestParam("uid") UserIdentifier uid,
//			ModelAndView mav)throws ServiceException, RemoteException{
//			Integer erg0=new Integer(er0);
//			Integer erg1=new Integer(er1);
//			Integer erg2=new Integer(er2);
//			Integer erg3=new Integer(er3);
//			Integer erg4=new Integer(er4);
//			Integer erg5=new Integer(er5);
//			Integer erg6=new Integer(er6);
//			Integer erg7=new Integer(er7);
//			Integer erg8=new Integer(er8);
//			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
//			if(0<=erg0&& erg0<=2){
//				tips.add(erg0);
//				if(0<=erg1&& erg1<=2){
//					tips.add(erg1);}
//					if(0<=erg2&& erg2<=2){
//						tips.add(erg2);}
//						if(0<=erg3&& erg3<=2){
//							tips.add(erg3);}
//							if(0<=erg4&& erg4<=2){
//								tips.add(erg4);}
//								if(0<=erg5&& erg5<=2){
//									tips.add(erg5);}
//									if(0<=erg6&& erg6<=2){
//										tips.add(erg6);}
//										if(0<=erg7&& erg7<=2){
//											tips.add(erg7);}
//											if(0<=erg8&& erg8<=2){
//												tips.add(erg8);}
//			int a=0;
//			for(int i=1;i==data.length;i++){
//				
//				FootballGameData footballdata=new FootballGameData(new DateTime(data[i].getMatchDateTime()),data[i].getNameTeam1(),data[i].getNameTeam2());
//				results.add(footballdata);
//				tore[a]=data[i].getPointsTeam1();a++;
//				tore[a]=data[i].getPointsTeam2();a++;
//			}
//			mav.setViewName("customer/tips/tip_toto");
//			mav.addObject("confirm", true);
//			mav.addObject("tips",tips);
//			
//			int[] tipsa = new int[tips.size()];
//			for(int i=0;i==tips.size();i++){
//				tipsa[i]=tips.get(i);}
//			
//			TotoSTT ticket = GmbFactory.createAndPurchase_TotoSTT(currentUser).var2;
//			TotoEvaluation eva= GmbFactory.new_TotoEvaluation(new DateTime(data[0].getMatchDateTime()), results) ;
//			eva.createAndSubmitSingleTip(ticket, tipsa);
//		//	eva.evaluate(tore);
//			
//			Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
//			mav.addObject("spielliste",data);
//			
//			return mav;}
//			
//			else{
//				mav.setViewName("customer/tips/tip_toto");
//				mav.addObject("confirm", false);
//				mav.addObject("tips",tips);
//				Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
//				mav.addObject("spielliste",data);
//				mav.addObject("failureComment", "du kannst nur,0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden), eintragen");
//				return mav;
//			}
//	}
//	
//	
//	@RequestMapping("/TotoConfirmGroup")
//	public ModelAndView totoscheingroup ( 
//			@RequestParam("0") String er0,
//			@RequestParam("1") String er1,
//			@RequestParam("2") String er2,
//			@RequestParam("3") String er3,
//			@RequestParam("4") String er4,
//			@RequestParam("5") String er5,
//			@RequestParam("6") String er6,
//			@RequestParam("7") String er7,
//			@RequestParam("8") String er8,
//			@RequestParam("uid") UserIdentifier uid,
//			ModelAndView mav)throws ServiceException, RemoteException{
//			Integer erg0=new Integer(er0);
//			Integer erg1=new Integer(er1);
//			Integer erg2=new Integer(er2);
//			Integer erg3=new Integer(er3);
//			Integer erg4=new Integer(er4);
//			Integer erg5=new Integer(er5);
//			Integer erg6=new Integer(er6);
//			Integer erg7=new Integer(er7);
//			Integer erg8=new Integer(er8);
//			Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
//			if(0<=erg0&& erg0<=2){
//				tips.add(erg0);
//				if(0<=erg1&& erg1<=2){
//					tips.add(erg1);}
//					if(0<=erg2&& erg2<=2){
//						tips.add(erg2);}
//						if(0<=erg3&& erg3<=2){
//							tips.add(erg3);}
//							if(0<=erg4&& erg4<=2){
//								tips.add(erg4);}
//								if(0<=erg5&& erg5<=2){
//									tips.add(erg5);}
//									if(0<=erg6&& erg6<=2){
//										tips.add(erg6);}
//										if(0<=erg7&& erg7<=2){
//											tips.add(erg7);}
//											if(0<=erg8&& erg8<=2){
//												tips.add(erg8);}
//			int a=0;
//			for(int i=1;i==data.length;i++){
//				
//				FootballGameData footballdata=new FootballGameData(new DateTime(data[i].getMatchDateTime()),data[i].getNameTeam1(),data[i].getNameTeam2());
//				results.add(footballdata);
//				tore[a]=data[i].getPointsTeam1();a++;
//				tore[a]=data[i].getPointsTeam2();a++;
//			}
//			mav.setViewName("customer/tips/tip_toto");
//			mav.addObject("confirm", true);
//			mav.addObject("tips",tips);
//			
//			int[] tipsa = new int[tips.size()];
//			for(int i=0;i==tips.size();i++){
//				tipsa[i]=tips.get(i);}
//			
//			TotoSTT ticket = GmbFactory.createAndPurchase_TotoSTT(currentUser).var2;
//			TotoEvaluation eva= GmbFactory.new_TotoEvaluation(new DateTime(), results) ;
//			eva.createAndSubmitSingleTip(ticket, tipsa);
//		//	eva.evaluate(tore);
//			
//			Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
//			mav.addObject("spielliste",data);
//			
//			return mav;}
//			
//			else{
//				mav.setViewName("customer/tips/tip_toto");
//				mav.addObject("confirm", false);
//				mav.addObject("tips",tips);
//				Matchdata[] data= sportDataSoup.getMatchdataByGroupLeagueSaison(goid, "bl1", ls);
//				mav.addObject("spielliste",data);
//				mav.addObject("failureComment", "du kannst nur,0 (Heimmannschaft gewinnt), 1 (Gastmannschaft gewinnt) oder 2 (Unentschieden), eintragen");
//				return mav;
//			}
//	}
	

}
