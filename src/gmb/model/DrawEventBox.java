package gmb.model;

import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;

import java.util.LinkedList;
import java.util.List;

//no persistence required
public class DrawEventBox 
{
	protected List<WeeklyLottoDraw> weeklyLottoDrawings = new LinkedList<WeeklyLottoDraw>();
	protected List<DailyLottoDraw> dailyLottoDrawings = new LinkedList<DailyLottoDraw>();
	protected List<TotoEvaluation> totoEvaluations = new LinkedList<TotoEvaluation>();
	
	public DrawEventBox(){}
	
	public void addWeeklyLottoDrawing(WeeklyLottoDraw draw){ weeklyLottoDrawings.add(draw); }
	public void addDailyLottoDrawing(DailyLottoDraw draw){ dailyLottoDrawings.add(draw); }
	public void addTotoEvaluation(TotoEvaluation eval){ totoEvaluations.add(eval); }
	
	public List<WeeklyLottoDraw> getWeeklyLottoDrawings(){ return weeklyLottoDrawings; }
	public List<DailyLottoDraw> getDailyLottoDrawings(){ return dailyLottoDrawings; }
	public List<TotoEvaluation> getTotoEvaluations(){ return totoEvaluations; }
}
