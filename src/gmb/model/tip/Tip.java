package gmb.model.tip;

import gmb.model.Lottery;


import org.joda.time.DateTime;

public abstract class Tip 
{
	protected DateTime submissionDate;
	protected Draw draw;
	
	@Deprecated
	protected Tip(){}
	
	public Tip(Draw draw)
	{
		this.draw = draw;
		
		submissionDate = Lottery.getInstance().getTimer().getDateTime();
	}
	
	public Draw getDraw(){ return draw; }
	
	
	public int withdraw()
	{
		if(draw.getEvaluated())
			return 1;
		else
			return 0;
	}	
}
