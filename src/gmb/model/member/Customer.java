package gmb.model.member;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.group.Group;
import gmb.model.member.container.MemberData;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import gmb.model.CDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * The customer class representing the gambler.
 *
 */
@Entity
public class Customer extends Member 
{
	@OneToOne(cascade=CascadeType.ALL)
	//@JoinColumn(name="userIdentifier" , referencedColumnName="userIdentifier")
	protected LotteryBankAccount lotteryBankAccount;
	@ManyToMany
	protected List<Group> groups;
	
	@OneToMany(mappedBy="owner")
	protected List<WeeklyLottoSTT> weeklyLottoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<DailyLottoSTT> dailyLottoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<TotoSTT> totoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<WeeklyLottoPTT> weeklyLottoPTTs;
	@OneToMany(mappedBy="owner")
	protected List<DailyLottoPTT> dailyLottoPTTs;
	
	@OneToMany
	protected List<GroupMembershipApplication> groupInvitations;
	@OneToMany
	protected List<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	@OneToMany
	protected List<GroupMembershipApplication> groupMembershipApplications;
	

	@Deprecated
	protected Customer(){}
	
	public Customer(String nickName, String password, MemberData memberData, LotteryBankAccount lotteryBankAccount)
	{
		super(nickName, password, memberData, MemberType.Customer);
		
		this.lotteryBankAccount = lotteryBankAccount;
		
		groups = new LinkedList<Group>();
		
		weeklyLottoSTTs = new LinkedList<WeeklyLottoSTT>();
		dailyLottoSTTs = new LinkedList<DailyLottoSTT>();
		totoSTTs = new LinkedList<TotoSTT>();
		weeklyLottoPTTs = new LinkedList<WeeklyLottoPTT>();
		dailyLottoPTTs = new LinkedList<DailyLottoPTT>();
		
		groupInvitations = new LinkedList<GroupMembershipApplication>();
		groupAdminRightsTransfereOfferings = new LinkedList<GroupAdminRightsTransfereOffering>();
		groupMembershipApplications = new LinkedList<GroupMembershipApplication>();
	}
	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * Returns true if the customer's associated "credit" >= "price".
	 * @param price
	 * @return true if customer has enough money, otherwise false.
	 */
	public boolean hasEnoughMoneyToPurchase(CDecimal price)
	{
		return lotteryBankAccount.getCredit().compareTo(price.abs()) > -1;
	}
	
//	public void setLotteryBankAccount(LotteryBankAccount lotteryBankAccount){ this.lotteryBankAccount = lotteryBankAccount; }
	
	public void addTipTicket(WeeklyLottoSTT ticket){ weeklyLottoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(DailyLottoSTT ticket){ dailyLottoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(TotoSTT ticket){ totoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(WeeklyLottoPTT ticket){ weeklyLottoPTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(DailyLottoPTT ticket){ dailyLottoPTTs.add(ticket); DB_UPDATE(); }
	
	public void addGroupInvitation(GroupMembershipApplication invitation){ groupInvitations.add(invitation); DB_UPDATE(); }
	public void addGroupAdminRightsTransfereOffering(GroupAdminRightsTransfereOffering offering){ groupAdminRightsTransfereOfferings.add(offering); DB_UPDATE(); }
	public void addGroupMembershipApplication(GroupMembershipApplication application){ groupMembershipApplications.add(application); DB_UPDATE(); }
	public void addGroup(Group group){ groups.add(group); DB_UPDATE(); }

	public boolean removeGroup(Group group){ boolean result = groups.remove(group); DB_UPDATE(); return result;}

 	public LotteryBankAccount getBankAccount(){ return lotteryBankAccount; }
 	public LinkedList<Group> getGroups(){ return (LinkedList<Group>) groups; }
 	
	public LinkedList<WeeklyLottoSTT> getWeeklyLottoSTTs(){ return (LinkedList<WeeklyLottoSTT>) weeklyLottoSTTs; }
	public LinkedList<DailyLottoSTT> getDailyLottoSTTs(){ return (LinkedList<DailyLottoSTT>) dailyLottoSTTs; }
	public LinkedList<TotoSTT> getTotoSTTs(){ return (LinkedList<TotoSTT>) totoSTTs; }
	public LinkedList<WeeklyLottoPTT> getWeeklyLottoPTTs(){ return (LinkedList<WeeklyLottoPTT>) weeklyLottoPTTs; }
	public LinkedList<DailyLottoPTT> getDailyLottoPTTs(){ return (LinkedList<DailyLottoPTT>) dailyLottoPTTs; }
	
	public LinkedList<GroupMembershipApplication> getGroupInvitations(){ return (LinkedList<GroupMembershipApplication>) groupInvitations; }
	public LinkedList<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return (LinkedList<GroupAdminRightsTransfereOffering>) groupAdminRightsTransfereOfferings; }
	public LinkedList<GroupMembershipApplication> getGroupMembershipApplications(){ return (LinkedList<GroupMembershipApplication>) groupMembershipApplications; }	
}
