package gmb.model;

import gmb.model.tip.draw.DailyLottoDraw;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.salespointframework.core.time.DefaultTime;

public class Timer extends DefaultTime 
{
	protected Duration offset1 = new Duration(0);
	protected Duration offset2 = new Duration(0);
	
	public Timer()
	{
		super();
	}
	
	public void setReferenceDate(DateTime refDate)
	{
		offset1 = new Duration(super.getDateTime(), refDate);
		offset2 = new Duration(0);
	}
	
	protected void evaluateUnevaluatedDailyLottoDrawings()
	{
		for(DailyLottoDraw draw : Lottery.getInstance().getTipManagement().getDailyLottoDrawings())
		{
			Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), draw.getPlanedEvaluationDate());

			if(duration.isShorterThan(new Duration(0)))
				draw.evaluate();
		}
	}
	
	public void addMinutes(int minuteCount){ this.offset2 = this.offset2.plus(new Duration(1000*60 * minuteCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addHours(int hoursCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60 * hoursCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addDays(int daysCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24 * daysCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addWeeks(int weeksCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*7 * weeksCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addMonths(int monthCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*30 * monthCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addYears(int yearCount){ this.offset2 = this.offset2.plus(new Duration(1000*60*60*24*365 * yearCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	
	public void addToOffset(Duration offset){ this.offset2 = this.offset2.plus(offset); }
	
	public DateTime getDateTime()
	{
		DateTime actualDateTime = super.getDateTime();	
		return actualDateTime.plus(offset1).plus(offset2);
	}
	
	public void setOffset(Duration offset){ this.offset2 = offset; }
	public void resetOffset(){ offset2 = new Duration(0); }
	public Duration getOffset(){ return offset2; }
}
