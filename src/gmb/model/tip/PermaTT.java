package gmb.model.tip;

import gmb.model.user.Customer;

import java.util.LinkedList;

import org.joda.time.DateTime;
import org.joda.time.Duration;


public abstract class PermaTT extends TipTicket 
{
	protected LinkedList<SingleTip> tips;
	
	protected int durationType;	
	protected final static long millisecondsOfDay = 1000*60*60*24;
//	protected DateTime durationDate;
	
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
			Duration duration;
			
			switch(durationType)
			{
			case 1 : duration = new Duration(millisecondsOfDay*182);
			case 2 : duration = new Duration(millisecondsOfDay*365);
			default : duration = new Duration(millisecondsOfDay*30);
			}
			
			DateTime durationDate = purchaseDate.plus(duration);
			
			if(durationDate.isBeforeNow())
				expired = true;
			
			return expired;
		}
		else
			return true;
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
	
	public boolean addTip(SingleTip tip)
	{ 
		if(!isExpired())
		tips.add(tip); 
		
		return !expired;
	}
	
	protected boolean addTip(SingleTip tip, Class<?> tipType)
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
	
	public LinkedList<SingleTip> getTips(){ return tips; }	

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
