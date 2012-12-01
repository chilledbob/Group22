package gmb.model.tip;
import java.util.LinkedList;

import org.joda.time.Duration;


public class TipManagement 
{
	protected LinkedList<WeeklyLottoDraw> weeklyLottoDrawings;
	protected LinkedList<DailyLottoDraw> dailyLottoDrawings;
	protected LinkedList<TotoEvaluation> totoEvaluations;
	
	protected Duration tipSubmissionTimeLimit;
	
	@Deprecated
	protected TipManagement(){}
	
	public TipManagement(long tipSubmissionTimeLimitInMilliSeconds)
	{
		weeklyLottoDrawings = new LinkedList<WeeklyLottoDraw>();
		dailyLottoDrawings = new LinkedList<DailyLottoDraw>();
		totoEvaluations = new LinkedList<TotoEvaluation>();
		
		tipSubmissionTimeLimit = new Duration(tipSubmissionTimeLimitInMilliSeconds);
	}
	
	public void addDraw(WeeklyLottoDraw draw){ weeklyLottoDrawings.add(draw); }
	public void addDraw(DailyLottoDraw draw){ dailyLottoDrawings.add(draw); }
	public void addDraw(TotoEvaluation draw){ totoEvaluations.add(draw); }
	
	public void setTipSubmissionTimeLimit(Duration tipSubmissionTimeLimit){  this.tipSubmissionTimeLimit = tipSubmissionTimeLimit; }
	
	public LinkedList<WeeklyLottoDraw> getWeeklyLottoDrawings(){ return weeklyLottoDrawings; }
	public LinkedList<DailyLottoDraw> getDailyLottoDrawings(){ return dailyLottoDrawings; }
	public LinkedList<TotoEvaluation> getTotoEvaluations(){ return totoEvaluations; }
	
	public Duration getTipSubmissionTimeLimit(){ return tipSubmissionTimeLimit; }
}
