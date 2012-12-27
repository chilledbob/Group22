package gmb.model.tip.draw.container;

import gmb.model.PersiObject;
import gmb.model.tip.draw.TotoEvaluation;

import java.util.Date;

import org.joda.time.DateTime;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FootballGameData extends PersiObject
{	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date matchDay;
	
	@ManyToOne(fetch=FetchType.LAZY)
	protected TotoEvaluation totoEvaluation;
	
	protected String homeClubName;
	protected String visitorClubName;
	
	protected String homeResult;
	protected String visitorResult;
	
	@Deprecated
	protected FootballGameData(){}
	
	public FootballGameData(DateTime matchDay, String homeClubName, String visitorClubName)
	{
		this.matchDay = matchDay.toDate();
		this.homeClubName = homeClubName;
		this.visitorClubName = visitorClubName;
		
		homeResult = "-";
		visitorResult = "-";
	}

	public void setResults(int homeResult, int visitorResult)
	{
		this.homeResult = (new Integer(homeResult)).toString();
		this.visitorResult = (new Integer(visitorResult)).toString();
	}
	
	public DateTime getMatchDay(){ return new DateTime(matchDay); }
	
	public String getHomeClubName(){ return homeClubName; }
	public String getVisitorClubName(){ return visitorClubName; }
	
	public String getHomeResult(){ return homeResult; }
	public String getVisitorResult(){ return visitorResult; }
}
