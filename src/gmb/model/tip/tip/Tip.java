package gmb.model.tip.tip;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.financial.transaction.Winnings;
import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;


import org.joda.time.DateTime;

@Entity
public abstract class Tip extends PersiObject
{		
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date submissionDate;
	@ManyToOne
	protected Draw draw;
	
	@OneToOne
	@PrimaryKeyJoinColumn(name="TIP_PERSISTENCEID")
	protected Winnings overallWinnings;
	
	@Deprecated
	protected Tip(){}
	
	public Tip(Draw draw)
	{
		this.draw = draw;
		
		overallWinnings = null;
		draw = null;
		
		submissionDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	public Draw getDraw(){ return draw; }
		
	/**
	 * [intended for direct usage by controller]
	 * Tries to withdraw the tip with all implications.
	 * @return
	 */
	public int withdraw()
	{
		if(draw.getEvaluated())
			return 1;
		else
			return 0;
	}	
	
	public void setOverallWinnings(Winnings overallWinnings){ this.overallWinnings = overallWinnings; DB_UPDATE(); }
	public Winnings getOverallWinnings(){ return overallWinnings; }
	
	public DateTime getSubmissionDate(){ return new DateTime(submissionDate); }
	public abstract Customer getOwner();
}
