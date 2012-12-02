package gmb.model.tip;

import org.joda.time.DateTime;


public class FootballGameResult 
{
	protected DateTime matchDay;
	protected PartialResult homeResult;
	protected PartialResult visitorResult;
	
	@Deprecated
	protected FootballGameResult(){}
	
	public FootballGameResult(DateTime matchDay, PartialResult homeResult, PartialResult visitorResult)
	{
		this.matchDay = matchDay;
		this.homeResult = homeResult;
		this.visitorResult = visitorResult;
	}

	public DateTime getMatchDay(){ return matchDay; }
	public PartialResult getHomeResult(){ return homeResult; }
	public PartialResult getVisitorResult(){ return visitorResult; }
}
