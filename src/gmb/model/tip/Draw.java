package gmb.model.tip;

import gmb.model.Lottery;
import gmb.model.financial.Winnings;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.ElementCollection;

import org.joda.time.DateTime;
import org.joda.time.Duration;

@Entity
public abstract class Draw 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int drawId;
	
	protected boolean evaluated = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date planedEvaluationDate;	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date actualEvaluationDate = null;	
	
	protected BigDecimal prizePotential;	
	@ElementCollection
	protected List<Winnings> winnings;
	
	@OneToMany
	protected List<SingleTip> singleTips;
	@OneToMany(mappedBy="draw")
	protected List<GroupTip> groupTips;
	
	@Deprecated
	protected Draw(){}
	
	public Draw(DateTime planedEvaluationDate)
	{
		this.planedEvaluationDate = planedEvaluationDate.toDate();
		winnings =  new LinkedList<Winnings>();
		
		singleTips = new LinkedList<SingleTip>();
		groupTips = new LinkedList<GroupTip>();
		
		prizePotential = new BigDecimal(0);
	}

	public boolean evaluate()
	{
		actualEvaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
		evaluated = true;
		
		//accumulate the amount of spent money:
		for(SingleTip tip : singleTips)
			prizePotential = prizePotential.add(tip.getTipTicket().getPaidPurchasePrice());
		
		for(GroupTip groupTip : groupTips)
			for(SingleTip tip :  groupTip.getTips())
			prizePotential = prizePotential.add(tip.getTipTicket().getPaidPurchasePrice());
		
		return true;
	}
	
	/**
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 */
	public abstract int createAndSubmitSingleTip(TipTicket ticket, int[] tipTip);
	
	/**
	 * returns true if there is still time to submit or "unsubmit" tips, otherwise false
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluation()
	{
		Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), new DateTime(planedEvaluationDate));
		return duration.isLongerThan(Lottery.getInstance().getTipManagement().getTipSubmissionTimeLimit());		
	}
	
	/**
	 * submits the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
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
	
	/**
	 * submits the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
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
	
	/**
	 * removes the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
	public boolean removeTip(SingleTip tip)
	{ 
		if(isTimeLeftUntilEvaluation())
			return singleTips.remove(tip); 
		else 
			return false;
	}
	
	/**
	 * removes the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
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

	public List<SingleTip> getSingleTips(){ return singleTips; }
	public List<GroupTip> getGroupTips(){ return groupTips; }
	
	public boolean getEvaluated(){ return evaluated; }
	public List<Winnings> getWinnings(){ return winnings; }
	
	public DateTime getPlanedEvaluationDate(){ return new DateTime(planedEvaluationDate); }
	public DateTime getActualEvaluationDate(){ return new DateTime(actualEvaluationDate); }
	
	public abstract int[] getResult();
}
