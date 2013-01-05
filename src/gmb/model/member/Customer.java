package gmb.model.member;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.group.Group;
import gmb.model.member.container.MemberData;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.request.group.GroupMembershipInvitation;
import gmb.model.tip.tipticket.perma.DailyLottoPTT;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import gmb.model.CDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

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
	@JoinFetch(JoinFetchType.INNER)
	protected List<WeeklyLottoSTT> weeklyLottoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<DailyLottoSTT> dailyLottoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<TotoSTT> totoSTTs;
	@OneToMany(mappedBy="owner")
	protected List<WeeklyLottoPTT> weeklyLottoPTTs;
	@OneToMany(mappedBy="owner")
	protected List<DailyLottoPTT> dailyLottoPTTs;
	
	@OneToMany(mappedBy="member",fetch=FetchType.EAGER)
	protected List<GroupMembershipInvitation> groupInvitations;
	@OneToMany
	protected List<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	@OneToMany(mappedBy="member",fetch=FetchType.EAGER)
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
		
		groupInvitations = new LinkedList<GroupMembershipInvitation>();
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
	
	public void addGroupInvitation(GroupMembershipInvitation invitation){ groupInvitations.add(invitation); DB_UPDATE(); }
	public void addGroupAdminRightsTransfereOffering(GroupAdminRightsTransfereOffering offering){ groupAdminRightsTransfereOfferings.add(offering); DB_UPDATE(); }
	public void addGroupMembershipApplication(GroupMembershipApplication application){ groupMembershipApplications.add(application); DB_UPDATE(); }
	public void addGroup(Group group){ groups.add(group); DB_UPDATE(); }

	public boolean removeGroup(Group group){ boolean result = groups.remove(group); DB_UPDATE(); return result;}

 	public LotteryBankAccount getBankAccount(){ return lotteryBankAccount; }
 	public List<Group> getGroups(){ return groups; }
 	
	public List<WeeklyLottoSTT> getWeeklyLottoSTTs(){ return weeklyLottoSTTs; }
	public List<DailyLottoSTT> getDailyLottoSTTs(){ return dailyLottoSTTs; }
	public List<TotoSTT> getTotoSTTs(){ return totoSTTs; }
	public List<WeeklyLottoPTT> getWeeklyLottoPTTs(){ return weeklyLottoPTTs; }
	public List<DailyLottoPTT> getDailyLottoPTTs(){ return dailyLottoPTTs; }
	
	public List<GroupMembershipInvitation> getGroupInvitations(){ return groupInvitations; }
	public List<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public List<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	
}
