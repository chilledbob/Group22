package gmb.model.tip;
import gmb.model.PersiObject;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.joda.time.Duration;

@Entity
public class TipManagement extends PersiObject
{
	@Id
	protected int tipManagementId = 1;
	
	@OneToMany(mappedBy="tipManagementId")
	protected List<WeeklyLottoDraw> weeklyLottoDrawings;
	@OneToMany(mappedBy="tipManagementId")
	protected List<DailyLottoDraw> dailyLottoDrawings;
	@OneToMany(mappedBy="tipManagementId")
	protected List<TotoEvaluation> totoEvaluations;
	
	protected long tipSubmissionTimeLimitInMilliSeconds = 5*60*1000;//five minutes
	
	public TipManagement()
	{
		weeklyLottoDrawings = new LinkedList<WeeklyLottoDraw>();
		dailyLottoDrawings = new LinkedList<DailyLottoDraw>();
		totoEvaluations = new LinkedList<TotoEvaluation>();
	}
	
	public void addDraw(WeeklyLottoDraw draw){ weeklyLottoDrawings.add(draw); DB_UPDATE(); }
	public void addDraw(DailyLottoDraw draw){ dailyLottoDrawings.add(draw); DB_UPDATE(); }
	public void addDraw(TotoEvaluation draw){ totoEvaluations.add(draw); DB_UPDATE(); }
	
	public void setTipSubmissionTimeLimit(long tipSubmissionTimeLimitInMilliSeconds){  this.tipSubmissionTimeLimitInMilliSeconds = tipSubmissionTimeLimitInMilliSeconds; DB_UPDATE(); }
	
	public List<WeeklyLottoDraw> getWeeklyLottoDrawings(){ return weeklyLottoDrawings; }
	public List<DailyLottoDraw> getDailyLottoDrawings(){ return dailyLottoDrawings; }
	public List<TotoEvaluation> getTotoEvaluations(){ return totoEvaluations; }
	
	public Duration getTipSubmissionTimeLimit(){ return new Duration(tipSubmissionTimeLimitInMilliSeconds); }
}
