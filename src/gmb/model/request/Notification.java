package gmb.model.request;

import gmb.model.Lottery;

import org.joda.time.DateTime;

public class Notification 
{
	protected String note;
	protected DateTime date;

	@Deprecated
	protected Notification(){}

	public Notification(String note)
	{
		this.note = note;
		
		date = Lottery.getInstance().getTimer().getDateTime();
	}
	
	public DateTime getDate(){ return date; }
}
