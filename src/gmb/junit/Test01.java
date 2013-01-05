package gmb.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.LinkedList;

import gmb.model.CDecimal;
import gmb.model.DrawEventBox;
import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.TipTicketPrices;
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
import gmb.model.request.RequestState;
import gmb.model.request.data.MemberDataUpdateRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.request.group.GroupMembershipInvitation;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.draw.container.ExtendedEvaluationResult;
import gmb.model.tip.tip.group.TotoGroupTip;
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
import org.junit.Test;

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
	
	/**
	 * A test simulating user behavior in the system over a certain period of time.<br>
	 * Achieves decent code coverage in the model code (on average about 70% - 80% in each code file).
	 */
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
		printCurrentTimeToConsol("Lottery has been opend!");//<------------------------------------------------------------------------------------------------<TIMELINE UPDATE>
		assertNotNull(tipManagement);
		assertNotNull(Lottery.getInstance());
		assertNotNull(Lottery.getInstance().getTipManagement());
		assertNotNull(Lottery.getInstance().getTipManagement().getDailyLottoDrawings());
		Lottery.getInstance().getTimer().addMinutes(5);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		Lottery.getInstance().getTimer().resetDailyLottoDrawAutoCreation();//don't automatically create DailyLottoDrawings for now
		Lottery.getInstance().getTimer().resetTotoEvaluatioAutoEvaluation();
		
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

		assertEquals("Amanda", cus1.getMemberData().getFirstName());
		assertEquals("Jiggsaw", cus1.getMemberData().getLastName());
		assertEquals(new DateTime(1970,10,16,0,0), cus1.getMemberData().getBirthDate());
		assertEquals("0735643", cus1.getMemberData().getPhoneNumber());
		assertEquals("me@mail.gmb", cus1.getMemberData().getEMail());
		
		assertEquals("PimpfStreet", cus1Adress.getStreetName());
		assertEquals("044", cus1Adress.getHouseNumber());
		assertEquals("08653", cus1Adress.getPostCode());
		assertEquals("PimpfCity", cus1Adress.getTownName());
		
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
		
		assertEquals(1, cus5.getBankAccount().sendExternalTransactionRequest(new CDecimal(-100000), "Wanna move this money back to my real bank account.").var1.intValue());

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

		MemberDataUpdateRequest req01 = cus5.sendDataUpdateRequest(GmbFactory.new_MemberData("SATAN", "NATAS", new DateTime(999,1,1,0,0), "999999", "satan@natas.gmb", null), "I'm actually SATAN! Satan doesn't need an address!");
		
		assertEquals(2, Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests().size());
		
		//tired admin work, accepting nearly everything:
		for(MemberDataUpdateRequest request : Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests())
			if(request != req01)
				assertEquals(0, request.accept());

		for(RealAccountDataUpdateRequest request : Lottery.getInstance().getFinancialManagement().getRealAccounDataUpdateRequests())
			assertEquals(0, request.accept());
		System.out.println("");

		//but not this!
		assertEquals(true, req01.refuse());
		//very tired
		assertEquals(1, req01.accept());
		
		assertEquals("SATAN", req01.getUpdatedData().getFirstName());
		assertEquals("Karsten", cus5.getMemberData().getFirstName());
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
		for(GroupMembershipInvitation  invitation : group1.getGroupInvitations())
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

		assertEquals(3, group1.getGroupMembers().size());
		assertEquals(3, group2.getGroupMembers().size());

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

		assertEquals(cus1, group3.getGroupAdmin());

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
		assertEquals(null, group3.getGroupAdmin());
		assertEquals(RequestState.Withdrawn, ((LinkedList<GroupMembershipInvitation>)cus3.getGroupInvitations()).getLast().getState());
		//=========================================================================================================================//TIPTICKET TESTs NO 1

		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]


		CDecimal oriCredit1 = cus1.getBankAccount().getCredit();
		assertEquals(0, GmbFactory.createAndPurchase_WeeklyLottoSTT(cus1).var1.intValue());
		WeeklyLottoSTT ticket1 = GmbFactory.createAndPurchase_WeeklyLottoSTT(cus1).var2;
		assertEquals(0, GmbFactory.createAndPurchase_DailyLottoSTT(cus1).var1.intValue());

		CDecimal oriCredit2 = cus2.getBankAccount().getCredit();
		assertEquals(0, GmbFactory.createAndPurchase_TotoSTT(cus2).var1.intValue());
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
//		System.out.println(ticket5.getDurationDate().toString());
		assertEquals(false, ticket5.isExpired());
		assertEquals(0, draw1.check_createAndSubmitSingleTip(ticket5, new int[]{1,2,3,4,5,7}));
		assertEquals(0, draw1.createAndSubmitSingleTip(ticket5, new int[]{1,2,3,4,5,7}).var1.intValue());//5 hits + extraNumber
		((WeeklyLottoTip)(ticket5.getLastTip())).setSuperNumber(0);//irrelevant

		assertEquals(0, rcode1);
		assertEquals(2, draw1.getSingleTips().size());

		assertNotNull(ticket1.getTip());
		assertEquals(1, ticket5.getTips().size());

		for(int i = 0; i < 7; ++i)
			draw1.createAndSubmitSingleTip(cus3WLSTTs[i], new int[]{1,2,12,13,14,15});//2 hits

		draw1.createAndSubmitSingleTip(cus3WLSTTs[7], new int[]{1,2,3,7,14,15});//3 hits + extraNumber
		draw1.createAndSubmitSingleTip(cus3WLSTTs[8], new int[]{1,2,3,13,14,15});//3 hits
		SingleTip someTip1 = draw1.createAndSubmitSingleTip(cus3WLSTTs[9], new int[]{1,2,3,13,14,15}).var2;//3 hits
		
		assertEquals(3, someTip1.setTip(new int[]{1,2,3,13,94,15}));//94 
		assertEquals(3, someTip1.setTip(new int[]{1,2,3,13,-94,15}));//-94 
		assertEquals(4, someTip1.setTip(new int[]{1,1,3,13,3,15}));//2x1

		DateTime beforeSomeTip2Submission = Lottery.getInstance().getTimer().getDateTime();
		Lottery.getInstance().getTimer().addMinutes(1);
		SingleTip someTip2 = draw1.createAndSubmitSingleTip(cus3WLSTTs[10], new int[]{1,2,3,4,5,6}).var2;//6 hits
		Lottery.getInstance().getTimer().addMinutes(1);
		DateTime afterSomeTip2Submission = Lottery.getInstance().getTimer().getDateTime();
		
		assertEquals(true, beforeSomeTip2Submission.isBefore(someTip2.getSubmissionDate()));
		assertEquals(true, afterSomeTip2Submission.isAfter(someTip2.getSubmissionDate()));
		
		assertEquals(0, someTip2.check_withdraw());
		assertEquals(0, someTip2.withdraw());//fail :D
		assertEquals(null, cus3WLSTTs[10].getTip());
		
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

		assertEquals(0, gwtip1.check_createAndSubmitSingleTipList(cus1_tickets1, cus1_tipTips1));
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus1_tickets1, cus1_tipTips1).var1.intValue());
		assertEquals(2, gwtip1.getTips().size());
		assertEquals(2, gwtip1.getGroupMemberStake(cus1));

		assertEquals(1, gwtip1.getTips().getFirst().check_withdraw());
		assertEquals(1, gwtip1.getTips().getFirst().withdraw());
		assertEquals(false, gwtip1.submit());

		//cus2 contributes tips:
		LinkedList<int[]> cus2_tipTips1 = new LinkedList<int[]>();
		cus2_tipTips1.add(new int[]{1,2,12,13,14,15});

		LinkedList<TipTicket> cus2_tickets1 = new LinkedList<TipTicket>();
		cus2_tickets1.add(cus2WLSTTs[0]);

		assertEquals(6, gwtip1.check_createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1));
		assertEquals(6, gwtip1.createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1).var1.intValue());

		cus2_tipTips1.add(new int[]{1,2,12,4,7,8});//3 hits + extraNumber
		cus2_tickets1.add(cus2WLSTTs[1]);

		assertEquals(0, gwtip1.check_createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1));
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus2_tickets1, cus2_tipTips1).var1.intValue());

		assertEquals(false, gwtip1.submit());

		//cus3 contributes tips:
		LinkedList<int[]> cus3_tipTips1 = new LinkedList<int[]>();
		cus3_tipTips1.add(new int[]{1,2,12,13,14,15});
		cus3_tipTips1.add(new int[]{1,2,12,13,14,15});
		cus3_tipTips1.add(new int[]{44,45,46,47,33,35});//0

		LinkedList<TipTicket> cus3_tickets1 = new LinkedList<TipTicket>();
		cus3_tickets1.add(cus3WLSTTs[0]);
		cus3_tickets1.add(cus3WLSTTs[1]);
		cus3_tickets1.add(cus3WLSTTs[2]);

		assertEquals(0, gwtip1.check_createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1));
		ReturnBox<Integer, LinkedList<SingleTip>> box42 = gwtip1.createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1);
		assertEquals(0, box42.var1.intValue());
		
		LinkedList<SingleTip> tips = box42.var2;			

		assertEquals(0, gwtip1.check_removeSingleTip(tips.getLast()));
		assertEquals(0, gwtip1.removeSingleTip(tips.getLast()));
		assertEquals(3, gwtip1.check_removeSingleTip(tips.getLast()));
		assertEquals(3, gwtip1.removeSingleTip(tips.getLast()));
		
		assertEquals(true, gwtip1.submit());
		assertEquals(true, gwtip1.unsubmit());
		assertEquals(true, gwtip1.submit());

		assertEquals(0, gwtip1.check_removeAllTipsOfGroupMember(cus3));
		assertEquals(0, gwtip1.removeAllTipsOfGroupMember(cus3));
		assertEquals(false, gwtip1.submit());

		cus3_tickets1.removeLast();
		cus3_tipTips1.removeLast();
		
		assertEquals(0, gwtip1.check_createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1));//re-submit tips
		assertEquals(0, gwtip1.createAndSubmitSingleTipList(cus3_tickets1, cus3_tipTips1).var1.intValue());//re-submit tips

		assertEquals(true, gwtip1.submit());//re-submit group tip
		//=========================================================================================================================//SHORTLY BEFORE EVALUATION

		Lottery.getInstance().getTimer().addDays(7);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]	

		//		Lottery.getInstance().getTimer().addMinutes(-4);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		Lottery.getInstance().getTimer().addMinutes(-140);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		assertEquals(false, gwtip1.unsubmit());

		assertEquals(-1, gwtip1.check_removeAllTipsOfGroupMember(cus1));
		assertEquals(-1, gwtip1.removeAllTipsOfGroupMember(cus1));
		assertEquals(2, gwtip1.check_withdraw());
		assertEquals(2, gwtip1.withdraw());
		//cus4:
		assertEquals(-2, draw1.check_createAndSubmitSingleTip(ticket7, new int[]{1,2,3,4,5,6}));
		assertEquals(-2, draw1.createAndSubmitSingleTip(ticket7, new int[]{1,2,3,4,5,6}).var1.intValue());

		assertEquals(-1, someTip1.check_withdraw());
		assertEquals(-1, someTip1.withdraw());//too late
		
		printCurrentTimeToConsol("Another customer tried to submit but was too late. Also the group tip couldn't bee 'unsubmitted'.");//<------------------------------------------------------------------<TIMELINE UPDATE>	
		Lottery.getInstance().getTimer().addMinutes(145);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

		assertEquals(-2, someTip1.setTip(new int[]{1,2,3,13,4,15}));//too late
		
		//=========================================================================================================================//WEEKLYDRAW EVALUATION

		draw1.evaluate(new int[]{1,2,3,4,5,6,7,8});

		assertNotNull(someTip1.getOverallWinnings());
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

		assertNotNull(cus1DLSTTs[0].getTip());

		for(int i = 0; i < 7; ++i)
			draw2.createAndSubmitSingleTip(cus3DLSTTs[i], new int[]{0,0,0,0,0,0,0,0,0,1});//0 hits

		draw2.createAndSubmitSingleTip(cus3DLSTTs[7], new int[]{1,2,3,0,0,0,0,0,0,0});//3 hits
		draw2.createAndSubmitSingleTip(cus3DLSTTs[8], new int[]{1,2,0,4,5,6,7,8,9,0});//2 hits
		draw2.createAndSubmitSingleTip(cus3DLSTTs[9], new int[]{0,2,3,4,5,6,7,8,9,0});//0 hits

		printCurrentTimeToConsol("Three people submitted tips to a DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>

		//=========================================================================================================================//DAILYDRAW EVALUATION

		draw2.setResult(new int[]{1,2,3,4,5,6,7,8,9,0});

		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		Lottery.getInstance().getTimer().addHours(20);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		assertEquals(false, draw2.getEvaluated());	

		assertEquals(-2, draw2.check_createAndSubmitSingleTip(cus3DLSTTs[10], new int[]{0,2,3,4,5,6,7,8,9,0}));
		assertEquals(-2, draw2.createAndSubmitSingleTip(cus3DLSTTs[10], new int[]{0,2,3,4,5,6,7,8,9,0}).var1.intValue());//TOO LATE!!!
		
		printCurrentTimeToConsol("Auto-Evaluation of DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addHours(4);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]

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

		assertNotNull(cus1TSTTs[0].getTip());

		//cus3:
		draw3.createAndSubmitSingleTip(cus3TSTTs[0], new int[]{1,0,1,0,0,0,2,2,2});//4 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[1], new int[]{1,0,1,0,0,0,2,2,2});//4 hits

		draw3.createAndSubmitSingleTip(cus3TSTTs[2], new int[]{1,0,1,0,1,0,2,2,2});//5 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[3], new int[]{1,0,0,1,1,1,2,2,2});//8 hits
		draw3.createAndSubmitSingleTip(cus3TSTTs[4], new int[]{1,1,1,0,0,0,1,1,1});//0 hits

		printCurrentTimeToConsol("Three people submitted tips to a DailyLottoDraw (draw2).");//<------------------------------------------------------------------<TIMELINE UPDATE>

		//=========================================================================================================================//TOTO-EVAL EVALUATION
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		assertEquals(false,draw3.isEvaluated());
		assertEquals(true, draw3.evaluate(new int[]{1,1, 2,2, 0,0, 2,0, 1,0, 3,2, 1,3, 0,2, 0,1}));

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
		assertEquals(5, pptTestDraw1.check_createAndSubmitSingleTip(pptTicketM1, new int[]{0,0,0,0,0,0,0,0,0,0}));
		assertEquals(5, pptTestDraw1.createAndSubmitSingleTip(pptTicketM1, new int[]{0,0,0,0,0,0,0,0,0,0}).var1.intValue());
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		printCurrentTimeToConsol("two days later.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		System.out.println( pptTicketM1.getDurationDate().toString());
		assertEquals(-1, pptTestDraw1.check_createAndSubmitSingleTip(pptTicketM2, new int[]{0,0,0,0,0,0,0,0,0,0}));//duration expired
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
		//=========================================================================================================================//WEEKLY PERMA TIPS TEST:
		Lottery.getInstance().getTimer().addWeeks(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		System.out.println(Lottery.getInstance().getTipManagement().getDailyLottoDrawings().size());
		
		assertEquals(0, cus1.getBankAccount().sendExternalTransactionRequest(new CDecimal(400), "").var2.accept());
		assertEquals(0, cus2.getBankAccount().sendExternalTransactionRequest(new CDecimal(400), "").var2.accept());
		assertEquals(0, cus3.getBankAccount().sendExternalTransactionRequest(new CDecimal(400), "").var2.accept());
		
		WeeklyLottoPTT cus1WPPT01 = GmbFactory.createAndPurchase_WeeklyLottoPTT(cus1, PTTDuration.Month).var2;
		assertEquals(0, cus1WPPT01.setTip(new int[]{5,34,12,7,32,1}));//3
		
		WeeklyLottoPTT cus2WPPT01 = GmbFactory.createAndPurchase_WeeklyLottoPTT(cus2, PTTDuration.Halfyear).var2;
		assertEquals(0, cus2WPPT01.setTip(new int[]{4,2,1,13,32,12}));//3+extra, 3+extra
		
		WeeklyLottoPTT cus3WPPT01 = GmbFactory.createAndPurchase_WeeklyLottoPTT(cus3, PTTDuration.Month).var2;
		WeeklyLottoPTT cus3WPPT02 = GmbFactory.createAndPurchase_WeeklyLottoPTT(cus3, PTTDuration.Year).var2;
		assertEquals(0, cus3WPPT01.setTip(new int[]{8,2,4,1,44,45}));//4
		assertEquals(0, cus3WPPT02.setTip(new int[]{12,32,34,11,17,21}));//0, 3
		
		Lottery.getInstance().getTimer().addDays(4);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		WeeklyLottoDraw WLDrawX01 = GmbFactory.new_WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(2));
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(true, WLDrawX01.evaluate(new int[]{5,4,2,7,8,1,13,33}));
		
		assertEquals(3, WLDrawX01.getDrawEvaluationResult().getWinnings().size());
		assertEquals(1, WLDrawX01.getDrawEvaluationResult().getTipsInCategory(7).size());
		assertEquals(1, WLDrawX01.getDrawEvaluationResult().getTipsInCategory(6).size());
		assertEquals(1, WLDrawX01.getDrawEvaluationResult().getTipsInCategory(5).size());
		
		Lottery.getInstance().getTimer().addMonths(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		WeeklyLottoDraw WLDrawX02 = GmbFactory.new_WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(2));
		
		Lottery.getInstance().getTimer().addDays(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(true, WLDrawX02.evaluate(new int[]{5,34,12,7,32,1,2,3}));
		
		assertEquals(2, WLDrawX02.getDrawEvaluationResult().getWinnings().size());
		assertEquals(0, WLDrawX02.getDrawEvaluationResult().getTipsInCategory(1).size());
		assertEquals(1, WLDrawX02.getDrawEvaluationResult().getTipsInCategory(6).size());
		assertEquals(1, WLDrawX02.getDrawEvaluationResult().getTipsInCategory(7).size());
		
		printCurrentTimeToConsol("Weekly perma tips have been tested.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		//=========================================================================================================================//DAILY PERMA TIPS TEST:
		Lottery.getInstance().getTimer().addWeeks(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		DailyLottoPTT cus1DPPT01 = GmbFactory.createAndPurchase_DailyLottoPTT(cus1, PTTDuration.Month).var2;
		assertEquals(0, cus1DPPT01.setTip(new int[]{0,1,2,3,4,5,6,7,8,9}));//
		
		DailyLottoPTT cus2DPPT01 = GmbFactory.createAndPurchase_DailyLottoPTT(cus2, PTTDuration.Halfyear).var2;
		assertEquals(0, cus2DPPT01.setTip(new int[]{0,1,2,3,4,5,6,7,8,9}));//
		
		DailyLottoPTT cus3DPPT01 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Month).var2;
		DailyLottoPTT cus3DPPT02 = GmbFactory.createAndPurchase_DailyLottoPTT(cus3, PTTDuration.Year).var2;
		assertEquals(0, cus3DPPT01.setTip(new int[]{0,1,2,0,4,5,6,7,8,9}));//
		assertEquals(0, cus3DPPT02.setTip(new int[]{0,1,2,0,0,5,6,7,8,9}));//
		
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		Lottery.getInstance().getTimer().setDailyLottoDrawAutoCreation();
		DailyLottoDraw DLDrawX01 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(2));
		DLDrawX01.setResult(new int[]{0,1,2,0,0,0,0,0,0,0});
		
		Lottery.getInstance().getTimer().addDays(3);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(4, DLDrawX01.getDrawEvaluationResult().getWinnings().size());
		assertEquals(2, DLDrawX01.getDrawEvaluationResult().getTipsInCategory(7).size());
		assertEquals(1, DLDrawX01.getDrawEvaluationResult().getTipsInCategory(6).size());
		assertEquals(1, DLDrawX01.getDrawEvaluationResult().getTipsInCategory(5).size());
		
		assertEquals(false, ((LinkedList<DailyLottoDraw>)(Lottery.getInstance().getTipManagement().getDailyLottoDrawings())).getLast().isEvaluated());
		Lottery.getInstance().getTimer().resetDailyLottoDrawAutoCreation();
		Lottery.getInstance().getTimer().resetDailyLottoDrawAutoEvaluation();
		
		DailyLottoDraw  DLDrawX021 = ((LinkedList<DailyLottoDraw>)(Lottery.getInstance().getTipManagement().getDailyLottoDrawings())).getLast();//the last automatically created draw
		assertEquals(true, DLDrawX021.evaluate(new int[]{0,1,2,3,0,0,0,0,0,0}));
		assertEquals(4, DLDrawX021.getDrawEvaluationResult().getWinnings().size());
		
		Lottery.getInstance().getTimer().addMonths(2);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		DailyLottoDraw  DLDrawX02 = GmbFactory.new_DailyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(1));
		
		Lottery.getInstance().getTimer().addDays(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		assertEquals(false, DLDrawX02.isEvaluated());
		DLDrawX02.setResult(new int[]{0,1,2,3,0,0,0,0,0,0});
		assertEquals(true, DLDrawX02.evaluate(null));

		assertEquals(true, DLDrawX02.isEvaluated());
		
		assertEquals(2, DLDrawX02.getDrawEvaluationResult().getWinnings().size());
		assertEquals(1, DLDrawX02.getDrawEvaluationResult().getTipsInCategory(7).size());
		assertEquals(1, DLDrawX02.getDrawEvaluationResult().getTipsInCategory(6).size());
		
		printCurrentTimeToConsol("Daily perma tips have been tested.");//<------------------------------------------------------------------<TIMELINE UPDATE>
		
		//=========================================================================================================================//(TOTO-) GROUPTIP WITHDRAWING
		
		Lottery.getInstance().getTimer().addWeeks(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		ArrayList<FootballGameData> gameData2 = new ArrayList<FootballGameData>(9);

		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Namo", "Schland"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Schaft", "Mann"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "HSV", "FSV"));

		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Nemo", "Minime"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Bubble", "Bert"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Klos", "Moos"));

		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Stoss", "Noss"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Hammel", "Bammel"));
		gameData2.add(GmbFactory.new_FootballGameData(new DateTime(), "Gammel", "Rammel"));
		
		TotoEvaluation TotoEvalX01 = GmbFactory.new_TotoEvaluation(Lottery.getInstance().getTimer().getDateTime().plusDays(2), gameData2);
		
		assertEquals(0, cus5.getBankAccount().sendExternalTransactionRequest(new CDecimal(400), "That much money I actually have.").var2.accept());
		assertEquals(0, cus4.getBankAccount().sendExternalTransactionRequest(new CDecimal(400), "").var2.accept());
		
		TotoSTT cus4TSTT01 = GmbFactory.createAndPurchase_TotoSTT(cus4).var2;
		
		TotoSTT cus5TSTT01 = GmbFactory.createAndPurchase_TotoSTT(cus5).var2;
		TotoSTT cus5TSTT02 = GmbFactory.createAndPurchase_TotoSTT(cus5).var2;
		
		TotoGroupTip TotoGTX01 = GmbFactory.new_TotoGroupTip(TotoEvalX01, group2, 1, 2);
		
		LinkedList<TipTicket> cus4TotoTickets = new LinkedList<TipTicket>();
		cus4TotoTickets.add(cus4TSTT01);
		
		LinkedList<int[]> cus4TotoTips = new LinkedList<int[]>();
		cus4TotoTips.add(new int[]{0,0,0,0,0,0,0,0,0});
		
		SingleTip cus4STTtip = TotoGTX01.createAndSubmitSingleTipList(cus4TotoTickets, cus4TotoTips).var2.getFirst();
		
		LinkedList<TipTicket> cus5TotoTickets = new LinkedList<TipTicket>();
		cus5TotoTickets.add(cus5TSTT01);
		
		LinkedList<int[]> cus5TotoTips = new LinkedList<int[]>();
		cus5TotoTips.add(new int[]{0,0,0,0,0,0,0,0,0});
		
		TotoGTX01.createAndSubmitSingleTipList(cus5TotoTickets, cus5TotoTips);
		
		assertEquals(true, TotoGTX01.submit());
		
		assertEquals(true, TotoGTX01.isSubmitted());
		assertEquals(0, TotoGTX01.check_removeSingleTip(cus4STTtip));
		assertEquals(0, TotoGTX01.removeSingleTip(cus4STTtip));
		assertEquals(false, TotoGTX01.isSubmitted());
		
		LinkedList<TipTicket> cus5TotoTickets2 = new LinkedList<TipTicket>();
		cus5TotoTickets2.add(cus5TSTT02);
		
		LinkedList<int[]> cus5TotoTips2 = new LinkedList<int[]>();
		cus5TotoTips2.add(new int[]{0,0,0,0,0,0,0,0,0});
		
		TotoGTX01.createAndSubmitSingleTipList(cus5TotoTickets2, cus5TotoTips2);
		
		assertEquals(true, TotoGTX01.submit());
		assertEquals(0, TotoGTX01.check_withdraw());
		assertEquals(0, TotoGTX01.withdraw());
		
		assertNull(cus4TSTT01.getTip());
		assertNull(cus4TSTT01.getTip());
		assertNull(cus4TSTT01.getTip());
		
		assertEquals(false, TotoEvalX01.isEvaluated());
		Lottery.getInstance().getTimer().setTotoEvaluatioAutoEvaluation();
		Lottery.getInstance().getTimer().addDays(3);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		assertEquals(true, TotoEvalX01.isEvaluated());
		Lottery.getInstance().getTimer().resetTotoEvaluatioAutoEvaluation();
		
		printCurrentTimeToConsol("Withdrawing of group tips has been tested.");//<------------------------------------------------------------------<TIMELINE UPDATE>
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
