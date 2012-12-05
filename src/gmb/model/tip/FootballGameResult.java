package gmb.model.tip;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class FootballGameResult {

	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int footballGameResultId;
	@Temporal(value = TemporalType.DATE)
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
