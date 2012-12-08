package gmb.model.request;

import java.util.Date;

import gmb.model.Lottery;
import gmb.model.user.Member;

import org.joda.time.DateTime;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Notification 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int notificationId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userIdentifier", referencedColumnName="userIdentifier")
	protected Member member;
	
	protected String note;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date date;

	@Deprecated
	protected Notification(){}

	public Notification(String note)
	{
		this.note = note;
		
		date = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	public DateTime getDate(){ return new DateTime(date); }
	public String getNote(){ return note; }
}
