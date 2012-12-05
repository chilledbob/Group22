package gmb.model.user;

import gmb.model.Lottery;
import gmb.model.request.MemberDataUpdateRequest;
import gmb.model.request.Notification;

import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.UserIdentifier;

@Entity
public abstract class Member extends PersistentUser 
{
	//ATTRIBUTES	
	protected boolean activated = false;
	@Temporal(value = TemporalType.DATE)
	protected DateTime registrationDate;
	
	@OneToOne 
    @JoinColumn(name="memberDataId") 
	protected MemberData memberData;
	@OneToMany(mappedBy="member")
	protected List<MemberDataUpdateRequest> memberDataUpdateRequest;
	@OneToMany(mappedBy="member")
	@JoinColumn(name="member", referencedColumnName="member_ID")
	protected List<Notification> notifications;
	
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
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return memberDataUpdateRequest; }
	public List<Notification> getNotifications(){ return notifications; }


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
