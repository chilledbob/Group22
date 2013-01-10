package gmb.controller;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

import gmb.model.CDecimal;
import gmb.model.GmbDecoder;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.RequestState;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PermaTT;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.SingleTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import org.eclipse.persistence.exceptions.DatabaseException;
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
		
		for(TotoSTT tSTT : currentCustomer.getTotoSTTs()){
			if((!tSTT.getTip().getDraw().getEvaluated()) && (tSTT.getPapaTicket() == 0)){
				totoSTTList.add(tSTT);
			}
		}
		for(DailyLottoSTT dLSTT : currentCustomer.getDailyLottoSTTs()){
			if((!dLSTT.getTip().getDraw().getEvaluated()) && (dLSTT.getPapaTicket() == 0)){
				dailyLottoSTTList.add(dLSTT);
			}
		}
		for(WeeklyLottoSTT wLSTT : currentCustomer.getWeeklyLottoSTTs()){
			if((!wLSTT.getTip().getDraw().getEvaluated()) && (wLSTT.getPapaTicket() == 0))
				weeklySTTList.add(wLSTT);
		}

		mav.addObject("weeklySTTList", (weeklySTTList.size() > 0) ? weeklySTTList : null);
		mav.addObject("totoSTTList", (totoSTTList.size() > 0) ? totoSTTList : null );
		mav.addObject("dailySTTList", (dailyLottoSTTList.size() > 0) ? dailyLottoSTTList : null);
		mav.addObject("weeklyPTTList", (currentCustomer.getWeeklyLottoPTTs().size() > 0 ) ? currentCustomer.getWeeklyLottoPTTs() : null);
		mav.addObject("dailyPTTList", (currentCustomer.getDailyLottoPTTs().size() > 0) ? currentCustomer.getDailyLottoPTTs() : null);
		mav.addObject("currentUser", currentCustomer);
		return mav;	
	}
	
