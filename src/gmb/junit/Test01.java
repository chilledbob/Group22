package gmb.junit;

import static org.junit.Assert.*;

import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import gmb.model.CDecimal;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.financial.container.ReceiptsDistribution;
import gmb.model.financial.container.TipTicketPrices;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.group.GroupManagement;
import gmb.model.member.Admin;
import gmb.model.member.Customer;
import gmb.model.member.MemberManagement;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.Notification;
import gmb.model.request.RequestState;
import gmb.model.request.data.MemberDataUpdateRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;
import gmb.model.request.group.GroupInvitation;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.salespointframework.core.user.PersistentUserManager;

public class Test01 
{
	boolean printTimeLineToConsol = true;
	
	public void printCurrentTimeToConsol(String happening)
	{
		if(printTimeLineToConsol)
		{
			System.out.println(happening);
			System.out.println(Lottery.getInstance().getTimer().getDateTime().toString());
			System.out.println("");
		}
	}
	
	public Customer createStdCustomer(String name)
	{
		Adress stdAdress = new Adress("SomeStreet", "111", "010101", "SomeOtherCity");
		MemberData stdData = new MemberData(name, "Mueller", new DateTime(1970,10,16,0,0), "0735643", "someone@mail.gmb", stdAdress);
		LotteryBankAccount stdbankacc = new LotteryBankAccount(new RealAccountData("0303003", "0340400"));
		return  new Customer(name+"_nick", "notsafepassword", stdData, stdbankacc);
	}
	
	Admin admin1;
	Customer cus1;
	Customer cus2;
	Customer cus3;
	Customer cus4;
	Customer cus5;
	
	Group group1;
	Group group2;
	Group group3;
	Group group4;
	
