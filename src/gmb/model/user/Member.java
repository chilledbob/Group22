package gmb.model.user;

import gmb.model.Lottery;
import gmb.model.request.MemberDataUpdateRequest;
import gmb.model.request.Notification;

import java.util.LinkedList;

import org.joda.time.DateTime;
import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.UserIdentifier;


public abstract class Member extends PersistentUser 
{
	//ATTRIBUTES	
	protected boolean activated = false;
	protected DateTime registrationDate;
	protected MemberData memberData;
	protected LinkedList<MemberDataUpdateRequest> memberDataUpdateRequest;
	protected LinkedList<Notification> notifications;
	
	//CONSTRUCTOR
	@Deprecated
	protected Member(){}
	
	public Member(String nickName, String password, MemberData memberData)
	{
		super(new UserIdentifier(nickName), password);
		this.memberData = memberData;
		
		registrationDate = Lottery.getInstance().getTimer().getDateTime();
		
		memberDataUpdateRequest = new LinkedList<MemberDataUpdateRequest>();
		notifications = new LinkedList<Notification>();
	}	
	
	//SET/ADD METHODS
	public void setMemberData(MemberData memberData){ this.memberData = memberData; }
	public void addNotification(Notification notification){ this.notifications.add(notification); }
	
	//GET METHODS
	public MemberData getMemberData(){ return memberData; }	
	public DateTime getRegistrationDate(){ return registrationDate; }
	
	public LinkedList<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return memberDataUpdateRequest; }
	public LinkedList<Notification> getNotifications(){ return notifications; }


	//OTHERS METHODS
	public void activateAccount(){ activated = true; }
	
	/**
	 * create a new "MemberDataUpdateRequest" and adds a reference of the request to the "MemberManagement"
	 * and the "Member" himself
	 * @param note
	 * @param updatedData
	 */
	public void sendDataUpdateRequest(String note, MemberData updatedData)	
	{
		MemberDataUpdateRequest request = new MemberDataUpdateRequest(updatedData, this, note);

		Lottery.getInstance().getMemberManagement().addMemberDataUpdateRequest(request);

		memberDataUpdateRequest.add(request);
	}	
}
