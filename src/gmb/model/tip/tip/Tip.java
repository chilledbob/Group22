package gmb.model.tip.tip;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gmb.model.PersiObject;
import gmb.model.PersiObjectSingleTable;
import gmb.model.financial.transaction.Winnings;
import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;
import org.joda.time.DateTime;

/**
 * Abstract super class for all tip types.
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Tip extends PersiObjectSingleTable
{
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date submissionDate;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="DRAW_PERSISTENCEID")
	@JoinFetch(JoinFetchType.INNER)
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
	}
	
	public Draw getDraw(){ return draw; }
	
	public void setOverallWinnings(Winnings overallWinnings){ this.overallWinnings = overallWinnings; DB_UPDATE(); }
	public Winnings getOverallWinnings(){ return overallWinnings; }
	
	public DateTime getSubmissionDate(){ return new DateTime(submissionDate); }
	public abstract Customer getOwner();
}
