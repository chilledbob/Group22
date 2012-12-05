package gmb.model.tip;

import gmb.model.user.Customer;

import java.util.List;

import javax.persistence.OneToMany;
import javax.persistence.Entity;

import org.joda.time.DateTime;
import org.joda.time.Duration;

@Entity
public abstract class PermaTT extends TipTicket 
{
	@OneToMany(mappedBy="permaTT")
	protected List<SingleTip> tips;
	
	protected int durationType;	
	protected final static long millisecondsOfDay = 1000*60*60*24;
	//@Temporal(value = TemporalType.DATE)
//	protected Date durationDate;
	
	protected boolean expired = false;

	@Deprecated
	protected PermaTT(){}

	public PermaTT(Customer owner, PTTDuration duration)
	{
		super(owner);
		setDuration(duration);
	}
	
	/**
	 * checks whether the ticket's duration has expired by now
	 * @return
	 */
	public boolean isExpired()
	{
		if(!expired)
		{				
			DateTime durationDate = this.getDurationDate();
			
			if(durationDate.isBeforeNow())
				expired = true;
			
			return expired;
		}
		else
			return true;
	}
	
	public DateTime getDurationDate()
	{
		Duration duration;
		
		switch(durationType)
		{
		case 1 : duration = new Duration(millisecondsOfDay*182);
		case 2 : duration = new Duration(millisecondsOfDay*365);
		default : duration = new Duration(millisecondsOfDay*30);
		}
		
		return new DateTime(purchaseDate).plus(duration);
	}
	
	public boolean removeTip(SingleTip tip)
	{
		if(this.tips.contains(tip))
		{
			this.tips.remove(tip);
			return true;
		}
		else
		return false;
	}
	
	/**
	 * adds the tip to the "tips" list if the ticket's duration hasn't been expired
	 * @param tip
	 * @return
	 */
	public int addTip(SingleTip tip)
	{ 
		if(isExpired()) return -1;
		if(tips.contains(tip)) return 2;

		tips.add(tip); 
		return 0;		
	}

	protected int addTip(SingleTip tip, Class<?> tipType)
	{ 
		assert tip.getClass() == tipType : "Wrong type given to PermaTT.addTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		return addTip(tip);
	}
	
	public void setDuration(PTTDuration duration)
	{
		switch(duration)
		{
		case HALFYEAR : durationType = 1;
		case YEAR : durationType = 2;
		default : durationType = 0;
		}
	}
	
	public void setDurationType(int durationType){ this.durationType = durationType; }
	public void setExpired(boolean expired){ this.expired = expired; }
	
	public List<SingleTip> getTips(){ return tips; }	

	public PTTDuration getDuration()
	{
		switch(durationType)
		{
		case 1 : return PTTDuration.HALFYEAR;
		case 2 : return PTTDuration.YEAR;
		default : return PTTDuration.MONTH;
		}
	}
	
	public int getDurationType(){ return durationType; }
	public boolean getExpired(){ return expired; }
}
