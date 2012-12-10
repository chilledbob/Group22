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

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
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
		lotteryBankAccount.setOwner(this);
		
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
	
	public boolean hasEnoughMoneyToPurchase(BigDecimal price)
	{
		return lotteryBankAccount.getCredit().compareTo(price) > -1;
	}
	
//	public void setLotteryBankAccount(LotteryBankAccount lotteryBankAccount){ this.lotteryBankAccount = lotteryBankAccount; }
	
	public void addTipTicket(WeeklyLottoSTT ticket){ weeklyLottoSTTs.add(ticket); }
	public void addTipTicket(DailyLottoSTT ticket){ dailyLottoSTTs.add(ticket); }
	public void addTipTicket(TotoSTT ticket){ totoSTTs.add(ticket); }
	public void addTipTicket(WeeklyLottoPTT ticket){ weeklyLottoPTTs.add(ticket); }
	public void addTipTicket(DailyLottoPTT ticket){ dailyLottoPTTs.add(ticket); }
	
	public void addGroupInvitation(GroupInvitation invitation){ groupInvitations.add(invitation); }
	public void addGroupAdminRightsTransfereOffering(GroupAdminRightsTransfereOffering offering){ groupAdminRightsTransfereOfferings.add(offering); }
	public void addGroupMembershipApplication(GroupMembershipApplication application){ groupMembershipApplications.add(application); }
	public void addGroup(Group group){ groups.add(group); }


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
