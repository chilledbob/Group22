package gmb.model.user;

import gmb.model.Lottery;
import gmb.model.request.GroupAdminRightsTransfereOffering;
import gmb.model.request.GroupInvitation;
import gmb.model.request.GroupMembershipApplication;
import gmb.model.request.Notification;
import gmb.model.request.RequestState;
import gmb.model.tip.DailyLottoGroupTip;
import gmb.model.tip.TotoGroupTip;
import gmb.model.tip.WeeklyLottoGroupTip;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

@Entity
public class Group 
{
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int groupId;
	
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
		//this.groupAdmin.addGroup(this);
		
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
	 * creates a "GroupMembershipApplication" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public GroupMembershipApplication applyForMembership(Customer customer, String note)
	{
		GroupMembershipApplication application = new GroupMembershipApplication(this, customer, note);

		customer.addGroupMembershipApplication(application);
		this.groupMembershipApplications.add(application);
		
		return application;
	}

	/**
	 * creates a "GroupInvitation" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public GroupInvitation sendGroupInvitation(Customer customer, String note)
	{
		GroupInvitation invitation = new GroupInvitation(this, customer, note);

		customer.addGroupInvitation(invitation);
		this.groupInvitations.add(invitation);
		
		return invitation;
	}

	/**
	 * creates a "GroupAdminRightsTransfereOffering" and adds it to the "groupMember" and this group
	 * @param groupMember
	 * @param note
	 */
	public GroupAdminRightsTransfereOffering sendGroupAdminRightsTransfereOffering(Customer groupMember, String note)
	{
		GroupAdminRightsTransfereOffering offering = new GroupAdminRightsTransfereOffering(this, groupMember, note);

		groupMember.addGroupAdminRightsTransfereOffering(offering);
		this.groupAdminRightsTransfereOfferings.add(offering);
		
		return offering;
	}

	public boolean switchGroupAdmin(Customer groupMember)
	{
		if(groupMembers.contains(groupMember))
		{
			groupMembers.add(groupAdmin);
			groupAdmin = groupMember;
			groupMembers.remove(groupMember);
			
			return true;
		}
		else
			return false;
	}
	
	/**
	 * resign the "groupMember" by withdrawing all unhandled group related requests in "groupMember",
	 * sending a "Notification" to the member and removing him from the "groupMembers" list.
	 * if it's the "groupAdmin" who resigns "close" the group. 
	 * @param groupMember
	 */
	public void resign(Customer groupMember)
	{	
		if(groupMember == groupAdmin)
		{
			close();
			
			withdrawUnhandledGroupRequestsOfGroupMember(groupAdmin);
			groupAdmin.addNotification(new Notification("The group " + name + ", where you had admin status, has been closed. You will be automatically resigned."));
			
			groupAdmin.getGroups().remove(this);
		}
		else
		if(groupMembers.contains(groupMember))
		{
			withdrawUnhandledGroupRequestsOfGroupMember(groupMember);

			groupMember.addNotification(new Notification("You have been resigned from Group " + name + "."));
			groupMembers.remove(groupMember);
			
			groupMember.getGroups().remove(this);
		}
	}

	/**
	 * withdraw all unhandled group related "Requests" in "groupMember"
	 * @param groupMember
	 */
	protected void withdrawUnhandledGroupRequestsOfGroupMember(Customer groupMember)
	{
		for(GroupMembershipApplication application : groupMember.getGroupMembershipApplications())
		{
			if(application.getGroup() == this && application.getState() == RequestState.UNHANDELED)
				application.withdraw();
		}

		for(GroupInvitation invitation : groupMember.getGroupInvitations())
		{
			if(invitation.getGroup() == this && invitation.getState() == RequestState.UNHANDELED)
				invitation.withdraw();
		}

		for(GroupAdminRightsTransfereOffering offerings : groupMember.getGroupAdminRightsTransfereOfferings())
		{
			if(offerings.getGroup() == this && offerings.getState() == RequestState.UNHANDELED)
				offerings.withdraw();
		}
	}
	
	/**
	 * close group by resigning all "groupMembers" + "groupAdmin" and setting the "closed" flag to true
	 */
	public void close()
	{
		if(closed == true) return;
		
		closed = true;
		
		for(Customer groupMember : groupMembers)
		{
			if(groupMembers.contains(groupMember))
			{
				withdrawUnhandledGroupRequestsOfGroupMember(groupMember);
				groupMember.addNotification(new Notification("The group " + name + " has been closed. You will be automatically resigned."));
			}
		}
				
		resign(groupAdmin);
	}
 
	public void addGroupMember(Customer customer)
	{ 
		groupMembers.add(customer); 
		customer.addGroup(this);
	}
	
	public void SetInfoText(String infoText){ this.infoText = infoText; }	
	public void setGroupAdmin(Customer groupAdmin){ this.groupAdmin = groupAdmin; }

	public List<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public List<GroupInvitation> getGroupInvitations(){ return groupInvitations; }
	public List<GroupMembershipApplication> getGroupMembershipApplications(){ return groupMembershipApplications; }	

	public List<Customer> getGroupMembers(){ return groupMembers; }
	public String getInfoText(){ return infoText; }	
	public Customer getGroupAdmin(){ return groupAdmin; }
	public DateTime getFoundingDate(){ return new DateTime(foundingDate); }

	public List<DailyLottoGroupTip> getDailyLottoGroupTips(){ return dailyLottoGroupTips; }	
	public List<WeeklyLottoGroupTip> getWeeklyLottoGroupTips(){ return weeklyLottoGroupTips; }	
	public List<TotoGroupTip> getTotoGroupTips(){ return totoGroupTips; }	

	public void addDailyLottoGroupTip(DailyLottoGroupTip tip){ dailyLottoGroupTips.add(tip); }	
	public void addWeeklyLottoGroupTip(WeeklyLottoGroupTip tip){ weeklyLottoGroupTips.add(tip); }	
	public void addTotoGroupTip(TotoGroupTip tip){ totoGroupTips.add(tip); }
}
