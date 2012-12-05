package gmb.model.tip;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import gmb.model.Lottery;


import org.joda.time.DateTime;

@Entity
public abstract class Tip 
{	
	@Id
	@GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int tipId;
	
	protected DateTime submissionDate;
	@ManyToOne
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
