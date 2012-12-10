package gmb.model.user;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.request.MemberDataUpdateRequest;
import gmb.model.request.Notification;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

import org.salespointframework.core.user.PersistentUser;
import org.salespointframework.core.user.UserIdentifier;

@Entity
public abstract class Member extends PersistentUser 
{
	@Column(name="activated")
	protected boolean activated = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date registrationDate;
	
	@ManyToOne
	protected MemberManagement memberManagementID;
	
	@OneToOne(cascade=CascadeType.ALL)
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
		
		registrationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		
		memberDataUpdateRequest = new LinkedList<MemberDataUpdateRequest>();
		notifications = new LinkedList<Notification>();
	}	
	
	public void setMemberData(MemberData memberData){ this.memberData = memberData; GmbPersistenceManager.update(this); }
	public void addNotification(Notification notification){ this.notifications.add(notification); }
	public void addNotification(String notification){ this.notifications.add(new Notification(notification)); }

	public MemberData getMemberData(){ return memberData; }	
	public DateTime getRegistrationDate(){ return new DateTime(registrationDate); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return memberDataUpdateRequest; }
	public List<Notification> getNotifications(){ return notifications; }


	public void activateAccount(){ activated = true; }
	
	/**
	 * create a new "MemberDataUpdateRequest" and adds a reference of the request to the "MemberManagement"
	 * and the "Member" himself
	 * @param note
	 * @param updatedData
	 */
	public void sendDataUpdateRequest(MemberData updatedData, String note)	
	{
		MemberDataUpdateRequest request = new MemberDataUpdateRequest(updatedData, this, note);

		Lottery.getInstance().getMemberManagement().addMemberDataUpdateRequest(request);

		memberDataUpdateRequest.add(request);
	}	
}
