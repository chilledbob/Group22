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
			if(draw.getEvaluated() == false)
			{
				Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), draw.getPlanedEvaluationDate());

				if(duration.isShorterThan(new Duration(0)))
					draw.evaluate(null);
			}
	}

	public void addMinutes(long minuteCount){ this.offset2 = this.offset2.plus(new Duration(minuteCount * 1000*60)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addHours(long hoursCount){ this.offset2 = this.offset2.plus(new Duration(hoursCount * 1000*60*60)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addDays(long daysCount){ this.offset2 = this.offset2.plus(new Duration(daysCount * 1000*60*60*24)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addWeeks(long weeksCount){ this.offset2 = this.offset2.plus(new Duration(weeksCount * 1000*60*60*24*7)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addMonths(long monthCount){ this.offset2 = this.offset2.plus(new Duration(monthCount * 1000*60*60*24*30)); evaluateUnevaluatedDailyLottoDrawings(); }
	public void addYears(long yearCount){ this.offset2 = this.offset2.plus(new Duration(yearCount * 1000*60*60*24*365)); evaluateUnevaluatedDailyLottoDrawings(); }
	
	//this didn't work, produced an int overflow: (LONG EVERYWHERE AND JAVA CASTS BACK TO INT! WHY JAVA, WHY?!! *$%^*'?°§%C#####!!! >.<)
//	public void addMonths(long monthCount){ this.offset2 = this.offset2.plus(new Duration((long)1000*(long)60*(long)60*(long)24*(long)30 * monthCount)); evaluateUnevaluatedDailyLottoDrawings(); }
	
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