	@SuppressWarnings("unchecked")
	@Test
	public void MasterTest()
	{
		//=========================================================================================================================//USER TESTs NO 1
		
		FinancialManagement financialManagement = new FinancialManagement(new TipTicketPrices(), new ReceiptsDistribution());
		MemberManagement memberManagement = new MemberManagement();
		GroupManagement groupManagement = new GroupManagement();
		TipManagement tipManagement = new TipManagement();

		Lottery.Instanciate(financialManagement, memberManagement, groupManagement, tipManagement);
		Lottery.getInstance().getTimer().setReferenceDate(new DateTime(2013,1,1,0,0));//HAPPY NEW YEAR! ..it's now..belief it!  
		printCurrentTimeToConsol("Lottery has opend!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addMinutes(5);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		Adress admin1Adress = new Adress("Eich Strasse", "18", "90378", "SomeTown");
		MemberData admin1Data = new MemberData("Heinrich", "Siegel", new DateTime(1950,1,1,0,0), "892537", "heino@mail.gmb", admin1Adress);
		admin1 = new Admin("MegaAdmin", "secretpasswordnonumbers", admin1Data);
		Lottery.getInstance().getMemberManagement().addMember(admin1);
		
		printCurrentTimeToConsol("We have an Admin now!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addHours(8);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		Adress cus1Adress = new Adress("PimpfStreet", "044", "08653", "PimpfCity");
		MemberData cus1Data = new MemberData("Amanda", "Jiggsaw", new DateTime(1970,10,16,0,0), "0735643", "me@mail.gmb", cus1Adress);
		LotteryBankAccount cus1bankacc = new LotteryBankAccount(new RealAccountData("869823", "0387934"));
		cus1 = new Customer("MiniAmanda", "iwannaplayagame", cus1Data, cus1bankacc);
		Lottery.getInstance().getMemberManagement().addMember(cus1);
		
		printCurrentTimeToConsol("First Customer!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		cus2 = createStdCustomer("Tom");
		cus3 = createStdCustomer("Heino");
		cus4 = createStdCustomer("Nohein");
		cus5 = createStdCustomer("Karsten");
		
		Lottery.getInstance().getMemberManagement().addMember(cus2);
		Lottery.getInstance().getMemberManagement().addMember(cus3);
		Lottery.getInstance().getMemberManagement().addMember(cus4);
		Lottery.getInstance().getMemberManagement().addMember(cus5);
		
		assertEquals(6, Lottery.getInstance().getMemberManagement().getMembers().size());
		
		printCurrentTimeToConsol("5 brand new StdCustomers!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		cus1.getBankAccount().sendExternalTransactionRequest(new CDecimal(100), "want my money! D:");
		cus2.getBankAccount().sendExternalTransactionRequest(new CDecimal(60), "want my money also! D:");
		cus3.getBankAccount().sendExternalTransactionRequest(new CDecimal(1000), "money!");
		cus4.getBankAccount().sendExternalTransactionRequest(new CDecimal(10), "");
		cus5.getBankAccount().sendExternalTransactionRequest(new CDecimal(1000000), "WANT MORE MONEY THEN I PROBABLY HAVE!!!");
		
		assertEquals(5, Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests().size());
		
		printCurrentTimeToConsol("some guys requested Money from their real kontos!");//<------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addHours(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//smart admin work:
		for(ExternalTransactionRequest request : Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests())
		{
			if(request.getTransaction().getAmount().compareTo(new CDecimal(5000)) < 1)
			{
				System.out.println(request.accept());
			}
			else
			{
				request.getMember().addNotification(new Notification("No so much money! You evil!"));
			}
		}
		System.out.println("");
		
		assertEquals(4, Lottery.getInstance().getFinancialManagement().getExternalTransactions().size());
		assertEquals(1, cus5.getNotifications().size());
		
		assertEquals(new CDecimal("100.00"), cus1.getBankAccount().getCredit());
		assertEquals(new CDecimal("60.00"), cus2.getBankAccount().getCredit());
		assertEquals(new CDecimal("1000.00"), cus3.getBankAccount().getCredit());
		assertEquals(new CDecimal("10.00"), cus4.getBankAccount().getCredit());
		assertEquals(new CDecimal(0), cus5.getBankAccount().getCredit());
		
		printCurrentTimeToConsol("Most people got their money!");//<-------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addMinutes(23);//<-------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		Adress cus2Adress = new Adress("Bahn Strasse", "96", "436747", "BahnStadt");
		MemberData cus2Data = new MemberData("Heino", "Heini", new DateTime(1980,10,10,0,0), "74375878", "heinoWHOALREADYTOOKMYNAME@DieVerdammteBahnKommtIMMERzuSpaet.gmb", cus2Adress);
		
		cus2.sendDataUpdateRequest(cus2Data, "I want real member data! You've created me with default data! D:");
		cus3.getBankAccount().sendDataUpdateRequest(new RealAccountData("839843789", "7885758"), "Hello, please accept my update. Thanks.");
		
		//tired admin work, accepting everything:
		for(MemberDataUpdateRequest request : Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests())
			System.out.println(request.accept());
		
		for(RealAccountDataUpdateRequest request : Lottery.getInstance().getFinancialManagement().getRealAccounDataUpdateRequests())
			System.out.println(request.accept());
		System.out.println("");
		
		assertEquals("Bahn Strasse", cus2.getMemberData().getAdress().getStreetName());
		assertEquals("7885758", cus3.getBankAccount().getRealAccountData().getAccountNumber());
		
		printCurrentTimeToConsol("Some DataUpdateRequests accepted!");//<-------------------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=============================================================================================================================================//GROUP TESTs NO 1
		
		Lottery.getInstance().getTimer().addHours(1);//<-------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		group1 = new Group("Cutie Mark Crusaders", cus1, "We are a group of people who like to play all kind of lotto and stuff! SRSLY!");
		group1.sendGroupInvitation(cus2, "Love you, come in my group!");
		GroupInvitation inv1 = group1.sendGroupInvitation(cus5, "Love for all! Even for ya!");
		group1.applyForMembership(cus3, "Hello, please accept!");
		
		group2 = new Group("ExclusiveGroupDLX", cus5, "Only rich people without real money allowed!");
		group2.applyForMembership(cus4, "");
		group2.sendGroupInvitation(cus2, "My group DLX! Come!");
		group2.applyForMembership(cus3, "Hello, please accept!");
		
		Lottery.getInstance().getTimer().addHours(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//smart cus2 browsing his lists:
		for(GroupInvitation invitation : cus2.getGroupInvitations())
		{
			if(invitation.getGroup() == group1)
			{
				invitation.accept(); 
				invitation.getGroup().getGroupAdmin().addNotification("I'm so happy you've sent me an invitation! :D");
				invitation.getGroup().getGroupAdmin().addNotification("Btw, don't send Karsten an invitation! He's no brony!");
			}
			else
				if(invitation.getGroup() == group2)
				{
					invitation.refuse(); 
					invitation.getGroup().getGroupAdmin().addNotification("Screw you douchebag!");
				}
		}
		
		Lottery.getInstance().getTimer().addMinutes(5);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//smart cus1(groupadmin) browsing group1's lists:
		for(GroupInvitation invitation : group1.getGroupInvitations())
		{
			if(invitation.getMember() == cus5)
				invitation.withdraw();//if Heino says so then better not...
		}
		
		//smart cus1(groupadmin) browsing group1's lists:
		for(GroupMembershipApplication application : group1.getGroupMembershipApplications())
		{
			if(application.getMember() == cus3)
			{
				application.accept();
				application.getMember().addNotification("Welcome on board! :)");
			}
		}
		
		Lottery.getInstance().getTimer().addMinutes(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//stupid cus5(groupadmin) browsing group2's lists:
		for(GroupMembershipApplication application : group2.getGroupMembershipApplications())
		{
			if(application.getMember() == cus3 || application.getMember() == cus4)
				application.accept();//perhaps some rich guy..who knows
		}
		
		//stupid cus5 browsing his lists:
		for(GroupInvitation invitation : cus5.getGroupInvitations())
		{
			if(invitation.getGroup() == group1)
				invitation.accept();//tries to accept even though the request has already been withdrawn (stupid guy) [the View should actually hide this possibility]

		}
		
		assertEquals(2, Lottery.getInstance().getGroupManagement().getGroups().size());
		
		assertEquals(2, cus2.getGroupInvitations().size());
		assertEquals(1, cus5.getGroupInvitations().size());
		
		assertEquals(2, cus3.getGroupMembershipApplications().size());
		assertEquals(1, cus4.getGroupMembershipApplications().size());
		
		assertEquals(1, cus1.getGroups().size());
		assertEquals(1, cus2.getGroups().size());
		assertEquals(2, cus3.getGroups().size());
		assertEquals(1, cus4.getGroups().size());
		assertEquals(1, cus5.getGroups().size());
		
		assertEquals(2, group1.getGroupMembers().size());
		assertEquals(2, group2.getGroupMembers().size());
		
		assertEquals(2, group1.getGroupInvitations().size());
		assertEquals(1, group2.getGroupInvitations().size());
		
		assertEquals(1, group1.getGroupMembershipApplications().size());
		assertEquals(2, group2.getGroupMembershipApplications().size());
		
		assertEquals(RequestState.WITHDRAWN, inv1.getState());
		
		printCurrentTimeToConsol("2 new groups and some applications + invitations.");//<------------------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=============================================================================================================================================//GROUP TESTs NO 1
		
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		group3 = new Group("MuffinGroup", cus2, "Omnomnom!");
		group3.applyForMembership(cus1, "Muffins?! I'm so in!!1");
		group3.applyForMembership(cus4, "");
		group3.applyForMembership(cus5, "Yo, man!");
		
		//smart cus2(groupadmin) browsing group3's lists:
		for(GroupMembershipApplication application : group3.getGroupMembershipApplications())
			application.accept();

		group3.sendGroupAdminRightsTransfereOffering(cus1, "U moar muffin! U admin! :D");
		cus1.getGroupAdminRightsTransfereOfferings().get(0).accept();
		
		assertEquals(true, group3.getGroupAdmin() == cus1);
		
		group3.resign(cus5);
		
		assertEquals(false, group3.getGroupMembers().contains(cus5));
		assertEquals(3, cus5.getNotifications().size());
		assertEquals(0, cus5.getGroupAdminRightsTransfereOfferings().size());
		
		//cus1 (groupAdmin) sending invitation:
		group3.sendGroupInvitation(cus3, "u wanna come in?");
		
		//group admin resigns which leads to closure of the group:
		group3.resign(cus1);
		
		assertEquals(true, group3.isClosed());
		assertEquals(false, group3.getGroupMembers().contains(cus1));
		assertEquals(false, group3.getGroupMembers().contains(cus4));
		assertEquals(true, group3.getGroupAdmin() == null);
		assertEquals(RequestState.WITHDRAWN, ((LinkedList<GroupInvitation>)cus3.getGroupInvitations()).getLast().getState());
		//=========================================================================================================================//TIPTICKET TESTs NO 1
		
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		WeeklyLottoSTT ticket0 = new WeeklyLottoSTT();
		WeeklyLottoSTT ticket1 = new WeeklyLottoSTT();
		DailyLottoSTT ticket2 = new DailyLottoSTT();
		CDecimal oriCredit1 = cus1.getBankAccount().getCredit();
		ticket0.purchase(cus1);
		ticket1.purchase(cus1);
		ticket2.purchase(cus1);
		
		TotoSTT ticket3 = new TotoSTT();
		DailyLottoPTT ticket4 = new DailyLottoPTT(PTTDuration.MONTH);
		CDecimal oriCredit2 = cus2.getBankAccount().getCredit();
		ticket3.purchase(cus2);
		ticket4.purchase(cus2);
		
		WeeklyLottoPTT ticket5 = new WeeklyLottoPTT(PTTDuration.YEAR);
		DailyLottoPTT ticket6 = new DailyLottoPTT(PTTDuration.HALFYEAR);
		CDecimal oriCredit3 = cus3.getBankAccount().getCredit();
		ticket5.purchase(cus3);
		ticket6.purchase(cus3);
		
		WeeklyLottoSTT ticket7 = new WeeklyLottoSTT();
		CDecimal oriCredit4 = cus4.getBankAccount().getCredit();
		ticket7.purchase(cus4);
		
		WeeklyLottoPTT ticket8 = new WeeklyLottoPTT(PTTDuration.YEAR);
		DailyLottoPTT ticket9 = new DailyLottoPTT(PTTDuration.YEAR);
		boolean tp_res1 = ticket8.purchase(cus5);
		ticket9.purchase(cus5);
		
		TipTicketPrices prices = new TipTicketPrices();
		
		assertEquals(0, ticket4.getDurationType());
		assertEquals(2, ticket5.getDurationType());
		assertEquals(1, ticket6.getDurationType());
		assertEquals(2, ticket8.getDurationType());
		assertEquals(2, ticket9.getDurationType());
		
		assertEquals(1, cus1.getDailyLottoSTTs().size());
		assertEquals(2, cus1.getWeeklyLottoSTTs().size());
		assertEquals(oriCredit1.subtract(prices.getWeeklyLottoSTTPrice()).subtract(prices.getWeeklyLottoSTTPrice()).subtract(prices.getDailyLottoSTTPrice()), cus1.getBankAccount().getCredit());
		
		assertEquals(1, cus2.getTotoSTTs().size());
		assertEquals(1, cus2.getDailyLottoPTTs().size());
		assertEquals(oriCredit2.subtract(prices.getDailyLottoPTTPrice_Month()).subtract(prices.getTotoSTTPrice()), cus2.getBankAccount().getCredit());
		
		assertEquals(1, cus3.getWeeklyLottoPTTs().size());
		assertEquals(1, cus3.getDailyLottoPTTs().size());
		assertEquals(oriCredit3.subtract(prices.getWeeklyLottoPTTPrice_Year()).subtract(prices.getDailyLottoPTTPrice_HalfYear()), cus3.getBankAccount().getCredit());
		
		assertEquals(1, cus4.getWeeklyLottoSTTs().size());
		assertEquals(oriCredit4.subtract(prices.getWeeklyLottoSTTPrice()), cus4.getBankAccount().getCredit());
		
		assertFalse(tp_res1);
		
		assertEquals(0, cus5.getWeeklyLottoPTTs().size());
		assertEquals(0, cus5.getDailyLottoPTTs().size());
		assertEquals(new CDecimal(0), cus5.getBankAccount().getCredit());
		
		assertEquals(8, Lottery.getInstance().getFinancialManagement().getTicketPurchases().size());
		
		WeeklyLottoSTT[] cus3WLSTTs = new WeeklyLottoSTT[15];
		for(int i = 0; i < 15; ++i)
		{
			cus3WLSTTs[i] = new WeeklyLottoSTT();
			cus3WLSTTs[i].purchase(cus3);
		}
		
		WeeklyLottoSTT[] cus1WLSTTs = new WeeklyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
		{
			cus1WLSTTs[i] = new WeeklyLottoSTT();
			cus1WLSTTs[i].purchase(cus1);
		}
		
		WeeklyLottoSTT[] cus2WLSTTs = new WeeklyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
		{
			cus2WLSTTs[i] = new WeeklyLottoSTT();
			cus2WLSTTs[i].purchase(cus2);
		}
		
		printCurrentTimeToConsol("Some people purchased TipTickets.");//<------------------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=========================================================================================================================//DRAW AND SINGLETIP TESTs No 1
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		WeeklyLottoDraw draw1 = new WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));
		
//		DailyLottoDraw draw2 = new DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(1));
//		TotoEvaluation eval1 = new TotoEvaluation(Lottery.getInstance().getTimer().getDateTime().plusDays(1));
		
		//cus1:
		int rcode1 = draw1.createAndSubmitSingleTip(ticket1, new int[]{1,2,3,4,5,6});//6 hits!
		((WeeklyLottoTip)(ticket1.getTip())).setSuperNumber(8);//+superNumber!
		
		//cus3:
		int rcode2 = draw1.createAndSubmitSingleTip(ticket5, new int[]{1,2,3,4,5,7});//5 hits + extraNumber
		((WeeklyLottoTip)(ticket5.getLastTip())).setSuperNumber(0);//irrelevant
		
		assertEquals(0, rcode1);
		assertEquals(0, rcode2);
		assertEquals(2, draw1.getSingleTips().size());
		
		assertEquals(true, (ticket1.getTip() != null));
		assertEquals(1, ticket5.getTips().size());
		
		for(int i = 0; i < 7; ++i)
		draw1.createAndSubmitSingleTip(cus3WLSTTs[i], new int[]{1,2,12,13,14,15});//2 hits
		
		draw1.createAndSubmitSingleTip(cus3WLSTTs[7], new int[]{1,2,3,7,14,15});//3 hits + extraNumber
		draw1.createAndSubmitSingleTip(cus3WLSTTs[8], new int[]{1,2,3,13,14,15});//3 hits
		draw1.createAndSubmitSingleTip(cus3WLSTTs[9], new int[]{1,2,3,13,14,15});//3 hits
		
		printCurrentTimeToConsol("Two people submitted tips to a WeeklyLottoDraw (draw1).");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=========================================================================================================================//GROUPTIP

		//cus1 creates group tip:
		WeeklyLottoGroupTip gwtip1 = new WeeklyLottoGroupTip(draw1, group1, 2, 5);
		
		//cus1 contributes tips:
		LinkedList<int[]> cus1_tipTips1 = new LinkedList<int[]>();
		cus1_tipTips1.add(new int[]{1,2,12,13,14,15});
		cus1_tipTips1.add(new int[]{1,2,12,13,14,15});
		
		LinkedList<TipTicket> cus1_tickets1 = new LinkedList<TipTicket>();
		cus1_tickets1.add(cus1WLSTTs[0]);
		cus1_tickets1.add(cus1WLSTTs[1]);
		
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus1_tickets1, cus1_tipTips1));
		assertEquals(2, gwtip1.getTips().size());
		assertEquals(2, gwtip1.getGroupMemberStake(cus1));
		
		assertEquals(1, gwtip1.getTips().getFirst().withdraw());
		assertEquals(false, gwtip1.submit());
		
		//cus2 contributes tips:
		LinkedList<int[]> cus2_tipTips1 = new LinkedList<int[]>();
		cus2_tipTips1.add(new int[]{1,2,12,13,14,15});
			
		LinkedList<TipTicket> cus2_tickets1 = new LinkedList<TipTicket>();
		cus2_tickets1.add(cus2WLSTTs[0]);
		
		assertEquals(6, gwtip1.createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1));
		
		cus2_tipTips1.add(new int[]{1,2,12,4,7,8});//3 hits + extraNumber
		cus2_tickets1.add(cus2WLSTTs[1]);
		
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1));
		
