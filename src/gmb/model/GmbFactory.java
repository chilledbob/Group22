package gmb.model;

import java.util.ArrayList;

import org.joda.time.DateTime;

import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.Jackpots;
import gmb.model.financial.container.LotteryCredits;
import gmb.model.financial.container.PrizeCategories;
import gmb.model.financial.container.RealAccountData;
import gmb.model.financial.container.ReceiptsDistribution;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.container.TipTicketPrices;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.financial.transaction.TicketPurchase;
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
import gmb.model.request.data.MemberDataUpdateRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.PTTDuration;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;
import gmb.model.tip.tipticket.type.DailyLottoTT;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

public class GmbFactory 
{
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//====================================================================================================================// FOR USAGE IN CONTROLLER:

	////financial:
	//container:
	
	public static RealAccountData new_RealAccountData(String bankCode, String accountNumber)
	{
		RealAccountData obj = new RealAccountData(bankCode, accountNumber);
		return (RealAccountData) obj.DB_ADD();
	}
	
	//..
	public static LotteryBankAccount new_LotteryBankAccount(RealAccountData realAccountData)
	{
		LotteryBankAccount obj = new LotteryBankAccount(realAccountData);
		return (LotteryBankAccount) obj.DB_ADD();
	}
	
	////group:
	public static Group new_Group(String name, Customer groupAdmin, String infoText)
	{
		Group obj = new Group(name, groupAdmin, infoText);
		obj = (Group) obj.DB_ADD();
		
		obj.getGroupAdmin().addGroup(obj);
		Lottery.getInstance().getGroupManagement().addGroup(obj);
		
		return obj;
	}
	
	//////tip:
	////draw:
	public static DailyLottoDraw new_DailyLottoDraw(DateTime planedEvaluationDate)
	{
		DailyLottoDraw obj = new DailyLottoDraw(planedEvaluationDate);
		obj = (DailyLottoDraw) obj.DB_ADD();
		Lottery.getInstance().getTipManagement().addDraw(obj);
		
		return obj;
	}
	
	public static TotoEvaluation new_TotoEvaluation(DateTime planedEvaluationDate, ArrayList<FootballGameData> results)
	{
		TotoEvaluation obj = new TotoEvaluation(planedEvaluationDate, results);
		obj = (TotoEvaluation) obj.DB_ADD();
		Lottery.getInstance().getTipManagement().addDraw(obj);
		
		return obj;
	}
	
	public static WeeklyLottoDraw new_WeeklyLottoDraw(DateTime planedEvaluationDate)
	{
		WeeklyLottoDraw obj = new WeeklyLottoDraw(planedEvaluationDate);
		obj = (WeeklyLottoDraw) obj.DB_ADD();
		Lottery.getInstance().getTipManagement().addDraw(obj);
		
		return obj;
	}
	
	////tip:
	//group:
	public static DailyLottoGroupTip new_DailyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		DailyLottoGroupTip obj = new DailyLottoGroupTip(draw, group, minimumStake, overallMinimumStake);
		obj = (DailyLottoGroupTip) obj.DB_ADD();
		group.addGroupTip(obj);
		
