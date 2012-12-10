package gmb.model;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.salespointframework.core.time.DefaultTime;

public class Timer extends DefaultTime 
{
	protected Duration offset1 = new Duration(0);
	protected Duration offset2 = new Duration(0);
	
	public Timer()
	{
		super();
	}
	
	public void setReferenceDate(DateTime refDate)
	{
		offset1 = new Duration(super.getDateTime(), refDate);
		offset2 = new Duration(0);
	}
	
	public void addMinutes(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60 * minuteCount)); }
	public void addHours(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60 * minuteCount)); }
	public void addDays(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24 * minuteCount)); }
	public void addWeeks(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*7 * minuteCount)); }
	public void addMonths(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*30 * minuteCount)); }
	public void addYears(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*365 * minuteCount)); }
	
	public void addToOffset(Duration offset){ this.offset2 = this.offset2.plus(offset); }
	
	public DateTime getDateTime()
	{
		DateTime actualDateTime = super.getDateTime();	
		return actualDateTime.plus(offset1).plus(offset2);
	}
	
	public void setOffset(Duration offset){ this.offset2 = offset; }
	public void resetOffset(){ offset2 = new Duration(0); }
	public Duration getOffset(){ return offset2; }
}
