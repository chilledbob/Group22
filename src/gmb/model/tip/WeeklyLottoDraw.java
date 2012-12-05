package gmb.model.tip;

import org.joda.time.DateTime;

public class WeeklyLottoDraw extends Draw 
{
	protected int[] result = null;
	
	@Deprecated
	protected WeeklyLottoDraw(){}
	
	protected WeeklyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}
	
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate
		
		return false;
	}
	
	public void setResult(int[] result)
	{ 
		assert result.length == 7 : "Wrong result length (!=7) given to WeeklyLottoDraw.setResult(int[] result)!";
		this.result = result; 
	}
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, WeeklyLottoGroupTip.class); }
	
	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, WeeklyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, WeeklyLottoGroupTip.class); }
	
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
		assert ticket instanceof WeeklyLottoSTT : "Wrong TipTicket type given to WeeklyLottoDraw.createAndSubmitSingleTip()! Expected WeeklyLottoSTT!";
		
		if(this.isTimeLeftUntilEvaluation())
		{
			WeeklyLottoTip tip = new WeeklyLottoTip((WeeklyLottoSTT)ticket, this, tipTip);
			int result = ticket.addTip(tip);
			
			if(result == 0)
			{
				ticket.getOwner().addTipTicket((WeeklyLottoSTT)ticket);
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
