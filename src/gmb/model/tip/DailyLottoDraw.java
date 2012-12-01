package gmb.model.tip;

import org.joda.time.DateTime;

public class DailyLottoDraw extends Draw 
{
	protected int[] result;

	@Deprecated
	protected DailyLottoDraw(){}

	protected DailyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}

	@Override
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate
		
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
}
