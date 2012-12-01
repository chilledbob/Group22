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
	
	@Override
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate
		
		return false;
	}
	
	public void setResult(int[] result)
	{ 
		assert result.length == 9 : "Wrong result length (!=9) given to WeeklyLottoDraw.setResult(int[] result)!";
		this.result = result; 
	}
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, WeeklyLottoGroupTip.class); }
	
	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, WeeklyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, WeeklyLottoGroupTip.class); }
	
	public int[] getResult(){ return result; }
}
