package gmb.model.tip;

import java.util.Date;

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
	
	protected Date submissionDate;
	@ManyToOne
	protected Draw draw;
	
	@Deprecated
	protected Tip(){}
	
	public Tip(Draw draw)
	{
		this.draw = draw;
		
		submissionDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	public Draw getDraw(){ return draw; }
		
	public int withdraw()
	{
		if(draw.getEvaluated())
			return 1;
		else
			return 0;
	}	
	
	public DateTime getSubmissionDate(){ return new DateTime(submissionDate); }
}
