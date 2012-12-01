package gmb.model.user;

import gmb.model.Lottery;
import gmb.model.request.MemberDataUpdateRequest;
import gmb.model.request.Notification;

import java.util.Date;
import java.util.LinkedList;

import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.UserIdentifier;


public class Member extends PersistentUser 
{
	//ATTRIBUTES	
	protected boolean activated = false;
	protected Date registrationDate;
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
	}	
	
	//SET/ADD METHODS
	public void setMemberData(MemberData memberData){ this.memberData = memberData; }
	public void addNotification(Notification notification){ this.notifications.add(notification); }
	
	//GET METHODS
	public MemberData getMemberData(){ return memberData; }	
	public Date getRegistrationDate(){ return registrationDate; }
	
	public LinkedList<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return memberDataUpdateRequest; }
	public LinkedList<Notification> getNotifications(){ return notifications; }


	//OTHERS METHODS
	public void activateAccount(){ activated = true; }
	
	public void sendDataUpdateRequest(String note, MemberData updatedData)	
	{
		MemberDataUpdateRequest request = new MemberDataUpdateRequest(updatedData, this, note);

		Lottery.getInstance().getMemberManagement().addMemberDataUpdateRequest(request);

		memberDataUpdateRequest.add(request);
	}	
}
