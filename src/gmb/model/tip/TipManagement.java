package gmb.model.tip;
import gmb.model.DrawEventBox;
import gmb.model.PersiObject;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Container class storing tips and drawings related data.
 */
@Entity
public class TipManagement extends PersiObject
{
	@OneToMany(mappedBy="tipManagementId")
	protected List<WeeklyLottoDraw> weeklyLottoDrawings;
	@OneToMany(mappedBy="tipManagementId")
	protected List<DailyLottoDraw> dailyLottoDrawings;
	@OneToMany(mappedBy="tipManagementId")
	protected List<TotoEvaluation> totoEvaluations;
	
	protected long tipSubmissionTimeLimitInMilliSeconds;
	
	protected static final Duration zeroDuration = new Duration(0);
	
	@Deprecated
	protected TipManagement(){}
	
	public TipManagement(Object dummy)
	{
		tipSubmissionTimeLimitInMilliSeconds = 5*60*1000;//five minutes
		
		weeklyLottoDrawings = new LinkedList<WeeklyLottoDraw>();
		dailyLottoDrawings = new LinkedList<DailyLottoDraw>();
		totoEvaluations = new LinkedList<TotoEvaluation>();
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Accumulates all drawing on the given date and returns them in a {@link DrawEventBox}.
	 * @param date
	 * @return {@link DrawEventBox} object containing all drawings on the given date
	 */
	public DrawEventBox getDrawEventsOnDate(DateTime date)
	{
		DrawEventBox box = new DrawEventBox();
		
		DateTime lowerBound = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 0, 0, 0);
		DateTime upperBound = new DateTime(date.getYear(), date.getMonthOfYear(), date.getDayOfMonth(), 23, 59, 59);
		
		for(WeeklyLottoDraw draw : weeklyLottoDrawings)
		{
			if(draw.getPlanedEvaluationDate().isAfter(lowerBound) && draw.getPlanedEvaluationDate().isBefore(upperBound))
				box.addWeeklyLottoDrawing(draw);		
		}
		
		for(DailyLottoDraw draw : dailyLottoDrawings)
		{
			if(draw.getPlanedEvaluationDate().isAfter(lowerBound) && draw.getPlanedEvaluationDate().isBefore(upperBound))
				box.addDailyLottoDrawing(draw);
		}
		
		for(TotoEvaluation eval : totoEvaluations)
		{
			if(eval.getPlanedEvaluationDate().isAfter(lowerBound) && eval.getPlanedEvaluationDate().isBefore(upperBound))
				box.addTotoEvaluation(eval);
		}
		
		return box;
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
