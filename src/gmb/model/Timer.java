package gmb.model;

import gmb.model.tip.draw.DailyLottoDraw;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.salespointframework.core.time.DefaultTime;

/**
 * Timer class which offers functionality for accessing the current date time with offsets for testing purposes.
 */
public class Timer extends DefaultTime 
{
	protected Duration offset1 = new Duration(0);
	protected Duration offset2 = new Duration(0);

	protected boolean dailyLottoDrawAutoCreation = true;
	protected boolean dailyLottoDrawAutoEvaluation = true;

	public Timer()
	{
		super();
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * The given reference date will be assumed to be the current date.<br>
	 * All getDateTime() calls will return the current date relative to the reference date.
	 * @param refDate The new date.
	 */
	public void setReferenceDate(DateTime refDate)
	{
		offset1 = new Duration(super.getDateTime(), refDate);
		offset2 = new Duration(0); 
	}

	protected void evaluateUnevaluatedDailyLottoDrawings()
	{
		if(!dailyLottoDrawAutoEvaluation) return;
		
		boolean wasAutoEvaluation = false;
		DateTime lastEvalDate = new DateTime();

		for(DailyLottoDraw draw : Lottery.getInstance().getTipManagement().getDailyLottoDrawings())
			if(draw.getEvaluated() == false)
			{
				if(draw.getPlanedEvaluationDate().isBefore(getDateTime()))
				{			
					wasAutoEvaluation = true;	

					draw.evaluate(null);
					draw.getDrawEvaluationResult().setEvaluationDate(draw.getPlanedEvaluationDate());

					lastEvalDate = draw.getActualEvaluationDate();//same as draw.getPlanedEvaluationDate();
					
					if(dailyLottoDrawAutoCreation)
						break;
				}
			}


		if(wasAutoEvaluation && dailyLottoDrawAutoCreation)
		{												
			DateTime nextEvalDate = new DateTime(lastEvalDate.getYear(), lastEvalDate.getMonthOfYear(), lastEvalDate.getDayOfMonth(), 0, 0, 0).plusDays(1).plusHours(21);//next day, 9pm

			//check if there is already a planned DailyLottoDraw:
			if(Lottery.getInstance().getTipManagement().getDrawEventsOnDate(nextEvalDate).getDailyLottoDrawings().size() == 0)//no?
				GmbFactory.new_DailyLottoDraw(nextEvalDate);//create one

			evaluateUnevaluatedDailyLottoDrawings();
		}
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param minutesCount The count of minutes to be added to the current date time.
	 */
	public void addMinutes(int minutesCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusMinutes(minutesCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param hoursCount The count of hours to be added to the current date time.
	 */
	public void addHours(int hoursCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusHours(hoursCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param daysCount The count of days to be added to the current date time.
	 */
	public void addDays(int daysCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusDays(daysCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param weeksCount The count of weeks to be added to the current date time.
	 */
	public void addWeeks(int weeksCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusWeeks(weeksCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param monthsCount The count of months to be added to the current date time.
	 */
	public void addMonths(int monthsCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusMonths(monthsCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param yearsCount The count of years to be added to the current date time.
	 */
	public void addYears(int yearsCount)
	{ 
		DateTime actualDateTime = super.getDateTime();	
		DateTime fakeDateTime = actualDateTime.plus(offset1).plus(offset2);
		DateTime newFakeDateTime = fakeDateTime.plusYears(yearsCount);

		this.offset2 = this.offset2.plus(new Duration(fakeDateTime, newFakeDateTime)); 
		evaluateUnevaluatedDailyLottoDrawings(); 
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param offset The duration to be added to the current date time.
	 */
	public void addToOffset(Duration offset){ this.offset2 = this.offset2.plus(offset); }

	/**
	 * [Intended for direct usage by controller]<br>
	 * @return The current day time. Might depends on a given reference date and a duration offset.
	 */
	public DateTime getDateTime()
	{
		DateTime actualDateTime = super.getDateTime();	
		return actualDateTime.plus(offset1).plus(offset2);
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Time simulation for testing purposes.
	 * @param offset The new duration offset which will be added to the date time.
	 */
	public void setOffset(Duration offset){ this.offset2 = offset; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Resets the time offset to 0.
	 */
	public void resetOffset(){ offset2 = new Duration(0); }
	public Duration getOffset(){ return offset2; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns whether new DailyLottoDraws will be created automatically.
	 * @return dailyLottoDrawAutoCreation
	 */
	public boolean isDailyLottoDrawAutoCreation(){ return dailyLottoDrawAutoCreation; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Activates the automatic creation of new DailyLottoDraws.
	 */
	public void setDailyLottoDrawAutoCreation(){ dailyLottoDrawAutoCreation = true; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Deactivates the automatic creation of new DailyLottoDraws.
	 */
	public void resetDailyLottoDrawAutoCreation(){ dailyLottoDrawAutoCreation = false; }
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns whether DailyLottoDraws will be evaluated automatically.
	 * @return dailyLottoDrawAutoCreation
	 */
	public boolean isDailyLottoDrawAutoEvaluation(){ return dailyLottoDrawAutoEvaluation; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Activates the automatic evaluation of DailyLottoDraws.
	 */
	public void setDailyLottoDrawAutoEvaluation(){ dailyLottoDrawAutoEvaluation = true; }

	/**
	 * [Intended for direct usage by controller]<br>
	 * Deactivates the automatic evaluation of DailyLottoDraws.
	 */
	public void resetDailyLottoDrawAutoEvaluation(){ dailyLottoDrawAutoEvaluation = false; }
}
