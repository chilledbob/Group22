package gmb.model.tip;

import gmb.model.Lottery;
import gmb.model.financial.Winnings;

import java.util.LinkedList;

import org.joda.time.DateTime;
import org.joda.time.Duration;


public abstract class Draw 
{
	protected boolean evaluated = false;
	protected DateTime planedEvaluationDate;	
	protected DateTime actualEvaluationDate = null;	
	
	protected LinkedList<Winnings> winnings;
	
	protected LinkedList<SingleTip> singleTips;
	protected LinkedList<GroupTip> groupTips;
	
	@Deprecated
	protected Draw(){}
	
	protected Draw(DateTime planedEvaluationDate)
	{
		this.planedEvaluationDate = planedEvaluationDate;
		winnings =  new LinkedList<Winnings>();
		
		singleTips = new LinkedList<SingleTip>();
		groupTips = new LinkedList<GroupTip>();
	}

	public boolean evaluate()
	{
		actualEvaluationDate = Lottery.getInstance().getTimer().getDateTime();
		evaluated = true;

		return true;
	}
	
	public boolean isTimeLeftUntilEvaluation()
	{
		Duration duration = new Duration(planedEvaluationDate, Lottery.getInstance().getTimer().getDateTime());
		return duration.isLongerThan(Lottery.getInstance().getTipManagement().getTipSubmissionTimeLimit());		
	}
	
	public boolean addTip(SingleTip tip)
	{ 
		if(isTimeLeftUntilEvaluation())
		{
			singleTips.add(tip); 
			return true;
		}
		else 
			return false;
	}
	
	public boolean addTip(GroupTip tip)
	{ 
		if(isTimeLeftUntilEvaluation())
		{
			groupTips.add(tip); 
			return true;
		}
		else 
			return false;
	}
	
	public boolean removeTip(SingleTip tip)
	{ 
		if(isTimeLeftUntilEvaluation())
			return singleTips.remove(tip); 
		else 
			return false;
	}
	
	public boolean removeTip(GroupTip tip)
	{ 
		if(isTimeLeftUntilEvaluation())
			return groupTips.remove(tip); 
		else 
			return false;
	}
	
	//////////////////////////////////////////////////////////check for correct type://
	//===============================================================================//
	protected boolean addTip(SingleTip tip, Class<?> tipType)
	{ 		
		assert tip.getClass() == tipType : "Wrong type given to Draw.addTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		return addTip(tip);
	}
	
	protected boolean addTip(GroupTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.addTip(GroupTip tip)! Expected: " + tipType.getSimpleName() + " !";
		return addTip(tip);
	}

	protected boolean removeTip(SingleTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.removeTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		return removeTip(tip); 
	}
	
	protected boolean removeTip(GroupTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.removeTip(GroupTip tip)! Expected: " + tipType.getSimpleName() + " !";
		return removeTip(tip);
	}	 
	//===============================================================================//
	///////////////////////////////////////////////////////////////////////////////////

	public LinkedList<SingleTip> getWeeklyLottoTips(){ return singleTips; }
	public LinkedList<GroupTip> getWeeklyLottoGroupTips(){ return groupTips; }
	
	public boolean getEvaluated(){ return evaluated; }
	public LinkedList<Winnings> getWinnings(){ return winnings; }
	
	public DateTime getPlanedEvaluationDate(){ return planedEvaluationDate; }
	public DateTime getActualEvaluationDate(){ return actualEvaluationDate; }
}
