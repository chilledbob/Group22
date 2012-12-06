package gmb.model.tip;

import gmb.model.Lottery;

import javax.persistence.Entity;

import org.joda.time.DateTime;

@Entity
public class DailyLottoDraw extends Draw 
{
	protected int[] result;

	@Deprecated
	protected DailyLottoDraw(){}

	protected DailyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}

	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate and init prizePotential 

		prizePotential = prizePotential.add(Lottery.getInstance().getFinancialManagement().getDailyLottoPrize());
		prizePotential = Lottery.getInstance().getFinancialManagement().distributeDrawReceipts(prizePotential);

		//////////////////////////CALCULATE THE WINNINGS HERE THEN REMOVE THE FOLLOWING CODE
		for(SingleTip tip : singleTips)
			tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");

		for(GroupTip groupTip : groupTips)
			for(SingleTip tip :  groupTip.getTips())
				tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");
		
		Lottery.getInstance().getFinancialManagement().setDailyLottoPrize(prizePotential);//everything for the lottery!
		//////////////////////////

		return false;
	}

	public void setResult(int[] result)
	{ 
		assert result.length == 10 : "Wrong result length (!=10) given to DailyLottoDraw.setResult(int[] result)!";
		this.result = result; 
	}

	public boolean addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, DailyLottoGroupTip.class); }

	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, DailyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, DailyLottoGroupTip.class); }

	public int[] getResult(){ return result; }

	/**
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 */
	public int createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof WeeklyLottoSTT : "Wrong TipTicket type given to DailyLottoDraw.createAndSubmitSingleTip()! Expected DailyLottoSTT!";

	if(this.isTimeLeftUntilEvaluation())
	{
		DailyLottoTip tip = new DailyLottoTip((DailyLottoSTT)ticket, this, tipTip);
		int result = ticket.addTip(tip);

		if(result == 0)
		{
			ticket.getOwner().addTipTicket((DailyLottoSTT)ticket);
			singleTips.add(tip);

			return 0;
		}
		else 
			return result;
	}
	else
		return -1;
	}
}
