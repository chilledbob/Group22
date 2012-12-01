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

import java.util.LinkedList;

import org.joda.time.DateTime;


public class Group 
{
	//ATTRIBUTES
	protected String name;
	protected String infoText;
	protected DateTime foundingDate;
	protected Boolean closed = false;

	protected Customer groupAdmin;
	protected LinkedList<Customer> groupMembers;

	protected LinkedList<DailyLottoGroupTip> dailyLottoGroupTips;
	protected LinkedList<WeeklyLottoGroupTip> weeklyLottoGroupTips;
	protected LinkedList<TotoGroupTip> totoGroupTips;

	protected LinkedList<GroupInvitation> groupInvitations;
	protected LinkedList<GroupAdminRightsTransfereOffering> groupAdminRightsTransfereOfferings;
	protected LinkedList<GroupMembershipApplication> groupMembershipApplications;

	//CONSTRUCTORS
	@Deprecated
	protected Group(){}

	public Group(String name, Customer groupAdmin, String infoText)
	{
		this.name = name;
		this.infoText = infoText;
		this.groupAdmin = groupAdmin;

		foundingDate = Lottery.getInstance().getTimer().getDateTime();

		dailyLottoGroupTips = new LinkedList<DailyLottoGroupTip>();
		weeklyLottoGroupTips = new LinkedList<WeeklyLottoGroupTip>();
		totoGroupTips = new LinkedList<TotoGroupTip>();

		groupMembers =  new LinkedList<Customer>();
		groupInvitations = new LinkedList<GroupInvitation>();
		groupAdminRightsTransfereOfferings = new LinkedList<GroupAdminRightsTransfereOffering>();
		groupMembershipApplications = new LinkedList<GroupMembershipApplication>();
	}

	/**
	 * creates a "GroupMembershipApplication" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public void applyForMembership(Customer customer, String note)
	{
		GroupMembershipApplication application = new GroupMembershipApplication(this, customer, note);

		customer.getGroupMembershipApplications().add(application);
		this.groupMembershipApplications.add(application);
	}

	/**
	 * creates a "GroupInvitation" and adds it to the "customer" and this group
	 * @param customer
	 * @param note
	 */
	public void sendGroupInvitation(Customer customer, String note)
	{
		GroupInvitation application = new GroupInvitation(this, customer, note);

		customer.getGroupInvitations().add(application);
		this.groupInvitations.add(application);
	}

	/**
	 * creates a "GroupAdminRightsTransfereOffering" and adds it to the "groupMember" and this group
	 * @param groupMember
	 * @param note
	 */
	public void sendGroupAdminRightsTransfereOffering(Customer groupMember, String note)
	{
		GroupAdminRightsTransfereOffering application = new GroupAdminRightsTransfereOffering(this, groupMember, note);

		groupMember.getGroupAdminRightsTransfereOfferings().add(application);
		this.groupAdminRightsTransfereOfferings.add(application);
	}

	/**
	 * resigning a "groupMember" by withdrawing all unhandled group related requests in "groupMember",
	 * sending a "Notification" to the member and removing it from the "groupMembers" list 
	 * @param groupMember
	 */
	public void resign(Customer groupMember)
	{	
		if(groupMembers.contains(groupMember))
		{
			withdrawUnhandledGroupRequestsOfGroupMember(groupMember);

			groupMember.addNotification(new Notification("You have been resigned from Group " + name + "."));
			groupMembers.remove(groupMember);
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
		for(Customer groupMember : groupMembers)
		{
			if(groupMembers.contains(groupMember))
			{
				withdrawUnhandledGroupRequestsOfGroupMember(groupMember);
				groupMember.addNotification(new Notification("The group " + name + " has been closed. You will be automatically resigned."));
			}
		}
		
		groupMembers.clear();
		
		withdrawUnhandledGroupRequestsOfGroupMember(groupAdmin);
		groupAdmin.addNotification(new Notification("The group " + name + ", where you had admin status, has been closed. You will be automatically resigned."));

		closed = true;
	}

	//SET/ADD METHODS 
	public void SetInfoText(String infoText){ this.infoText = infoText; }	
	public void setGroupAdmin(Customer groupAdmin){ this.groupAdmin = groupAdmin; }
	public void addGroupMember(Customer customer){ groupMembers.add(customer); }

	public LinkedList<GroupAdminRightsTransfereOffering> getGroupAdminRightsTransfereOfferings(){ return groupAdminRightsTransfereOfferings; }
	public LinkedList<GroupInvitation> getGroupInvitations(){ return groupInvitations; }
	public LinkedList<GroupMembershipApplication> getGroupMemberShipApplications(){ return groupMembershipApplications; }	

	//GET METHODS
	public String getInfoText(){ return infoText; }	
	public Customer getGroupAdmin(){ return groupAdmin; }
	public DateTime getFoundingDate(){ return foundingDate; }

	public LinkedList<DailyLottoGroupTip> getDailyLottoGroupTips(){ return dailyLottoGroupTips; }	
	public LinkedList<WeeklyLottoGroupTip> getWeeklyLottoGroupTips(){ return weeklyLottoGroupTips; }	
	public LinkedList<TotoGroupTip> getTotoGroupTips(){ return totoGroupTips; }	

	public void addDailyLottoGroupTip(DailyLottoGroupTip tip){ dailyLottoGroupTips.add(tip); }	
	public void addWeeklyLottoGroupTip(WeeklyLottoGroupTip tip){ weeklyLottoGroupTips.add(tip); }	
	public void addTotoGroupTip(TotoGroupTip tip){ totoGroupTips.add(tip); }
}
