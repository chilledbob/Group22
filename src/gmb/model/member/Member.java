package gmb.model.member;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.member.container.MemberData;
import gmb.model.request.Notification;
import gmb.model.request.data.MemberDataUpdateRequest;

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

import org.salespointframework.core.user.Capability;
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
	
	public void DB_ADD(){ GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
	
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
	
	public void setMemberData(MemberData memberData){ this.memberData = memberData; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param notification
	 */
	public void addNotification(Notification notification){ this.notifications.add(notification); DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param notification
	 */
	public void addNotification(String notification){ this.notifications.add(new Notification(notification)); DB_UPDATE(); }

	public MemberData getMemberData(){ return memberData; }	
	public DateTime getRegistrationDate(){ return new DateTime(registrationDate); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return memberDataUpdateRequest; }
	public List<Notification> getNotifications(){ return notifications; }

	/**
	 * [intended for direct usage by controller]
	 * Sets "activated" to true and adds the capability "activated".
	 */
	public void activateAccount(){ activated = true;  this.addCapability(new Capability("activated")); DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * Sets "activated" to false and removes the capability "activated".
	 */
	public void deactivateAccount(){ activated = false; this.removeCapability(new Capability("activated")); DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * Create a new "MemberDataUpdateRequest" and adds a reference of the request to the "MemberManagement"
	 * and the "Member" himself.
	 * @param note
	 * @param updatedData
	 */
	public void sendDataUpdateRequest(MemberData updatedData, String note)	
	{
		MemberDataUpdateRequest request = new MemberDataUpdateRequest(updatedData, this, note);

		Lottery.getInstance().getMemberManagement().addMemberDataUpdateRequest(request);

		memberDataUpdateRequest.add(request);
		
		DB_UPDATE(); 
	}	
}
