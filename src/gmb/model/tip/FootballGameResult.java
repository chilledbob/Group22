package gmb.model.tip;
import java.util.Date;


public class FootballGameResult {

	private Date matchDay;
	private PartialResult homeResult;
	private PartialResult visitorResult;
	
	public Date getMatchDay(){
		return matchDay;
	}
	
	public PartialResult getHomeResult(){
		return homeResult;
	}
	
	public PartialResult getVisitorResult(){
		return visitorResult;
	}
}