		assertEquals(false, gwtip1.submit());
		
		//cus3 contributes tips:
		LinkedList<int[]> cus3_tipTips1 = new LinkedList<int[]>();
		cus3_tipTips1.add(new int[]{1,2,12,13,14,15});
		cus3_tipTips1.add(new int[]{1,2,12,13,14,15});
		
		LinkedList<TipTicket> cus3_tickets1 = new LinkedList<TipTicket>();
		cus3_tickets1.add(cus3WLSTTs[0]);
		cus3_tickets1.add(cus3WLSTTs[1]);
		
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1));
		
		assertEquals(true, gwtip1.submit());
		assertEquals(true, gwtip1.unsubmit());
		assertEquals(true, gwtip1.submit());
		
		assertEquals(0, gwtip1.removeAllTipsOfGroupMember(cus3));
		assertEquals(false, gwtip1.submit());
		
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1));//re-submit tips
		
		assertEquals(true, gwtip1.submit());//re-submit group tip
		//=========================================================================================================================//SHORTLY BEFORE EVALUATION
	
		Lottery.getInstance().getTimer().addDays(7);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]	
		
//		Lottery.getInstance().getTimer().addMinutes(-4);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		Lottery.getInstance().getTimer().addMinutes(-140);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

//		DateTime peDate = Lottery.getInstance().getTimer().getDateTime();
//		DateTime endDate = new DateTime(peDate.getYear(), peDate.getMonthOfYear(), peDate.getDayOfMonth(), 0, 0, 0);//reset hours, minutes, seconds
//		System.out.println(endDate.toString());
//		Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), endDate);
//
//		System.out.println(duration.toString());
//		System.out.println(draw1.getPlanedEvaluationDate().toString());
//		assertEquals(false, duration.isLongerThan(new Duration(0)));
//		assertEquals(false, draw1.isTimeLeftUntilEvaluationForSubmission());
		assertEquals(false, gwtip1.unsubmit());

		//cus4:
		assertEquals(-2, draw1.createAndSubmitSingleTip(ticket7, new int[]{1,2,3,4,5,6}));
		
		printCurrentTimeToConsol("Another customer tried to submit but was too late. Also the group tip couldn't been 'unsubmitted'.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addMinutes(145);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//=========================================================================================================================//WEEKLYDRAW EVALUATION
		
		draw1.setResult(new int[]{1,2,3,4,5,6,7,8});
		draw1.evaluate();
		
		assertEquals(1, gwtip1.getAllWinnings().size());
		
		assertEquals(3, cus1.getBankAccount().getWinnings().size());
		assertEquals(2, cus2.getBankAccount().getWinnings().size());
		assertEquals(6, cus3.getBankAccount().getWinnings().size());
		
		assertEquals(1, draw1.getDrawEvaluationResult().getTipsInCategory(0).size());
		assertEquals(1, draw1.getDrawEvaluationResult().getTipsInCategory(2).size());
		assertEquals(2, draw1.getDrawEvaluationResult().getTipsInCategory(6).size());
		assertEquals(2, draw1.getDrawEvaluationResult().getTipsInCategory(7).size());
		
		assertEquals(11, Lottery.getInstance().getFinancialManagement().getWinnings().size());
		
		int[] shouldPrizeCat = new int[]{1,3,7,8,8,-1,-1,-1,-1,-1,-1};
		int i1 = 0;
		for(Winnings winnings : Lottery.getInstance().getFinancialManagement().getWinnings())
		{
			assertEquals(shouldPrizeCat[i1], winnings.getPrizeCategory());
			++i1;
		}
		
		Object[] tipsPerCategory = draw1.getDrawEvaluationResult().getTipsInCategory();
		for(int i = 0; i < 8; ++i)
		{
			System.out.print(((LinkedList<SingleTip>)(tipsPerCategory[i])).size());
			System.out.print(" ");
		}
		
		System.out.println(" ");
		
		//--------------------------------------------------------//check whether lower prize categories have lower/equal per tip winnings:
		CDecimal[] categoryWinnings = draw1.getDrawEvaluationResult().getCategoryWinningsMerged();
		for(int i = 7; i > 0; --i)
			if(((LinkedList<SingleTip>)(tipsPerCategory[i])).size() > 0 && ((LinkedList<SingleTip>)(tipsPerCategory[i-1])).size() > 0)
			assertEquals(true, categoryWinnings[i].compareTo(categoryWinnings[i-1]) < 1);
		//--------------------------------------------------------//
		
		//--------------------------------------------------------//check whether the distribution of the winners due sums up correctly:
		CDecimal[] jackpotImageBefore = draw1.getDrawEvaluationResult().getJackpotImageBefore();
		CDecimal[] jackpotImageAfter = draw1.getDrawEvaluationResult().getJackpotImageAfter();
		
		CDecimal checkWinnersDue = new CDecimal(0);
		for(int i = 0; i < 8; ++i)
		{
			if(((LinkedList<SingleTip>)(tipsPerCategory[i])).size() > 0)
			{
				checkWinnersDue = checkWinnersDue.add(categoryWinnings[i].multiply(new CDecimal(((LinkedList<SingleTip>)(tipsPerCategory[i])).size())));
			}
			else
			{
				checkWinnersDue = checkWinnersDue.add(jackpotImageAfter[i]);
			}
		}
		checkWinnersDue = checkWinnersDue.add(draw1.getDrawEvaluationResult().getNormalizationAmount());

		assertEquals(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getWinnersDue(), checkWinnersDue);
		//--------------------------------------------------------//
		
		//not auto-testing here, just some stuff for manual checks:
		for(CDecimal dec : draw1.getDrawEvaluationResult().getCategoryWinningsUnMerged())
			System.out.print(dec.toString() + " ");
		
		System.out.println(" ");
		
		for(CDecimal dec : categoryWinnings)
			System.out.print(dec.toString() + " ");
		
		System.out.println(" ");
		
		System.out.println(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getWinnersDue());
		System.out.println(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getTreasuryDue());
		System.out.println(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getLotteryTaxDue());
		System.out.println(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getManagementDue());
		
		for(CDecimal dec : jackpotImageBefore)
		System.out.print(dec.toString() + " ");
		
		System.out.println(" ");
		
		for(CDecimal dec : jackpotImageAfter)
		System.out.print(dec.toString() + " ");
		
		System.out.println(" ");
		
		System.out.println(draw1.getDrawEvaluationResult().getNormalizationAmount().toString());
		
		
//		int findNoti = 0;
//		for(Notification notification : cus1.getNotifications())
//			if(notification.getNote().matches("Sadly there is no evaluation code for the drawings so you never really had a chance to win something."))
//				++findNoti;
//		
//		assertEquals(1, findNoti);
//		
//		findNoti = 0;
//		for(Notification notification : cus3.getNotifications())
//			if(notification.getNote().matches("Sadly there is no evaluation code for the drawings so you never really had a chance to win something."))
//				++findNoti;
//		
//		assertEquals(1, findNoti);
//
//		CDecimal pricePotential = ticket1.getPaidPurchasePrice().add(ticket5.getPaidPurchasePrice());
//		assertEquals(
//				pricePotential.multiply(new CDecimal(Lottery.getInstance().getFinancialManagement().getReceiptsDistribution().getWinnersDue())).divide(new CDecimal(100)) 
//				,Lottery.getInstance().getFinancialManagement().getWeeklyLottoPrize());
		
		printCurrentTimeToConsol("WeeklyLottoDraw (draw1) has been evaluated.");//<------------------------------------------------------------------<TIMELINE UPDATE>
	}

//	@Test(expected=AssertionError.class)
//	  public void testAssertionsEnabled() {
//	    assert(false);
//	  }
	
	//	@After
	//	void cleanTest()
	//	{
	//		
	//	}

}
