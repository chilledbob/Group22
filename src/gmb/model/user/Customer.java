package gmb.model.user;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.request.GroupAdminRightsTransfereOffering;
import gmb.model.request.GroupInvitation;
import gmb.model.request.GroupMembershipApplication;
import gmb.model.tip.DailyLottoPTT;
import gmb.model.tip.DailyLottoSTT;
import gmb.model.tip.TotoSTT;
import gmb.model.tip.WeeklyLottoPTT;
import gmb.model.tip.WeeklyLottoSTT;

import java.util.LinkedList;

import org.salespointframework.core.user.Capability;


public class Customer extends Member 
{
	//ATTRIBUTES
	protected LotteryBankAccount lotteryBankAccount;
	protected LinkedList<Group> groups;
	
	protected LinkedList<WeeklyLottoSTT> weeklyLottoSTTs;
	protected LinkedList<DailyLottoSTT> dailyLottoSTTs;
	protected LinkedList<TotoSTT> totoSTTs;
	protected LinkedList<WeeklyLottoPTT> weeklyLottoPTTs;
	protected LinkedList<DailyLottoPTT> dailyLottoPTTs;
	
	protected LinkedList<GroupInvitation> groupInvitations;
	protected LinkedList<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	protected LinkedList<GroupMembershipApplication> groupMembershipApplications;
	
	//CONSTRUCTORS
	public Customer(String nickName, String password, MemberData memberData, LotteryBankAccount lotteryBankAccount)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("customer"));
		
		this.lotteryBankAccount = lotteryBankAccount;
		groups = new LinkedList<Group>();
		
		weeklyLottoSTTs = new LinkedList<WeeklyLottoSTT>();
		dailyLottoSTTs = new LinkedList<DailyLottoSTT>();
		totoSTTs = new LinkedList<TotoSTT>();
		weeklyLottoPTTs = new LinkedList<WeeklyLottoPTT>();
		dailyLottoPTTs = new LinkedList<DailyLottoPTT>();
		
		groupInvitations = new LinkedList<GroupInvitation>();
		groupAdminRightsTransfereOfferings = new LinkedList<GroupAdminRightsTransfereOffering>();
		groupMembershipApplications = new LinkedList<GroupMembershipApplication>();
	}
	
	//SET/ADD METHODS 
	public void addTipTicket(WeeklyLottoSTT ticket){ weeklyLottoSTTs.add(ticket); }
	public void addTipTicket(DailyLottoSTT ticket){ dailyLottoSTTs.add(ticket); }
	public void addTipTicket(TotoSTT ticket){ totoSTTs.add(ticket); }
	public void addTipTicket(WeeklyLottoPTT ticket){ weeklyLottoPTTs.add(ticket); }
	public void addTipTicket(DailyLottoPTT ticket){ dailyLottoPTTs.add(ticket); }
	
	public void addGroupInvitation(GroupInvitation invitation){ groupInvitations.add(invitation); }
	public void addGroupAdminRightsTransfereOffering(GroupAdminRightsTransfereOffering offering){ groupAdminRightsTransfereOfferings.add(offering); }
	public void addGroupMembershipApplication(GroupMembershipApplication application){ groupMembershipApplications.add(application); }
	public void addGroup(Group group){ groups.add(group); }

	//GET METHODS
 	public LotteryBankAccount getBankAccount(){ return lotteryBankAccount; }
 	public LinkedList<Group> getGroups(){ return groups; }
 	
	public LinkedList<WeeklyLottoSTT> getWeeklyLottoSTTs(){ return weeklyLottoSTTs; }
	public LinkedList<DailyLottoSTT> getDailyLottoSTTs(){ return dailyLottoSTTs; }
	public LinkedList<TotoSTT> getTotoSTTs(){ return totoSTTs; }
	public LinkedList<WeeklyLottoPTT> getWeeklyLottoPTTs(){ return weeklyLottoPTTs; }
	public LinkedList<DailyLottoPTT> getDailyLottoPTT(){ return dailyLottoPTTs; }
	
	public LinkedList<GroupInvitation> getGroupInvitations(){ return groupInvitations; }
	public LinkedList<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public LinkedList<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	
}
