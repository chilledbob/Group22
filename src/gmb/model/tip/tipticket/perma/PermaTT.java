package gmb.model.tip.tipticket.perma;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;
import javax.persistence.Entity;

import org.eclipse.persistence.annotations.Convert;
import org.eclipse.persistence.annotations.Converter;
import org.joda.time.DateTime;

/**
 * Abstract super class for all perma tip tickets.
 * Perma tip tickets can be used once per drawing until they expire.
 */
@Entity
public abstract class PermaTT extends TipTicket 
{
	protected int[] tip;
	
	@OneToMany(mappedBy="permaTT")
	protected List<SingleTip> tips;

	protected int durationType;	
	protected final static long millisecondsOfDay = 1000*60*60*24;

	protected boolean expired;

	@Deprecated
	protected PermaTT(){}

	public PermaTT(PTTDuration duration)
	{
		super();
		tip = null;
		expired = false;
		this.setDuration(duration);
		tips = new LinkedList<SingleTip>();
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Checks whether the ticket's duration has expired by now.
	 * @return false if expired, otherwise true.
	 */
	public boolean isExpired()
	{
		if(!expired)
		{						
			if(this.getDurationDate().isBefore(Lottery.getInstance().getTimer().getDateTime()))
				expired = true;

			DB_UPDATE(); 
			
			return expired;
		}
		else
			return true;
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * @return duration date of the ticket.
	 */
	public DateTime getDurationDate()
	{
		DateTime durationDate = new DateTime(purchaseDate);

		switch(durationType)
		{
		case 1 : durationDate = durationDate.plusMonths(6); break;
		case 2 : durationDate = durationDate.plusYears(1); break;
		default : durationDate = durationDate.plusMonths(1); break;
		}
		
		return durationDate;
	}

	public boolean removeTip(SingleTip tip)
	{
		boolean result = this.tips.remove(tip);
		DB_UPDATE(); 
		
		return result;
	}

	/**
	 * Adds the tip to the "tips" list if the ticket's duration hasn't been expired.
	 * @param tip
	 * @return
	 */
	protected int addTip(SingleTip tip, Class<?> tipType)
	{ 
		assert tip.getClass() == tipType : "Wrong type given to PermaTT.addTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		
		if(this.isExpired()) return -1;
		if(tips.contains(tip)) return 2;

		for(SingleTip stip : tips)
			if(stip.getDraw() == tip.getDraw())
				return 5;
		
		tips.add(tip); 
		DB_UPDATE(); 
		
		return 0;	
	}

	public void setDuration(PTTDuration duration)
	{
		if(duration == PTTDuration.Month)
			durationType = 0;
		else
			if(duration == PTTDuration.Halfyear)
				durationType = 1;
			else
				durationType = 2;
		
		DB_UPDATE(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Changes the tipped result if it is valid. 
	 * This result will be tipped for each automatically created SingleTip.
	 * @param tip The new tipped result or null (default) to deactivate auto tip creation.
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>   - check {@link WeeklyLottoPTT.validateTip(int[] tip)} and {@link DailyLottoPTT.validateTip(int[] tip)} for further failure codes
	 * <ul>
	 */
	public int setTip(int[] tip)
	{
//		if(tip != null)
//		{
//			int result = this.validateTip(tip);
//			if(result != 0) return result;
//		}
//		------------------------------------making extra objects for validating a tip troubles the database and I had no time to fix it yet-------

		this.tip = tip;
		DB_UPDATE();

		return 0;
	}
	
	public abstract int validateTip(int[] tip);
	public int[] getTip(){return tip; }
	
	public void setDurationType(int durationType){ this.durationType = durationType; DB_UPDATE(); }
	public void setExpired(boolean expired){ this.expired = expired; DB_UPDATE(); }

	public LinkedList<SingleTip> getTips(){ return (LinkedList<SingleTip>) tips; }	
	public SingleTip getLastTip(){ return ((LinkedList<SingleTip>)tips).getLast(); }
			
	public PTTDuration getDuration()
	{
		switch(durationType)
		{
		case 1 : return PTTDuration.Halfyear;
		case 2 : return PTTDuration.Year;
		default : return PTTDuration.Month;
		}
	}

	public int getDurationTypeAsInt(){ return durationType; }
	public boolean getExpired(){ return expired; }
}