		return obj;
	}
	
	public static TotoGroupTip new_TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		TotoGroupTip obj = new TotoGroupTip(draw, group, minimumStake, overallMinimumStake);
		obj = (TotoGroupTip) obj.DB_ADD();
		group.addGroupTip(obj);
		
		return obj;
	}
	
	public static WeeklyLottoGroupTip new_WeeklyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		WeeklyLottoGroupTip obj = new WeeklyLottoGroupTip(draw, group, minimumStake, overallMinimumStake);
		obj = (WeeklyLottoGroupTip) obj.DB_ADD();
		group.addGroupTip(obj);
		
		return obj;
	}
	
	////tipticket:
	//single:
	public static ReturnBox<Integer, DailyLottoSTT> createAndPurchase_DailyLottoSTT(Customer customer)
	{
		DailyLottoSTT ticket = new DailyLottoSTT(null);

		if(!customer.hasEnoughMoneyToPurchase(ticket.getPrice())) 
			return new ReturnBox<Integer, DailyLottoSTT>(new Integer(1), ticket);
		
		ticket = (DailyLottoSTT) ticket.DB_ADD();	
		ticket.purchase(customer);
		
		return new ReturnBox<Integer, DailyLottoSTT>(new Integer(0), ticket);
	}

	public static ReturnBox<Integer, WeeklyLottoSTT> createAndPurchase_WeeklyLottoSTT(Customer customer)
	{
		WeeklyLottoSTT ticket = new WeeklyLottoSTT(null);
		
		if(!customer.hasEnoughMoneyToPurchase(ticket.getPrice()))
			return new ReturnBox<Integer, WeeklyLottoSTT>(new Integer(1), ticket);
		
		ticket = (WeeklyLottoSTT) ticket.DB_ADD();	
		ticket.purchase(customer);
		
		return new ReturnBox<Integer, WeeklyLottoSTT>(new Integer(0), ticket);
	}
	
	public static ReturnBox<Integer, TotoSTT> createAndPurchase_TotoSTT(Customer customer)
	{
		TotoSTT ticket = new TotoSTT(null);
		
		if(!customer.hasEnoughMoneyToPurchase(ticket.getPrice()))
			return new ReturnBox<Integer, TotoSTT>(new Integer(1), ticket);
		
		ticket = (TotoSTT) ticket.DB_ADD();	
		ticket.purchase(customer);
		
		return new ReturnBox<Integer, TotoSTT>(new Integer(0), ticket);
	}
	
	//perma:
	public static ReturnBox<Integer, DailyLottoPTT> createAndPurchase_DailyLottoPTT(Customer customer, PTTDuration duration)
	{
		DailyLottoPTT ticket = new DailyLottoPTT(duration);
		
		if(!customer.hasEnoughMoneyToPurchase(ticket.getPrice()))
			return new ReturnBox<Integer, DailyLottoPTT>(new Integer(1), ticket);
		
		ticket = (DailyLottoPTT) ticket.DB_ADD();	
		ticket.purchase(customer);
		
		return new ReturnBox<Integer, DailyLottoPTT>(new Integer(0), ticket);
	}

	public static ReturnBox<Integer, WeeklyLottoPTT> createAndPurchase_WeeklyLottoPTT(Customer customer, PTTDuration duration)
	{
		WeeklyLottoPTT ticket = new WeeklyLottoPTT(duration);
		
		if(!customer.hasEnoughMoneyToPurchase(ticket.getPrice()))
			return new ReturnBox<Integer, WeeklyLottoPTT>(new Integer(1), ticket);
		
		ticket = (WeeklyLottoPTT) ticket.DB_ADD();	
		ticket.purchase(customer);
		
		return new ReturnBox<Integer, WeeklyLottoPTT>(new Integer(0), ticket);
	}
	
	////member:
	//container:
	public static Adress new_Adress(String streetName, String houseNumber, String postCode, String townName)
	{
		Adress obj = new Adress(streetName, houseNumber, postCode, townName);
		return (Adress) obj.DB_ADD();
	}
	
	public static MemberData new_MemberData(String firstName, String lastName, DateTime birthDate, String phoneNumber, String eMail, Adress adress)
	{
		MemberData obj = new MemberData(firstName, lastName, birthDate, phoneNumber, eMail, adress);
		return (MemberData) obj.DB_ADD();
	}
	
	//..
	public static Member new_Member(String nickName, String password, MemberData memberData, MemberType type)
	{
		Member obj = new Member(nickName, password, memberData, type);
		return (Member) obj.DB_ADD();
	}
	
	public static Customer new_Customer(String nickName, String password, MemberData memberData, LotteryBankAccount lotteryBankAccount)
	{
		Customer obj = new Customer(nickName, password, memberData, lotteryBankAccount);
		obj.getBankAccount().setOwner(obj);
		
		return (Customer) obj.DB_ADD();
	}
	
	//====================================================================================================================//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//========================================================================================================================// ONLY MODEL INTERNALLY USED:
	
	////financial:
	//container:
	public static Jackpots new_Jackpots()
	{
		Jackpots obj = new Jackpots(null);
		return (Jackpots) obj.DB_ADD();
	}
	
	public static LotteryCredits new_LotteryCredits()
	{
		LotteryCredits obj = new LotteryCredits(null);
		return (LotteryCredits) obj.DB_ADD();
	}
	
	public static PrizeCategories new_PrizeCategories()
	{
		PrizeCategories obj = new PrizeCategories(null);
		return (PrizeCategories) obj.DB_ADD();
	}
	
	public static ReceiptsDistribution new_PrizeCategories(int winnersDue, int treasuryDue, int lotteryTaxDue, int managementDue)
	{
		ReceiptsDistribution obj = new ReceiptsDistribution(winnersDue, treasuryDue, lotteryTaxDue, managementDue);
		return (ReceiptsDistribution) obj.DB_ADD();
	}
	
	public static ReceiptsDistribution new_ReceiptsDistribution()
	{
		ReceiptsDistribution obj = new ReceiptsDistribution(null);
		return (ReceiptsDistribution) obj.DB_ADD();
	}
	
	public static ReceiptsDistribution new_ReceiptsDistribution(int winnersDue, int treasuryDue, int lotteryTaxDue, int managementDue)
	{
		ReceiptsDistribution obj = new ReceiptsDistribution(winnersDue, treasuryDue, lotteryTaxDue, managementDue);
		return (ReceiptsDistribution) obj.DB_ADD();
	}
	
	public static ReceiptsDistributionResult new_ReceiptsDistributionResult(CDecimal drawReceipts)
	{
		ReceiptsDistributionResult obj = new ReceiptsDistributionResult(drawReceipts);
		obj = (ReceiptsDistributionResult) obj.DB_ADD();
		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(obj);
		
		return obj;
	}
	
	public static TipTicketPrices new_TipTicketPrices()
	{
		TipTicketPrices obj = new TipTicketPrices(null);
		return (TipTicketPrices) obj.DB_ADD();
	}
	
	//transaction:	
	public static Winnings new_Winnings(Tip tip, CDecimal amount, int prizeCategory)
	{
		Winnings obj = new Winnings(tip, amount, prizeCategory);
		return (Winnings) obj.DB_ADD();
	}
	
	public static TicketPurchase new_TicketPurchase(TipTicket ticket)
	{
		TicketPurchase obj = new TicketPurchase(ticket);
		return (TicketPurchase) obj.DB_ADD();
	}
	
	public static ExternalTransaction new_ExternalTransaction(Customer affectedCustomer, CDecimal amount)
	{
		ExternalTransaction obj = new ExternalTransaction(affectedCustomer, amount);
		return (ExternalTransaction) obj.DB_ADD();
	}
	
	//..
	public static FinancialManagement new_FinancialManagement(TipTicketPrices tipTicketPrices, ReceiptsDistribution receiptsDistribution)
	{
		FinancialManagement obj = new FinancialManagement(tipTicketPrices, receiptsDistribution);
		return (FinancialManagement) obj.DB_ADD();
	}
	
	////group:
	public static GroupManagement new_GroupManagement()
	{
		GroupManagement obj = new GroupManagement(null);
		return (GroupManagement) obj.DB_ADD();
	}
	
	////member:
