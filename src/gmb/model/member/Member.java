package gmb.model.member;

import gmb.model.GmbFactory;
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
import javax.persistence.FetchType;
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
public class Member extends PersistentUser 
{
	@Column(name="activated")
	protected boolean activated = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date registrationDate;
	
	@ManyToOne
	protected MemberManagement memberManagement;
	
	@OneToOne(fetch=FetchType.EAGER) 
    @JoinColumn(name="memberDataId") 
	protected MemberData memberData;
	@OneToMany(mappedBy="member",fetch=FetchType.EAGER)
	protected List<MemberDataUpdateRequest> memberDataUpdateRequest;
	@OneToMany
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	protected List<Notification> notifications;
	
	protected int type;//0-Employee, 1-Admin, 2-Notary, 3-Customer
	
	public Member DB_ADD(){ return GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
	
	@Deprecated
	protected Member(){}
	
	public Member(String nickName, String password, MemberData memberData, MemberType type)
	{
		super(new UserIdentifier(nickName), password);
		
		if(type == MemberType.Employee)
		{
			this.type = 0;
			this.addCapability(new Capability("employee"));
		}
		else
			if(type == MemberType.Admin)
			{
				this.type = 1;
				this.addCapability(new Capability("admin"));
			}
			else
				if(type == MemberType.Notary)
				{
					this.type = 2;
					this.addCapability(new Capability("notary"));
				}
				else
				{
					this.type = 3;
					this.addCapability(new Capability("customer"));
				}
		
		this.memberData = memberData;
		
		registrationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		
		memberDataUpdateRequest = new LinkedList<MemberDataUpdateRequest>();
		notifications = new LinkedList<Notification>();
		
		this.memberManagement = Lottery.getInstance().getMemberManagement();
	}	
	
	public MemberType getType()
	{
		switch(this.type)
		{
		case 0: return MemberType.Employee;
		case 1: return MemberType.Admin;
		case 2: return MemberType.Notary;
		default : return MemberType.Customer;
		}
	}
	
	public int getTypeAsInt(){ return type; }
	
	public void setMemberData(MemberData memberData){ this.memberData = memberData; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param notification
	 */
//	public void addNotification(Notification notification){ this.notifications.add(notification); DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param notification
	 */
	public void addNotification(String notification){ this.notifications.add(GmbFactory.new_Notification(notification)); DB_UPDATE(); }

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
	 * Creates a new "MemberDataUpdateRequest" and adds a reference of the request to the "MemberManagement"
	 * and the "Member" himself. Returns the created request.
	 * @param note
	 * @param updatedData
	 */
	public MemberDataUpdateRequest sendDataUpdateRequest(MemberData updatedData, String note)	
	{
		//MemberDataUpdateRequest request =  GmbFactory.new_MemberDataUpdateRequest(updatedData, this, note);
		MemberDataUpdateRequest request = new MemberDataUpdateRequest(updatedData, this, note);
		Lottery.getInstance().getMemberManagement().addMemberDataUpdateRequest(request);

		memberDataUpdateRequest.add(request);
		
		DB_UPDATE(); 
		
		return request;
	}	
}
