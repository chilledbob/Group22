package gmb.model.tip.draw.container;

import gmb.model.PersiObject;

import java.util.Date;

import org.joda.time.DateTime;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FootballGameData extends PersiObject
{	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date matchDay;
	
	protected String homeClubName;
	protected String visitorClubName;
	
	@Deprecated
	protected FootballGameData(){}
	
	public FootballGameData(DateTime matchDay, String homeClubName, String visitorClubName)
	{
		this.matchDay = matchDay.toDate();
		this.homeClubName = homeClubName;
		this.visitorClubName = visitorClubName;
	}

	public DateTime getMatchDay(){ return new DateTime(matchDay); }
	public String getHomeClubName(){ return homeClubName; }
	public String getVisitorClubName(){ return visitorClubName; }
}
