package gmb.model.group;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.PersiObject2;
import gmb.model.member.Admin;
import gmb.model.member.Customer;
import gmb.model.member.container.MemberData;
import gmb.model.request.RequestState;
import gmb.model.request.group.GroupAdminRightsTransfereOffering;
import gmb.model.request.group.GroupInvitation;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

@Entity
@Table(name="GroupTable")
public class Group extends PersiObject2
{
//	@Id
//	@GeneratedValue (strategy=GenerationType.AUTO)
//	protected int groupId;
//	
//	public int getGroupId(){ return groupId;}
	
	protected String name;
	protected String infoText;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date foundingDate;
	protected Boolean closed = false;
	
	@ManyToOne
	protected GroupManagement groupManagementId;

	@OneToOne
	protected Customer groupAdmin;
	@ManyToMany
	protected List<Customer> groupMembers;

	@OneToMany(mappedBy="group")
	protected List<DailyLottoGroupTip> dailyLottoGroupTips;
	@OneToMany(mappedBy="group")
	protected List<WeeklyLottoGroupTip> weeklyLottoGroupTips;
	@OneToMany(mappedBy="group")
	protected List<TotoGroupTip> totoGroupTips;

	@OneToMany(mappedBy="group")
	protected List<GroupInvitation> groupInvitations;
	@OneToMany(mappedBy="group")
	protected List<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	@OneToMany(mappedBy="group")
	protected List<GroupMembershipApplication> groupMembershipApplications;

	@Deprecated
	protected Group(){}

	public Group(String name, Customer groupAdmin, String infoText)
	{
		this.name = name;
		this.infoText = infoText;
		foundingDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		
		this.groupAdmin = groupAdmin;
		this.groupAdmin.addGroup(this);
		
		groupMembers =  new LinkedList<Customer>();
		
		dailyLottoGroupTips = new LinkedList<DailyLottoGroupTip>();
		weeklyLottoGroupTips = new LinkedList<WeeklyLottoGroupTip>();
		totoGroupTips = new LinkedList<TotoGroupTip>();

		groupInvitations = new LinkedList<GroupInvitation>();
		groupAdminRightsTransfereOfferings = new LinkedList<GroupAdminRightsTransfereOffering>();
		groupMembershipApplications = new LinkedList<GroupMembershipApplication>();
		Lottery.getInstance().getGroupManagement().addGroup(this);
	}
	
	
	/**
	 * [intended for direct usage by controller]
	 * creates a "GroupMembershipApplication" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public GroupMembershipApplication applyForMembership(Customer customer, String note)
	{
		GroupMembershipApplication application = new GroupMembershipApplication(this, customer, note);

		customer.addGroupMembershipApplication(application);
		this.groupMembershipApplications.add(application);
		
		DB_UPDATE(); 
		
		return application;
	}

	/**
	 * [intended for direct usage by controller]
	 * creates a "GroupInvitation" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public GroupInvitation sendGroupInvitation(Customer customer, String note)
	{
		GroupInvitation invitation = new GroupInvitation(this, customer, note);

		customer.addGroupInvitation(invitation);
		this.groupInvitations.add(invitation);
		
		DB_UPDATE(); 
		
		return invitation;
	}

	/**
	 * [intended for direct usage by controller]
	 * creates a "GroupAdminRightsTransfereOffering" and adds it to the "groupMember" and this group
	 * @param groupMember
	 * @param note
	 */
	public GroupAdminRightsTransfereOffering sendGroupAdminRightsTransfereOffering(Customer groupMember, String note)
	{
		GroupAdminRightsTransfereOffering offering = new GroupAdminRightsTransfereOffering(this, groupMember, note);

		groupMember.addGroupAdminRightsTransfereOffering(offering);
		this.groupAdminRightsTransfereOfferings.add(offering);
		
		DB_UPDATE(); 
		
		return offering;
	}

	/**
	 * Swaps the "groupAdmin" with "groupMember".
	 * Removes the "groupMember" from the "groupMembers" list and adds the old "groupAdmin"
	 * Returns false if the "groupMember" is not in "groupMembers".
	 * @param groupMember
	 * @return
	 */
	public boolean switchGroupAdmin(Customer groupMember)
	{
		if(groupMembers.contains(groupMember))
		{
			groupMembers.add(groupAdmin);
			groupAdmin = groupMember;
			groupMembers.remove(groupMember);
			
			DB_UPDATE(); 
			
			return true;
		}
		else
			return false;
	}
	