//	-----------------------------------------edit STTs------------------------------------------
	
	@RequestMapping("/customerEditSTT")
	public ModelAndView editSTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("STTid") int ticketId){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		SingleTT stt = (SingleTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
		switch(stt.getDrawTypeAsInt()){
			case 1: mav.setViewName("customer/tips/editSingleTickets");
					for(DailyLottoSTT dailySTT : currentCustomer.getDailyLottoSTTs()){
						if(dailySTT.getPapaTicket() == ticketId)
							childList.add(dailySTT);
					}
					break;
			case 2: mav.setViewName("customer/tips/editSingleTicketsToto");
					for(TotoSTT totoSTT : currentCustomer.getTotoSTTs()){
						if(totoSTT.getPapaTicket() == ticketId)
							childList.add(totoSTT);
					}
					break;
			default: mav.setViewName("customer/tips/editSingleTicketWLT");
					for(WeeklyLottoSTT weeklySTT : currentCustomer.getWeeklyLottoSTTs()){
						if(weeklySTT.getPapaTicket() == ticketId)
								childList.add(weeklySTT);
					}
					break;
		}
		if(stt.getDrawTypeAsInt() == 2){
			TotoEvaluation eva = (TotoEvaluation) stt.getTip().getDraw();
			ArrayList<Integer> tiplist = new ArrayList<Integer>();
			for(int i = 0; i < 9;i++)
				tiplist.add(stt.getTip().getTip()[i]);
			mav.addObject("tipliste", tiplist);
			mav.addObject("spielliste", eva.getGameData());
		}
		CDecimal money = stt.getPaidPurchasePrice();
		for(SingleTT ticket : childList){
			money = money.add(ticket.getPaidPurchasePrice());
		}
		mav.addObject("singleTT", stt);
		mav.addObject("money", money);
		mav.addObject("currentUser", currentCustomer);
		return mav;
	}
	
	@RequestMapping(value="/customerChangeTipWLTip",method=RequestMethod.POST)
	public ModelAndView ChangeTipSTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("STTid") int ticketId,
			@RequestParam("Zahl1") int n1,
			@RequestParam("Zahl2") int n2,
			@RequestParam("Zahl3") int n3,
			@RequestParam("Zahl4") int n4,
			@RequestParam("Zahl5") int n5,
			@RequestParam("Zahl6") int n6,
			@RequestParam("Zahl7") int en){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		SingleTT stt = (SingleTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		mav.addObject("singleTT", stt);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editSingleTicketWLT");
		int[] newTip = new int[]{n1,n2,n3,n4,n5,n6,en};
		if(stt.getTip().getDraw().isTimeLeftUntilEvaluationForChanges()){
			for(int l = 0; l < 7; l++){
				if((newTip[l] < 1) || (newTip[l] > 49)){
					mav.addObject("failComment", "Die Zahl an der Stelle "+l+" war nicht zwischen 1 und 49");
					return mav;
				}
			}
			for(int i = 0; i < 7; i++){
				for(int k = i+1; k < 7; k++){
					if(newTip[i] == newTip[k]){
						mav.addObject("failComment", "Die Zahl an der Stelle "+i+" war gleich der Zahl an der stelle "+k);
						return mav;
					}
				}
			}
			stt.getTip().setTip(newTip);
			for(WeeklyLottoSTT weeklySTT : currentCustomer.getWeeklyLottoSTTs()){
				if(weeklySTT.getPapaTicket() == ticketId)
					weeklySTT.getTip().setTip(newTip);
			}
		}
		LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
		CDecimal money = stt.getPaidPurchasePrice();
		for(WeeklyLottoSTT weeklySTT : currentCustomer.getWeeklyLottoSTTs()){
			if(weeklySTT.getPapaTicket() == stt.getId())
					childList.add(weeklySTT);
		}
		for(SingleTT ticket : childList){
			money = money.add(ticket.getPaidPurchasePrice());
		}
		mav.addObject("failComment", "Ihr Tip wurde geändert!");
		mav.addObject("money", money);
		return mav;
	}
	
	@RequestMapping(value="/customerChangeTipDLTip",method=RequestMethod.POST)
	public ModelAndView ChangeTipSTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("STTid") int ticketId,
			@RequestParam("Zahl1") int n1,
			@RequestParam("Zahl2") int n2,
			@RequestParam("Zahl3") int n3,
			@RequestParam("Zahl4") int n4,
			@RequestParam("Zahl5") int n5,
			@RequestParam("Zahl6") int n6,
			@RequestParam("Zahl7") int n7,
			@RequestParam("Zahl8") int n8,
			@RequestParam("Zahl9") int n9,
			@RequestParam("Zahl10") int n10){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		SingleTT stt = (SingleTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		int[] newTip = new int[]{n1,n2,n3,n4,n5,n6,n7,n8,n9,n10};
		if(stt.getTip().getDraw().isTimeLeftUntilEvaluationForChanges()){
			if(stt.getTip().validateTip(newTip) == 0){
				stt.getTip().setTip(newTip);
				for(DailyLottoSTT dailySTT : currentCustomer.getDailyLottoSTTs()){
					if(dailySTT.getPapaTicket() == ticketId)
						dailySTT.getTip().setTip(newTip);
				}
				mav.addObject("failComment", "Ihr Tip wurde geändert!");
			}
			else{
				mav.addObject("failComment", "Sie haben keinen gültigen Tip abgegeben!");
			}
		}
		else{
			mav.addObject("failComment", "Es ist zu spät um Änderungen vorzunehmen.");
		}
		LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
		CDecimal money = stt.getPaidPurchasePrice();
		for(DailyLottoSTT dailySTT : currentCustomer.getDailyLottoSTTs()){
			if(dailySTT.getPapaTicket() == stt.getId())
					childList.add(dailySTT);
		}
		for(SingleTT ticket : childList){
			money = money.add(ticket.getPaidPurchasePrice());
		}
		mav.addObject("money", money);
		mav.addObject("singleTT", stt);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editSingleTickets");
		return mav;
	}
	
	@RequestMapping(value="/customerChangeTotoTip",method=RequestMethod.POST)
	public ModelAndView ChangeTotoTipSTT(ModelAndView mav,
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
			@RequestParam("ticketId") int ticketId){
			Integer erg0=new Integer(er0);
			Integer erg1=new Integer(er1);
			Integer erg2=new Integer(er2);
			Integer erg3=new Integer(er3);
			Integer erg4=new Integer(er4);
			Integer erg5=new Integer(er5);
			Integer erg6=new Integer(er6);
			Integer erg7=new Integer(er7);
			Integer erg8=new Integer(er8);
		TotoSTT totoStt = (TotoSTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		TotoEvaluation eva = (TotoEvaluation) totoStt.getTip().getDraw();
		if(eva.isTimeLeftUntilEvaluationForChanges()){
			int[] newTip = new int[]{erg0,erg1,erg2,erg3,erg4,erg5,erg6,erg7,erg8};
			for(int i = 0; i < 9; i++){
				if((newTip[i]!=0) && (newTip[i]!=1) && (newTip[i]!=2)){
					mav.addObject("singleTT", totoStt);
					mav.addObject("currentUser", currentCustomer);
					mav.addObject("failcomment", "Sie haben einen ungültigen Tip abgegeben!");
					mav.setViewName("customer/tips/editSingleTickets");
					return mav;
				}
			}
			totoStt.getTip().setTip(newTip);
			for(TotoSTT totoTicket : currentCustomer.getTotoSTTs()){
				if(totoTicket.getPapaTicket() == totoStt.getId())
					totoTicket.getTip().setTip(newTip);
			}
		}
		LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
		CDecimal money = totoStt.getPaidPurchasePrice();
		for(TotoSTT ticket : currentCustomer.getTotoSTTs()){
			if(ticket.getPapaTicket() == totoStt.getId())
					childList.add(ticket);
		}
		for(SingleTT ticket : childList){
			money = money.add(ticket.getPaidPurchasePrice());
		}
		mav.addObject("singleTT", totoStt);
		mav.addObject("money", money);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editSingleTickets");
		return mav;
	}
	
	@RequestMapping("/customerNewSTT")
	public ModelAndView newSTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("STTid") int ticketId,
			@RequestParam("anzahl") int count){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		SingleTT stt = (SingleTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		mav.addObject("singleTT", stt);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editSingleTickets");
		if(stt.getDrawTypeAsInt() == 1){
			stt = (DailyLottoSTT) stt;
			for(int i = 1; i < count+1; i++){
			ReturnBox<Integer, DailyLottoSTT> rb = GmbFactory.createAndPurchase_DailyLottoSTT(currentCustomer);
			if(rb.var1 == 0 ){
				DailyLottoSTT ticket = rb.var2;
				ticket.setPapaTicket(stt.getId());
				DailyLottoDraw draw = (DailyLottoDraw) stt.getTip().getDraw();
				draw.createAndSubmitSingleTip(ticket, stt.getTip().getTip());
				mav.addObject("confirm", true);
				mav.addObject("failComment", "Es wurden "+i+" neue Tickets erworben.");
			}
			else{
				mav.addObject("confirm", false);
				mav.addObject("failureComment", "Es konnten nur"+i+" Tiptickets erworben werden, da Sie mehr Geld benötigen um weitere Tiptickets zu erwerben." +
													"Bitte <a href='bankingCustomer?uid="+currentCustomer.getIdentifier()+"'>Konto aufladen</a>!");
				return mav;
			}
			}
			LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
			CDecimal money = stt.getPaidPurchasePrice();
			for(DailyLottoSTT dailySTT : currentCustomer.getDailyLottoSTTs()){
				if(dailySTT.getPapaTicket() == stt.getId())
						childList.add(dailySTT);
			}
			for(SingleTT childTicket : childList){
				money = money.add(childTicket.getPaidPurchasePrice());
			}
			mav.addObject("money", money);
		}
		else{
			if(stt.getDrawTypeAsInt() == 2){
				for(int i = 1; i < count+1; i++){	
					ReturnBox<Integer, TotoSTT> rb = GmbFactory.createAndPurchase_TotoSTT(currentCustomer);
					if(rb.var1 == 0){
						TotoSTT tSTT = rb.var2;
						tSTT.setPapaTicket(stt.getId());
						TotoEvaluation totoEva = (TotoEvaluation) stt.getTip().getDraw();
						totoEva.createAndSubmitSingleTip(tSTT, stt.getTip().getTip());
						mav.addObject("confirm", true);
						mav.addObject("failComment", "Es wurden "+i+" neue Tickets erworben.");
					}
					else{
						mav.addObject("confirm", false);
						mav.addObject("tips", stt.getTip().getTip());
						mav.addObject("failureComment", "Sie benötigen mehr Geld um ein Tipticket zu erwerben. Bitte <a href='bankingCustomer?uid="+currentCustomer.getIdentifier()+"'>Konto aufladen</a>!");
						return mav;
					}
				}
				LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
				CDecimal money = stt.getPaidPurchasePrice();
				for(TotoSTT ticket : currentCustomer.getTotoSTTs()){
					if(ticket.getPapaTicket() == stt.getId())
							childList.add(ticket);
				}
				for(SingleTT ticket : childList){
					money = money.add(ticket.getPaidPurchasePrice());
				}
				mav.addObject("money", money);
			}
			else{
				stt = (WeeklyLottoSTT) stt;
				for(int i = 1; i < count+1; i++){
					ReturnBox<Integer, WeeklyLottoSTT> rb = GmbFactory.createAndPurchase_WeeklyLottoSTT(currentCustomer);
					if(rb.var1 == 0 ){
						SingleTT ticket = rb.var2;
						ticket.setPapaTicket(stt.getId());
						WeeklyLottoDraw draw = (WeeklyLottoDraw) stt.getTip().getDraw();
						draw.createAndSubmitSingleTip(rb.var2, stt.getTip().getTip());
						mav.addObject("confirm", true);
						mav.addObject("failComment", "Es wurden "+i+" neue Tickets erworben.");
					}
					else{
						mav.addObject("confirm", false);
						mav.addObject("failureComment", "Sie benötigen mehr Geld um weitere Tiptickets zu erwerben. Bitte <a href='bankingCustomer?uid="+currentCustomer.getIdentifier()+"'>Konto aufladen</a>!");
						return mav;
					}
				}
				LinkedList<SingleTT> childList = new LinkedList<SingleTT>();
				CDecimal money = stt.getPaidPurchasePrice();
				for(WeeklyLottoSTT weeklySTT : currentCustomer.getWeeklyLottoSTTs()){
					if(weeklySTT.getPapaTicket() == stt.getId())
							childList.add(weeklySTT);
				}
				for(SingleTT ticket : childList){
					money = money.add(ticket.getPaidPurchasePrice());
				}
			}
		}
		
		return mav;
	}
	
	@RequestMapping("/customerDeleteSTT")
	public ModelAndView deleteSTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("STTid") int ticketId){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		SingleTT stt = (SingleTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		int i = 1;
		if(stt.getTip().getDraw().isTimeLeftUntilEvaluationForChanges()){
		if(stt.getDrawTypeAsInt() == 1){
			currentCustomer.getDailyLottoSTTs().remove((DailyLottoSTT) stt);
			stt.getTip().getDraw().removeTip((DailyLottoTip) stt.getTip());
			GmbPersistenceManager.remove((DailyLottoTip) stt.getTip());
//			GmbPersistenceManager.remove((DailyLottoSTT) stt);
			currentCustomer.DB_UPDATE();
			for(DailyLottoSTT dailySTT : currentCustomer.getDailyLottoSTTs()){
				if(dailySTT.getId() == ticketId){
					currentCustomer.getDailyLottoSTTs().remove(dailySTT);
					stt.getTip().getDraw().removeTip(dailySTT.getTip());
					GmbPersistenceManager.remove(dailySTT.getTip());
					GmbPersistenceManager.remove(dailySTT);
					currentCustomer.DB_UPDATE();
					i++;
				}
			}
		}
		else{
			if(stt.getDrawTypeAsInt() == 2){
				currentCustomer.getTotoSTTs().remove((TotoSTT) stt);
				stt.getTip().getDraw().removeTip((TotoTip) stt.getTip());
				currentCustomer.DB_UPDATE();
				GmbPersistenceManager.remove((TotoTip) stt.getTip());
//				GmbPersistenceManager.remove((TotoSTT) stt);
				for(TotoSTT totoSTT : currentCustomer.getTotoSTTs()){
					if(totoSTT.getId() == ticketId){
						currentCustomer.getTotoSTTs().remove(totoSTT);
						totoSTT.getTip().getDraw().removeTip(totoSTT.getTip());
						GmbPersistenceManager.remove(totoSTT.getTip());
//						GmbPersistenceManager.remove((TotoSTT) totoSTT);
						currentCustomer.DB_UPDATE();
						i++;
					}
				}
			}
			else{
				currentCustomer.getWeeklyLottoSTTs().remove((WeeklyLottoSTT) stt);
				stt.getTip().getDraw().removeTip((WeeklyLottoTip) stt.getTip());
				GmbPersistenceManager.remove((WeeklyLottoTip) stt.getTip());
				currentCustomer.DB_UPDATE();
				for(WeeklyLottoSTT weeklySTT : currentCustomer.getWeeklyLottoSTTs()){
					if(weeklySTT.getId() == ticketId){
						currentCustomer.getWeeklyLottoSTTs().remove((WeeklyLottoSTT) weeklySTT);
						weeklySTT.getTip().getDraw().removeTip((WeeklyLottoTip) weeklySTT.getTip());
						GmbPersistenceManager.remove((WeeklyLottoTip) weeklySTT.getTip());
//						GmbPersistenceManager.remove((WeeklyLottoSTT) weeklySTT);
						currentCustomer.DB_UPDATE();
						i++;
					}
				}
			}
		}
		mav.addObject("confirm", true);
		}
		else{
			mav.addObject("confirm", false);
		}
		int j = 1;
		do{
		ExternalTransactionRequest etr = currentCustomer.getBankAccount().sendExternalTransactionRequest(new CDecimal(stt.getPaidPurchasePrice().getAmount()), "Money back").var2;
		etr.accept();
		}while(j < i);
		currentCustomer = (Customer) GmbPersistenceManager.refresh(currentCustomer);
		mav.addObject("currentUser", currentCustomer);
		
		mav.setViewName("customer/tips/deletedSingleTickets");
		return mav;	
	}
	
//	-----------------------------------------edit PTTs------------------------------------------
	@RequestMapping("/customerEditPTT")
	public ModelAndView editPTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("PTTid") int ticketId){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		PermaTT ptt = (PermaTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		switch(ptt.getDrawTypeAsInt()){
			case 1: mav.setViewName("customer/tips/editPermaTicketsDaily");break;
			default: mav.setViewName("customer/tips/editPermaTicketsWeekly");break;
		}
		mav.addObject("permaTT", ptt);
		mav.addObject("currentUser", currentCustomer);
		return mav;
	}
	
	@RequestMapping(value="/customerChangeTipWLPTip",method=RequestMethod.POST)
	public ModelAndView ChangeWeeklyTipPTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("PTTid") int ticketId,
			@RequestParam("Zahl1") int n1,
			@RequestParam("Zahl2") int n2,
			@RequestParam("Zahl3") int n3,
			@RequestParam("Zahl4") int n4,
			@RequestParam("Zahl5") int n5,
			@RequestParam("Zahl6") int n6,
			@RequestParam("Zahl7") int en){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		PermaTT ptt = (PermaTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editPermaTicketsWeekly");
		int[] newTip = new int[]{n1,n2,n3,n4,n5,n6,en};
		if(!ptt.isExpired()){
			for(int l = 0; l < 7; l++){
				if((newTip[l] < 1) || (newTip[l] > 49)){
					mav.addObject("permaTT", ptt);
					mav.addObject("failComment", "Die Zahl an der Stelle "+l+" war nicht zwischen 1 und 49");
					return mav;
				}
			}
			for(int i = 0; i < 7; i++){
				for(int k = i+1; k < 7; k++){
					if(newTip[i] == newTip[k]){
						mav.addObject("permaTT", ptt);
						mav.addObject("failComment", "Die Zahl an der Stelle "+i+" war gleich der Zahl an der stelle "+k);
						return mav;
					}
				}
			}
			ptt.setTip(newTip);
			for(WeeklyLottoPTT wlptt : currentCustomer.getWeeklyLottoPTTs()){
				if(wlptt.getPapaTicket() == ptt.getId())
					wlptt.setTip(newTip);
			}
		}
		mav.addObject("permaTT", ptt);
		mav.addObject("failComment", "Ihr Tip wurde geändert!");
		return mav;
	}
	
	@RequestMapping(value="/customerChangeTipDLPTip",method=RequestMethod.POST)
	public ModelAndView ChangeDailyTipPTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("PTTid") int ticketId,
			@RequestParam("Zahl1") int n1,
			@RequestParam("Zahl2") int n2,
			@RequestParam("Zahl3") int n3,
			@RequestParam("Zahl4") int n4,
			@RequestParam("Zahl5") int n5,
			@RequestParam("Zahl6") int n6,
			@RequestParam("Zahl7") int n7,
			@RequestParam("Zahl8") int n8,
			@RequestParam("Zahl9") int n9,
			@RequestParam("Zahl10") int n10){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		PermaTT ptt = (PermaTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		mav.addObject("currentUser", currentCustomer);
		mav.setViewName("customer/tips/editPermaTicketsDaily");
		int[] newTip = new int[]{n1,n2,n3,n4,n5,n6,n7,n8,n9,n10};
		if(!ptt.isExpired()){
			for(int i = 0; i < 10; i++){
				if((newTip[i] < 1) || (newTip[i] > 9)){
					mav.addObject("permaTT", ptt);
					mav.addObject("failComment", "Ungültiger Tip! Die Zahl an der Stelle "+i+" war nicht zwischen 1 und 9");
					return mav;
				}
			}
			ptt.setTip(newTip);
			if(ptt.getDrawTypeAsInt() == 0){
				for(DailyLottoPTT dlptt : currentCustomer.getDailyLottoPTTs()){
					if(dlptt.getPapaTicket() == ptt.getId())
						dlptt.setTip(newTip);
				}
			}
		}
		mav.addObject("permaTT", ptt);
		return mav;
	}
	
	@RequestMapping("/customerNewPTT")
	public ModelAndView newPTT(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("PTTid") int ticketId,
			@RequestParam("anzahl") int count){
		Customer currentCustomer = (Customer)GmbPersistenceManager.get(uid);
		PermaTT ptt = (PermaTT) GmbPersistenceManager.get(TipTicket.class, ticketId);
		int papaTicketId = ptt.getId();
		if(ptt.getDrawTypeAsInt() == 1){
			mav.setViewName("customer/tips/editPermaTicketsDaily");
			ptt = (DailyLottoPTT) ptt;
			for(int i = 0; i < count; i++){
			ReturnBox<Integer, DailyLottoPTT> rb = GmbFactory.createAndPurchase_DailyLottoPTT(currentCustomer,ptt.getDuration());
			if(rb.var1 == 0 ){
				DailyLottoPTT ticket = rb.var2;
				ticket.setTip(ptt.getTip());
				if((i==0) && (ptt.getPurchaseDate().getDayOfYear() == Lottery.getInstance().getTimer().getDateTime().getDayOfYear()))
					papaTicketId = ticket.getPapaTicket();
				else
					ticket.setPapaTicket(papaTicketId);
			}
			else{
				mav.setViewName("customer/tips/tip_lotto");
				mav.addObject("confirm", false);
				mav.addObject("failureComment", "Sie benötigen mehr Geld um weitere Tiptickets zu erwerben. Bitte <a href='bankingCustomer?uid="+currentCustomer.getIdentifier()+"'>Konto aufladen</a>!");
			}
			}
		}
		else{
				mav.setViewName("customer/tips/editPermaTicketsWeekly");
				ptt = (WeeklyLottoPTT) ptt;
				for(int i = 0; i < count; i++){
				ReturnBox<Integer, WeeklyLottoPTT> rb = GmbFactory.createAndPurchase_WeeklyLottoPTT(currentCustomer, ptt.getDuration());
				if(rb.var1 == 0 ){
					PermaTT ticket = rb.var2;
					ticket.setTip(ptt.getTip());
					if((i==0) && (ptt.getPurchaseDate().getDayOfYear() == Lottery.getInstance().getTimer().getDateTime().getDayOfYear()))
						papaTicketId = ticket.getPapaTicket();
					else
						ticket.setPapaTicket(papaTicketId);
				}
				else{
					mav.setViewName("customer/tips/tip_lotto");
					mav.addObject("confirm", false);
					mav.addObject("failureComment", "Sie benötigen mehr Geld um weitere Tiptickets zu erwerben. Bitte <a href='bankingCustomer?uid="+currentCustomer.getIdentifier()+"'>Konto aufladen</a>!");
				}
			}
		}
		
		mav.addObject("permaTT", ptt);
		mav.addObject("currentUser", currentCustomer);
		return mav;
	}
	
}