//	public static MemberManagement new_MemberManagement()
//	{
//		MemberManagement obj = new MemberManagement(null);
//		return (MemberManagement) obj.DB_ADD();
//	}
	
	////request:
	//data:
	public static MemberDataUpdateRequest new_MemberDataUpdateRequest(MemberData updatedData, Member member, String note)
	{
		MemberDataUpdateRequest obj = new MemberDataUpdateRequest(updatedData, member, note);
		return (MemberDataUpdateRequest) obj.DB_ADD();
	}
	
	public static RealAccountDataUpdateRequest new_RealAccountDataUpdateRequest(RealAccountData updatedData, Customer member, String note)
	{
		RealAccountDataUpdateRequest obj = new RealAccountDataUpdateRequest(updatedData, member, note);
		return (RealAccountDataUpdateRequest) obj.DB_ADD();
	}
	
	//group:
	public static GroupAdminRightsTransfereOffering new_GroupAdminRightsTransfereOffering(Group group, Customer member, String note)
	{
		GroupAdminRightsTransfereOffering obj = new GroupAdminRightsTransfereOffering(group, member, note);
		return (GroupAdminRightsTransfereOffering) obj.DB_ADD();
	}
	
	public static GroupMembershipApplication new_GroupMembershipApplication(Group group, Customer member, String note)
	{
		GroupMembershipApplication obj = new GroupMembershipApplication(group, member, note);
		return (GroupMembershipApplication) obj.DB_ADD();
	}
	
	public static ExternalTransactionRequest new_ExternalTransactionRequest(ExternalTransaction transaction, String note)
	{
		ExternalTransactionRequest obj = new ExternalTransactionRequest(transaction, note);
		return (ExternalTransactionRequest) obj.DB_ADD();
	}
	
	public static Notification new_Notification(String note)
	{
		Notification obj = new Notification(note);
		return (Notification) obj.DB_ADD();
	}
	
	//////tip:
	////draw:
	//container:	