	protected boolean resign(Customer groupMember, String notification)
	{
		if(groupMembers.contains(groupMember) || groupMember == groupAdmin)
		{
			withdrawUnhandledGroupRequestsOfGroupMember(groupMember);

			groupMember.addNotification(notification);
			
			if(groupMembers.contains(groupMember))
				groupMembers.remove(groupMember);
			else
				groupAdmin = null;
			
			groupMember.removeGroup(this);
			
			return true;
		}
		else
			return false;
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Resigns the "groupMember" by withdrawing all unhandled group related requests in "groupMember"
	 * and sending a "Notification" to the member and removing him from the "groupMembers" list.
	 * If it's the "groupAdmin" who resigns "close" the group. 
	 * @param groupMember
	 */
	public boolean resign(Customer groupMember)
	{	
		if(groupMember == groupAdmin)
		{
			if(!close()) return false;
		}
		else
			resign(groupMember, "You have been resigned from Group " + name + ".");

		DB_UPDATE(); 
		
		return true;
	}

	/**
	 * withdraw all unhandled group related "Requests" in "groupMember"
	 * @param groupMember
	 */
	protected void withdrawUnhandledGroupRequestsOfGroupMember(Customer groupMember)
	{
		for(GroupMembershipApplication application : groupMembershipApplications)
		{
			if(application.getMember() == groupMember && application.getState() == RequestState.UNHANDLED)
				application.withdraw();
		}

		for(GroupInvitation invitation : groupInvitations)
		{
			if(invitation.getMember() == groupMember && invitation.getState() == RequestState.UNHANDLED)
				invitation.withdraw();
		}

		for(GroupAdminRightsTransfereOffering offerings : groupAdminRightsTransfereOfferings)
		{
			if(offerings.getMember() == groupMember && offerings.getState() == RequestState.UNHANDLED)
				offerings.withdraw();
		}
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Closes the group by resigning all "groupMembers" + "groupAdmin", 
	 * withdrawing all group related requests in the system which are still unhandled
	 * and setting the "closed" flag to true.
	 * This doesn't remove the group from the system entirely.
	 */
	public boolean close()
	{
		if(closed == true) return false;		
		closed = true;
		
		for(DailyLottoGroupTip groupTip : dailyLottoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		for(WeeklyLottoGroupTip groupTip : weeklyLottoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		for(TotoGroupTip groupTip : totoGroupTips)
			for(Customer groupMember : groupMembers)
				groupTip.removeAllTipsOfGroupMember(groupMember);
		
		
		for(Customer groupMember : groupMembers)
			resign(groupMember, "The group " + name + " has been closed. You will be automatically resigned.");
				
		resign(groupAdmin, "The group " + name + ", where you had admin status, has been closed. You will be automatically resigned.");
		
		//withdraw all group related requests not only those which are associated with groupMembers:
		for(GroupMembershipApplication application : groupMembershipApplications)
			if(application.getState() == RequestState.UNHANDLED)
				application.withdraw();

		for(GroupInvitation invitation : groupInvitations)
			if(invitation.getState() == RequestState.UNHANDLED)
				invitation.withdraw();
		
		for(GroupAdminRightsTransfereOffering offerings : groupAdminRightsTransfereOfferings)
			if(offerings.getState() == RequestState.UNHANDLED)
				offerings.withdraw();
		
		DB_UPDATE(); 
		
		return true;
	}
 
	public void addGroupMember(Customer customer)
	{ 
		groupMembers.add(customer); 
		customer.addGroup(this);
		
		DB_UPDATE(); 
	}
		
	/**
	 * [intended for direct usage by controller]
	 * Sets the info text for the group.
	 * @param infoText
	 */
	public void SetInfoText(String infoText){ this.infoText = infoText; DB_UPDATE(); }	
	public void setGroupAdmin(Customer groupAdmin){ this.groupAdmin = groupAdmin; DB_UPDATE(); }
	
	public void addGroupTip(DailyLottoGroupTip tip){ dailyLottoGroupTips.add(tip); DB_UPDATE(); }	
	public void addGroupTip(WeeklyLottoGroupTip tip){ weeklyLottoGroupTips.add(tip); DB_UPDATE(); }	
	public void addGroupTip(TotoGroupTip tip){ totoGroupTips.add(tip); DB_UPDATE(); }
	
	public boolean removeGroupTip(DailyLottoGroupTip tip){ boolean result = dailyLottoGroupTips.remove(tip); DB_UPDATE(); return result; }
	public boolean removeGroupTip(WeeklyLottoGroupTip tip){ boolean result = weeklyLottoGroupTips.remove(tip); DB_UPDATE(); return result; }
	public boolean removeGroupTip(TotoGroupTip tip){ boolean result = totoGroupTips.remove(tip); DB_UPDATE(); return result; }
	
	public boolean isClosed(){ return closed; }
	
	public List<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public List<GroupInvitation> getGroupInvitations(){ return groupInvitations; }
	public List<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	

	public List<Customer> getGroupMembers(){ return groupMembers; }
	/**
	 * show the groupname
	 * important for view output
	 * @return
	 */
	public String getName(){ return name;}
	public String getInfoText(){ return infoText; }	
	public Customer getGroupAdmin(){ return groupAdmin; }
	public DateTime getFoundingDate(){ return new DateTime(foundingDate); }

	public List<DailyLottoGroupTip> getDailyLottoGroupTips(){ return dailyLottoGroupTips; }	
	public List<WeeklyLottoGroupTip> getWeeklyLottoGroupTips(){ return weeklyLottoGroupTips; }	
	public List<TotoGroupTip> getTotoGroupTips(){ return totoGroupTips; }	
}
