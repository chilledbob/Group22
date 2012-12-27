package gmb.model.request;
import java.util.Date;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gmb.model.member.Member;

@Entity
public abstract class Request extends Notification
{ 

	protected int state;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date lastStateChangeDate;

	@Deprecated
	protected Request(){}

	public Request(Member member, String note)
	{
		super(note);
		this.member = member;
		
		this.state = 0;//RequestState.UNHANDELED
		
		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}

	public Member getMember(){ return member; }

	public RequestState getState()
	{
		if(this.state == 1){ return RequestState.Withdrawn; }
		else
			if(this.state == 2){ return RequestState.Accepted; }
			else
				if(this.state == 3){ return RequestState.Refused; }
				else
					return RequestState.Unhandled;
	}

	/**
	 * returns the value of the integer which internally encodes the state
	 * @return
	 */
	public int getStateAsInt(){ return state; }

	//	public void setNote(String note){ this.note = note; }

	//	public void setState(RequestState state)
	//	{
	//		if(state == RequestState.WITHDRAWN){ this.state = 1; }
	//		else
	//			if(state == RequestState.ACCEPTED){ this.state = 2; }
	//			else
	//				if(state == RequestState.REFUSED){ this.state = 3; }
	//				else
	//					this.state = 0; 
	//	}

	public void resetState()
	{ 
		state = 0; 
		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate(); 
		DB_UPDATE();
	}

	public boolean withdraw()
	{ 
		if(state != 0) 
			return false; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 1; 
		DB_UPDATE();
		return true;
	}

	public int accept()
	{ 
		if(state != 0) 
			return 1; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 2; 
		//DB_UPDATE();
		return 0;
	}

	public boolean refuse()
	{ 
		if(state != 0) 
			return false; 

		lastStateChangeDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		state = 3; 
		DB_UPDATE();
		return true;
	}
	
	public DateTime getLastStateChangeDate(){ return new DateTime(lastStateChangeDate); }
}
