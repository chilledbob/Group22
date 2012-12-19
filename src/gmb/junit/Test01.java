package gmb.junit;

import static org.junit.Assert.*;

import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gmb.model.CDecimal;
import gmb.model.DrawEventBox;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.ReturnBox;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.financial.container.ReceiptsDistribution;
import gmb.model.financial.container.TipTicketPrices;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.group.GroupManagement;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.member.MemberType;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.Notification;
import gmb.model.request.RequestState;
import gmb.model.request.data.MemberDataUpdateRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.draw.container.ExtendedEvaluationResult;
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
import org.junit.BeforeClass;
import org.junit.Test;
import org.salespointframework.core.database.Database;
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
		Adress stdAdress = GmbFactory.new_Adress("SomeStreet", "111", "010101", "SomeOtherCity");
		MemberData stdData = GmbFactory.new_MemberData(name, "Mueller", new DateTime(1970,10,16,0,0), "0735643", "someone@mail.gmb", stdAdress);
		LotteryBankAccount stdbankacc = GmbFactory.new_LotteryBankAccount(GmbFactory.new_RealAccountData("0303003", "0340400"));
		return  GmbFactory.new_Customer(name+"_nick", "notsafepassword", stdData, stdbankacc);
	}

	Member admin1;
	Customer cus1;
	Customer cus2;
	Customer cus3;
	Customer cus4;
	Customer cus5;

	Group group1;
	Group group2;
	Group group3;
	Group group4;

	//	@BeforeClass
	//	public static void beforeClass(){ Database.INSTANCE.initializeEntityManagerFactory("Lotterie"); }

	@Test
	public void MasterTest()
	{
		//=========================================================================================================================//USER TESTs NO 1

		FinancialManagement financialManagement = new FinancialManagement(GmbFactory.new_TipTicketPrices(), GmbFactory.new_ReceiptsDistribution());
		MemberManagement memberManagement = GmbFactory.new_MemberManagement();
		GroupManagement groupManagement = GmbFactory.new_GroupManagement();
		TipManagement tipManagement = GmbFactory.new_TipManagement();

		Lottery.Instanciate(financialManagement, memberManagement, groupManagement, tipManagement);
		Lottery.getInstance().getTimer().setReferenceDate(new DateTime(2013,1,1,0,0));//HAPPY NEW YEAR! ..it's now..belief it!  
		printCurrentTimeToConsol("Lottery has opend!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addMinutes(5);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		Adress admin1Adress = GmbFactory.new_Adress("Eich Strasse", "18", "90378", "SomeTown");
		MemberData admin1Data = GmbFactory.new_MemberData("Heinrich", "Siegel", new DateTime(1950,1,1,0,0), "892537", "heino@mail.gmb", admin1Adress);
		admin1 = GmbFactory.new_Member("MegaAdmin", "secretpasswordnonumbers", admin1Data, MemberType.Admin);
		Lottery.getInstance().getMemberManagement().addMember(admin1);

		printCurrentTimeToConsol("We have an Admin now!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addHours(8);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		Adress cus1Adress = GmbFactory.new_Adress("PimpfStreet", "044", "08653", "PimpfCity");
		MemberData cus1Data = GmbFactory.new_MemberData("Amanda", "Jiggsaw", new DateTime(1970,10,16,0,0), "0735643", "me@mail.gmb", cus1Adress);
		LotteryBankAccount cus1bankacc = GmbFactory.new_LotteryBankAccount(GmbFactory.new_RealAccountData("869823", "0387934"));
		cus1 = GmbFactory.new_Customer("MiniAmanda", "iwannaplayagame", cus1Data, cus1bankacc);
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
				request.getMember().addNotification("No so much money! You evil!");
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

		Adress cus2Adress = GmbFactory.new_Adress("Bahn Strasse", "96", "436747", "BahnStadt");
		MemberData cus2Data = GmbFactory.new_MemberData("Heino", "Heini", new DateTime(1980,10,10,0,0), "74375878", "heinoWHOALREADYTOOKMYNAME@DieVerdammteBahnKommtIMMERzuSpaet.gmb", cus2Adress);

		cus2.sendDataUpdateRequest(cus2Data, "I want real member data! You've created me with default data! D:");
		cus3.getBankAccount().sendDataUpdateRequest(GmbFactory.new_RealAccountData("839843789", "7885758"), "Hello, please accept my update. Thanks.");

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

		group1 = GmbFactory.new_Group("Cutie Mark Crusaders", cus1, "We are a group of people who like to play all kind of lotto and stuff! SRSLY!");
		group1.sendGroupInvitation(cus2, "Love you, come in my group!");
		GroupMembershipApplication inv1 = group1.sendGroupInvitation(cus5, "Love for all! Even for ya!");
		group1.applyForMembership(cus3, "Hello, please accept!");

		group2 = GmbFactory.new_Group("ExclusiveGroupDLX", cus5, "Only rich people without real money allowed!");
		group2.applyForMembership(cus4, "");
		group2.sendGroupInvitation(cus2, "My group DLX! Come!");
		group2.applyForMembership(cus3, "Hello, please accept!");

		Lottery.getInstance().getTimer().addHours(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		//smart cus2 browsing his lists:
		for(GroupMembershipApplication invitation : cus2.getGroupInvitations())
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
		for(GroupMembershipApplication invitation : group1.getGroupInvitations())
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
		for(GroupMembershipApplication invitation : cus5.getGroupInvitations())
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

		assertEquals(RequestState.Withdrawn, inv1.getState());

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
		assertEquals(RequestState.Withdrawn, ((LinkedList<GroupMembershipApplication>)cus3.getGroupInvitations()).getLast().getState());
		//=========================================================================================================================//TIPTICKET TESTs NO 1

		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]


		CDecimal oriCredit1 = cus1.getBankAccount().getCredit();
		WeeklyLottoSTT ticket0 = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus1).var2;
		WeeklyLottoSTT ticket1 = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus1).var2;
		DailyLottoSTT ticket2 = GmbFactory.createAndPurchase_DailyLottoSTT(cus1).var2;

		CDecimal oriCredit2 = cus2.getBankAccount().getCredit();
		TotoSTT ticket3 = GmbFactory.createAndPurchase_TotoSTT(cus2).var2;
		DailyLottoPTT ticket4 = GmbFactory.createAndPurchase_DailyLottoPTT(cus2, PTTDuration.Month).var2;


		CDecimal oriCredit3 = cus3.getBankAccount().getCredit();
		WeeklyLottoPTT ticket5 = GmbFactory.createAndPurchase_WeeklyLottoPTT(cus3, PTTDuration.Year).var2;
		DailyLottoPTT ticket6 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Halfyear).var2;

		CDecimal oriCredit4 = cus4.getBankAccount().getCredit();
		WeeklyLottoSTT ticket7 = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus4).var2;


		ReturnBox<Integer, WeeklyLottoPTT> ticket8_box= GmbFactory.createAndPurchase_WeeklyLottoPTT(cus5, PTTDuration.Year);

		DailyLottoPTT ticket9 = GmbFactory.createAndPurchase_DailyLottoPTT(cus5, PTTDuration.Year).var2;

		TipTicketPrices prices = new TipTicketPrices(null);

		assertEquals(0, ticket4.getDurationTypeAsInt());
		assertEquals(2, ticket5.getDurationTypeAsInt());
		assertEquals(1, ticket6.getDurationTypeAsInt());
		assertEquals(2, ticket8_box.var2.getDurationTypeAsInt());
		assertEquals(2, ticket9.getDurationTypeAsInt());

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

		assertEquals(1, ticket8_box.var1.intValue());

		assertEquals(0, cus5.getWeeklyLottoPTTs().size());
		assertEquals(0, cus5.getDailyLottoPTTs().size());
		assertEquals(new CDecimal(0), cus5.getBankAccount().getCredit());

		assertEquals(8, Lottery.getInstance().getFinancialManagement().getTicketPurchases().size());

		WeeklyLottoSTT[] cus3WLSTTs = new WeeklyLottoSTT[15];
		for(int i = 0; i < 15; ++i)
			cus3WLSTTs[i] = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus3).var2;

		WeeklyLottoSTT[] cus1WLSTTs = new WeeklyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus1WLSTTs[i] = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus1).var2;

		WeeklyLottoSTT[] cus2WLSTTs = new WeeklyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus2WLSTTs[i] = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus2).var2;

		//-----------//

		DailyLottoSTT[] cus3DLSTTs = new DailyLottoSTT[15];
		for(int i = 0; i < 15; ++i)
			cus3DLSTTs[i] = GmbFactory.createAndPurchase_DailyLottoSTT(cus3).var2;

		DailyLottoSTT[] cus1DLSTTs = new DailyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus1DLSTTs[i] = GmbFactory.createAndPurchase_DailyLottoSTT(cus1).var2;

		DailyLottoSTT[] cus2DLSTTs = new DailyLottoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus2DLSTTs[i] = GmbFactory.createAndPurchase_DailyLottoSTT(cus2).var2;

		//-----------//

		TotoSTT[] cus3TSTTs = new TotoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus3TSTTs[i] = GmbFactory.createAndPurchase_TotoSTT(cus3).var2;

		TotoSTT[] cus1TSTTs = new TotoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus1TSTTs[i] = GmbFactory.createAndPurchase_TotoSTT(cus1).var2;

		TotoSTT[] cus2TSTTs = new TotoSTT[5];
		for(int i = 0; i < 5; ++i)
			cus2TSTTs[i] = GmbFactory.createAndPurchase_TotoSTT(cus2).var2;

		printCurrentTimeToConsol("Some people purchased TipTickets.");//<------------------------------------------------------------------------------<TIMELINE UPDATE>

		//=========================================================================================================================//DRAW AND SINGLETIP TESTs No 1

		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		WeeklyLottoDraw draw1 = GmbFactory.new_WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(7));

		//		DailyLottoDraw draw2 = new DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(1));
		//		TotoEvaluation eval1 = new TotoEvaluation(Lottery.getInstance().getTimer().getDateTime().plusDays(1));

		//cus1:
		int rcode1 = draw1.createAndSubmitSingleTip(ticket1, new int[]{1,2,3,4,5,6}).var1.intValue();//6 hits!
		((WeeklyLottoTip)(ticket1.getTip())).setSuperNumber(8);//+superNumber!

		//cus3:
		int rcode2 = draw1.createAndSubmitSingleTip(ticket5, new int[]{1,2,3,4,5,7}).var1.intValue();//5 hits + extraNumber
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
		WeeklyLottoGroupTip gwtip1 = GmbFactory.new_WeeklyLottoGroupTip(draw1, group1, 2, 5);

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
		assertEquals(-2, draw1.createAndSubmitSingleTip(ticket7, new int[]{1,2,3,4,5,6}).var1.intValue());

		printCurrentTimeToConsol("Another customer tried to submit but was too late. Also the group tip couldn't been 'unsubmitted'.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addMinutes(145);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		//=========================================================================================================================//WEEKLYDRAW EVALUATION


		draw1.evaluate(new int[]{1,2,3,4,5,6,7,8});

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

		ArrayList<LinkedList<SingleTip>> tipsPerCategory = draw1.getDrawEvaluationResult().getTipsInCategory();
		for(int i = 0; i < 8; ++i)
		{
			System.out.print(tipsPerCategory.get(i).size());
			System.out.print(" ");
		}

		System.out.println(" ");

		//--------------------------------------------------------//check whether lower prize categories have lower/equal per tip winnings:
		ExtendedEvaluationResult wDrawEvaluationResult = (ExtendedEvaluationResult) draw1.getDrawEvaluationResult();
		ArrayList<CDecimal> categoryWinnings = wDrawEvaluationResult.getCategoryWinningsMerged();
		for(int i = 7; i > 0; --i)
			if(tipsPerCategory.get(i).size() > 0 && tipsPerCategory.get(i-1).size() > 0)
				assertEquals(true, categoryWinnings.get(i).compareTo(categoryWinnings.get(i-1)) < 1);
		//--------------------------------------------------------//

		//--------------------------------------------------------//check whether the distribution of the winners due sums up correctly:
		ArrayList<CDecimal> jackpotImageBefore = wDrawEvaluationResult.getJackpotImageBefore();
		ArrayList<CDecimal> jackpotImageAfter = wDrawEvaluationResult.getJackpotImageAfter();

		CDecimal checkWinnersDue = new CDecimal(0);
		for(int i = 0; i < 8; ++i)
		{
			if(tipsPerCategory.get(i).size() > 0)
			{
				checkWinnersDue = checkWinnersDue.add(categoryWinnings.get(i).multiply(new CDecimal(tipsPerCategory.get(i).size())));
			}
			else
			{
				checkWinnersDue = checkWinnersDue.add(jackpotImageAfter.get(i));
			}
		}
		checkWinnersDue = checkWinnersDue.add(wDrawEvaluationResult.getNormalizationAmount());

		assertEquals(draw1.getDrawEvaluationResult().getReceiptsDistributionResult().getWinnersDue(), checkWinnersDue);
		//--------------------------------------------------------//

		//not auto-testing here, just some stuff for manual checks:
		for(CDecimal dec : wDrawEvaluationResult.getCategoryWinningsUnMerged())
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

		System.out.println(wDrawEvaluationResult.getNormalizationAmount().toString());


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

		//=========================================================================================================================//DRAW AND SINGLETIP TESTs No 2

		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		DailyLottoDraw draw2 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(2));

		//cus1:
		int rcode11 = draw2.createAndSubmitSingleTip(cus1DLSTTs[0], new int[]{1,2,3,4,5,6,7,8,9,0}).var1.intValue();//10 hits!

		//cus3:
		int rcode22 = draw2.createAndSubmitSingleTip(cus2DLSTTs[0], new int[]{1,2,3,4,5,6,0,0,0,0}).var1.intValue();//6 hits!

		assertEquals(0, rcode11);
		assertEquals(0, rcode22);
		assertEquals(2, draw2.getSingleTips().size());

		assertEquals(true, (cus1DLSTTs[0].getTip() != null));

		for(int i = 0; i < 7; ++i)
			draw2.createAndSubmitSingleTip(cus3DLSTTs[i], new int[]{0,0,0,0,0,0,0,0,0,1});//0 hits

		draw2.createAndSubmitSingleTip(cus3DLSTTs[7], new int[]{1,2,3,0,0,0,0,0,0,0});//3 hits
		draw2.createAndSubmitSingleTip(cus3DLSTTs[8], new int[]{1,2,0,4,5,6,7,8,9,0});//2 hits
		draw2.createAndSubmitSingleTip(cus3DLSTTs[9], new int[]{0,2,3,4,5,6,7,8,9,0});//0 hits

		printCurrentTimeToConsol("Three people submitted tips to a DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>

		//=========================================================================================================================//DAILYDRAW EVALUATION

		draw2.setResult(new int[]{1,2,3,4,5,6,7,8,9,0});

		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		assertEquals(false, draw2.getEvaluated());	

		int rcode33 = draw2.createAndSubmitSingleTip(cus3DLSTTs[10], new int[]{0,2,3,4,5,6,7,8,9,0}).var1;//TOO LATE!!!
		assertEquals(-2, rcode33);
		
		printCurrentTimeToConsol("Auto-Evaluation of DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		assertEquals(true, draw2.getEvaluated());

		assertEquals(3+1, cus1.getBankAccount().getWinnings().size());
		assertEquals(2+1, cus2.getBankAccount().getWinnings().size());
		assertEquals(6+2, cus3.getBankAccount().getWinnings().size());

		assertEquals(1, draw2.getDrawEvaluationResult().getTipsInCategory(0).size());
		assertEquals(1, draw2.getDrawEvaluationResult().getTipsInCategory(4).size());
		assertEquals(1, draw2.getDrawEvaluationResult().getTipsInCategory(7).size());
		assertEquals(1, draw2.getDrawEvaluationResult().getTipsInCategory(8).size());

		assertEquals(11+4, Lottery.getInstance().getFinancialManagement().getWinnings().size());

		int[] shouldPrizeCat2 = new int[]{1,3,7,8,8,-1,-1,-1,-1,-1,-1, 1, 5, 8, 9};
		int i2 = 0;
		for(Winnings winnings : Lottery.getInstance().getFinancialManagement().getWinnings())
		{
			assertEquals(shouldPrizeCat2[i2], winnings.getPrizeCategory());
			++i2;
		}

		System.out.println(" ");

		//=========================================================================================================================//DRAW AND SINGLETIP TESTs No 3

		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		ArrayList<FootballGameData> gameData = new ArrayList<FootballGameData>(9);

		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Namo", "Schland"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Schaft", "Mann"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "HSV", "FSV"));

		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Nemo", "Minime"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Bubble", "Bert"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Klos", "Moos"));

		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Stoss", "Noss"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Hammel", "Bammel"));
		gameData.add(GmbFactory.new_FootballGameData(new DateTime(), "Gammel", "Rammel"));

		TotoEvaluation draw3 = GmbFactory.new_TotoEvaluation(Lottery.getInstance().getTimer().getDateTime().plusDays(2), gameData);

		//cus1:
		int rcode111 = draw3.createAndSubmitSingleTip(cus1TSTTs[0], new int[]{0,0,0,1,1,1,2,2,2}).var1.intValue();//9 hits!

		//cus2:
		int rcode222 = draw3.createAndSubmitSingleTip(cus2TSTTs[0], new int[]{1,0,0,0,1,1,1,2,2}).var1.intValue();//6 hits!

		assertEquals(0, rcode111);
		assertEquals(0, rcode222);
		assertEquals(2, draw3.getSingleTips().size());

		assertEquals(true, (cus1TSTTs[0].getTip() != null));

		//cus3:
		draw3.createAndSubmitSingleTip(cus3TSTTs[0], new int[]{1,0,1,0,0,0,2,2,2});//4 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[1], new int[]{1,0,1,0,0,0,2,2,2});//4 hits

		draw3.createAndSubmitSingleTip(cus3TSTTs[2], new int[]{1,0,1,0,1,0,2,2,2});//5 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[3], new int[]{1,0,0,1,1,1,2,2,2});//8 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[4], new int[]{1,1,1,0,0,0,1,1,1});//0 hits

		printCurrentTimeToConsol("Three people submitted tips to a DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>

		//=========================================================================================================================//TOTO-EVAL EVALUATION
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		draw3.evaluate(new int[]{1,1, 2,2, 0,0, 2,0, 1,0, 3,2, 1,3, 0,2, 0,1});

		assertEquals(1, draw3.getDrawEvaluationResult().getTipsInCategory(0).size());
		assertEquals(1, draw3.getDrawEvaluationResult().getTipsInCategory(1).size());
		assertEquals(0, draw3.getDrawEvaluationResult().getTipsInCategory(2).size());
		assertEquals(1, draw3.getDrawEvaluationResult().getTipsInCategory(3).size());
		assertEquals(1, draw3.getDrawEvaluationResult().getTipsInCategory(4).size());
		
		assertEquals(3+1+1, cus1.getBankAccount().getWinnings().size());
		assertEquals(2+1+1, cus2.getBankAccount().getWinnings().size());
		assertEquals(6+2+2, cus3.getBankAccount().getWinnings().size());
		
		assertEquals(11+4+4, Lottery.getInstance().getFinancialManagement().getWinnings().size());

		int[] shouldPrizeCat3 = new int[]{1,3,7,8,8,-1,-1,-1,-1,-1,-1, 1, 5, 8, 9, 1, 2, 4, 5};
		int i3 = 0;
		for(Winnings winnings : Lottery.getInstance().getFinancialManagement().getWinnings())
		{
			assertEquals(shouldPrizeCat3[i3], winnings.getPrizeCategory());
			++i3;
		}			
		
		printCurrentTimeToConsol("TotoEvaluatation (draw3) has been evaluated.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		//=========================================================================================================================//
		Lottery.getInstance().getTimer().addDays(5);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		DrawEventBox box = Lottery.getInstance().getTipManagement().getDrawEventsOnDate(Lottery.getInstance().getTimer().getDateTime().minusDays(5));
		
		assertEquals(0, box.getWeeklyLottoDrawings().size());
		assertEquals(0, box.getDailyLottoDrawings().size());
		assertEquals(1, box.getTotoEvaluations().size());
		
		printCurrentTimeToConsol("Checked some drawing events.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=========================================================================================================================//
		
		WeeklyLottoDraw draw0 = GmbFactory.new_WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime());
		draw0.evaluate(new int[]{0,0,0,0,0,0,0,0});
		
		assertEquals(0, draw0.getDrawEvaluationResult().getWinnings().size());
		
		printCurrentTimeToConsol("A drawing without submitted tickets has been evaluated.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=========================================================================================================================//PERMA-TT DURATION TEST:
		
		DailyLottoPTT pptTicketM1 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Month).var2;
		DailyLottoPTT pptTicketM2 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Month).var2;
		
		Lottery.getInstance().getTimer().addMonths(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		Lottery.getInstance().getTimer().addDays(-1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		printCurrentTimeToConsol("A month days later minus one day.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		DailyLottoDraw pptTestDraw1 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(4));
		pptTestDraw1.setResult(new int[]{1,1,1,1,1,1,1,1,1,1});
		assertEquals(0, pptTestDraw1.createAndSubmitSingleTip(pptTicketM1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		assertEquals(5, pptTestDraw1.createAndSubmitSingleTip(pptTicketM1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		printCurrentTimeToConsol("two days later.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		System.out.println( pptTicketM1.getDurationDate().toString());
		assertEquals(-1, pptTestDraw1.createAndSubmitSingleTip(pptTicketM2, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());//duration expired
		
		//===========================================//
		
		DailyLottoPTT pptTicketHY1 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Halfyear).var2;
		DailyLottoPTT pptTicketHY2 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Halfyear).var2;
		
		Lottery.getInstance().getTimer().addMonths(6);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		Lottery.getInstance().getTimer().addDays(-1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		DailyLottoDraw pptTestDraw2 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(4));
		pptTestDraw2.setResult(new int[]{1,1,1,1,1,1,1,1,1,1});
		
		assertEquals(1, pptTicketHY1.getDurationTypeAsInt());
		
		assertEquals(0, pptTestDraw2.createAndSubmitSingleTip(pptTicketHY1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		assertEquals(5, pptTestDraw2.createAndSubmitSingleTip(pptTicketHY1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(-1, pptTestDraw2.createAndSubmitSingleTip(pptTicketHY2, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());//duration expired
		
		//===========================================//
		
		DailyLottoPTT pptTicketY1 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Year).var2;
		DailyLottoPTT pptTicketY2 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Year).var2;
		
		Lottery.getInstance().getTimer().addYears(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		Lottery.getInstance().getTimer().addDays(-1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		DailyLottoDraw pptTestDraw3 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(4));
		pptTestDraw3.setResult(new int[]{1,1,1,1,1,1,1,1,1,1});
		
		assertEquals(2, pptTicketY1.getDurationTypeAsInt());
		
		assertEquals(0, pptTestDraw3.createAndSubmitSingleTip(pptTicketY1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		assertEquals(5, pptTestDraw3.createAndSubmitSingleTip(pptTicketY1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(-1, pptTestDraw3.createAndSubmitSingleTip(pptTicketY2, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());//duration expired
		
		printCurrentTimeToConsol("PermaTT duration stuff has been tested.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		//=========================================================================================================================//
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
