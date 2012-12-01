package gmb.model.tip;
import java.util.LinkedList;

import org.joda.time.DateTime;


public class TotoEvaluation extends Draw 
{
	private LinkedList<FootballGameResult> results;
	
	@Deprecated
	protected TotoEvaluation(){}

	protected TotoEvaluation(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}
	
	@Override
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate
		
		return false;
	}
	
	public void addResult(FootballGameResult result){ results.add(result); }
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, TotoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, TotoGroupTip.class); }
	
	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, TotoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, TotoGroupTip.class); }
	
	public LinkedList<FootballGameResult> getResults(){ return results; }
}
