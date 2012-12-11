package gmb.model.tip.draw.container;


import gmb.model.PersiObject;

import java.util.Date;

import org.joda.time.DateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FootballGameResult extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int footballGameResultId;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date matchDay;
	protected PartialResult homeResult;
	protected PartialResult visitorResult;
	
	@Deprecated
	protected FootballGameResult(){}
	
	public FootballGameResult(DateTime matchDay, PartialResult homeResult, PartialResult visitorResult)
	{
		this.matchDay = matchDay.toDate();
		this.homeResult = homeResult;
		this.visitorResult = visitorResult;
	}

	public DateTime getMatchDay(){ return new DateTime(matchDay); }
	public PartialResult getHomeResult(){ return homeResult; }
	public PartialResult getVisitorResult(){ return visitorResult; }
}