//	public static WeeklyLottoDrawEvaluationResult new_DrawEvaluationResult()
//	{
//		WeeklyLottoDrawEvaluationResult obj = new WeeklyLottoDrawEvaluationResult(null);
//		return (WeeklyLottoDrawEvaluationResult) obj.DB_ADD();
//	}
	
	////tip:
	//single:
	public static DailyLottoTip new_DailyLottoTip(DailyLottoTT tipTicket, DailyLottoDraw draw)
	{
		DailyLottoTip obj = new DailyLottoTip(tipTicket, draw);
		return (DailyLottoTip) obj.DB_ADD();
	}
	
	public static DailyLottoTip new_DailyLottoTip(DailyLottoTT tipTicket, GroupTip groupTip)
	{
		DailyLottoTip obj = new DailyLottoTip(tipTicket, groupTip);
		return (DailyLottoTip) obj.DB_ADD();
	}
	
	public static TotoTip new_TotoTip(TotoSTT tipTicket, TotoEvaluation draw)
	{
		TotoTip obj = new TotoTip(tipTicket, draw);
		return (TotoTip) obj.DB_ADD();
	}
	
	public static TotoTip new_TotoTip(TotoSTT tipTicket, GroupTip groupTip)
	{
		TotoTip obj = new TotoTip(tipTicket, groupTip);
		return (TotoTip) obj.DB_ADD();
	}
	
	public static WeeklyLottoTip new_WeeklyLottoTip(WeeklyLottoTT tipTicket, WeeklyLottoDraw draw)
	{
		WeeklyLottoTip obj = new WeeklyLottoTip(tipTicket, draw);
		return (WeeklyLottoTip) obj.DB_ADD();
	}
	
	public static WeeklyLottoTip new_WeeklyLottoTip(WeeklyLottoTT tipTicket, GroupTip groupTip)
	{
		WeeklyLottoTip obj = new WeeklyLottoTip(tipTicket, groupTip);
		return (WeeklyLottoTip) obj.DB_ADD();
	}
	
	//....
//	public static TipManagement new_TipManagement()
//	{
//		TipManagement obj = new TipManagement(null);
//		return (TipManagement) obj.DB_ADD();
//	}
	
	//========================================================================================================================//
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
}
