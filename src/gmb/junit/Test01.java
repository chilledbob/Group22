package gmb.junit;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.financial.ExternalTransaction;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.RealAccountData;
import gmb.model.financial.ReceiptsDistribution;
import gmb.model.financial.TipTicketPrices;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.GroupInvitation;
import gmb.model.request.GroupMembershipApplication;
import gmb.model.request.MemberDataUpdateRequest;
import gmb.model.request.Notification;
import gmb.model.request.RealAccountDataUpdateRequest;
import gmb.model.request.RequestState;
import gmb.model.tip.TipManagement;
import gmb.model.user.Admin;
import gmb.model.user.Adress;
import gmb.model.user.Customer;
import gmb.model.user.Group;
import gmb.model.user.GroupManagement;
import gmb.model.user.MemberData;
import gmb.model.user.MemberManagement;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
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
	
	@Test
	public void MasterTest()
	{
		//=========================================================================================================================//USER TESTs NO 1
		
		FinancialManagement financialManagement = new FinancialManagement(new TipTicketPrices(), new ReceiptsDistribution());
		MemberManagement memberManagement = new MemberManagement();
		GroupManagement groupManagement = new GroupManagement("");
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

		cus1.getBankAccount().sendExternalTransactionRequest(new BigDecimal(100), "want my money! D:");
		cus2.getBankAccount().sendExternalTransactionRequest(new BigDecimal(60), "want my money also! D:");
		cus3.getBankAccount().sendExternalTransactionRequest(new BigDecimal(1000), "money!");
		cus4.getBankAccount().sendExternalTransactionRequest(new BigDecimal(10), "");
		cus5.getBankAccount().sendExternalTransactionRequest(new BigDecimal(1000000), "WANT MORE MONEY THEN I PROBABLY HAVE!!!");
		
		assertEquals(5, Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests().size());
		
		printCurrentTimeToConsol("some guys requested Money from their real kontos!");//<------------------------------------------------------------------------<TIMELINE UPDATE>
		Lottery.getInstance().getTimer().addHours(1);//<------------------------------------------------------------------------------------------------[TIME SIMULATION]
		
		//smart admin work:
		for(ExternalTransactionRequest request : Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests())
		{
			if(request.getTransaction().getAmount().compareTo(new BigDecimal(5000)) < 1)
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
		
		assertEquals(new BigDecimal(100), cus1.getBankAccount().getCredit());
		assertEquals(new BigDecimal(60), cus2.getBankAccount().getCredit());
		assertEquals(new BigDecimal(1000), cus3.getBankAccount().getCredit());
		assertEquals(new BigDecimal(10), cus4.getBankAccount().getCredit());
		assertEquals(new BigDecimal(0), cus5.getBankAccount().getCredit());
		
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
		
		//=========================================================================================================================//TIP TESTs NO 2
		
	}

	//	@After
	//	void cleanTest()
	//	{
	//		
	//	}

}
