package gmb.model.member;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.group.Group;
import gmb.model.member.container.MemberData;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupInvitation;
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

import org.salespointframework.core.user.Capability;

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
	protected List<GroupInvitation> groupInvitations;
	@OneToMany
	protected List<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	@OneToMany
	protected List<GroupMembershipApplication> groupMembershipApplications;
	

	@Deprecated
	protected Customer(){}
	
	public Customer(String nickName, String password, MemberData memberData, LotteryBankAccount lotteryBankAccount)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("customer"));
		
		this.lotteryBankAccount = lotteryBankAccount;
		
		//currently not possible
		//membermanager must be updated first
		//better do this separately
		//lotteryBankAccount.setOwner(this);
		
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
	
	/**
	 * [intended for direct usage by controller]
	 * Returns true if the customer owns enough money.
	 * @param price
	 * @return
	 */
	public boolean hasEnoughMoneyToPurchase(CDecimal price)
	{
		return lotteryBankAccount.getCredit().compareTo(price) > -1;
	}
	
//	public void setLotteryBankAccount(LotteryBankAccount lotteryBankAccount){ this.lotteryBankAccount = lotteryBankAccount; }
	
	public void addTipTicket(WeeklyLottoSTT ticket){ weeklyLottoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(DailyLottoSTT ticket){ dailyLottoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(TotoSTT ticket){ totoSTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(WeeklyLottoPTT ticket){ weeklyLottoPTTs.add(ticket); DB_UPDATE(); }
	public void addTipTicket(DailyLottoPTT ticket){ dailyLottoPTTs.add(ticket); DB_UPDATE(); }
	
	public void addGroupInvitation(GroupInvitation invitation){ groupInvitations.add(invitation); DB_UPDATE(); }
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
	
	public List<GroupInvitation> getGroupInvitations(){ return groupInvitations; }
	public List<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public List<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	
}
