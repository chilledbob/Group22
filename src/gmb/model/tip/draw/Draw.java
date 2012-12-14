package gmb.model.tip.draw;

import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.tip.draw.container.DrawEvaluationResult;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import gmb.model.CDecimal;
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

import org.joda.time.DateTime;
import org.joda.time.Duration;

@Entity
public abstract class Draw extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int drawId;

	protected boolean evaluated = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date planedEvaluationDate;	

	//temporary variables used for evaluation:
	protected CDecimal prizePotential = new CDecimal(0);//temp
	protected List<SingleTip> allSingleTips = new LinkedList<SingleTip>();//temp

	protected DrawEvaluationResult drawEvaluationResult;

	@OneToMany
	protected List<SingleTip> singleTips = new LinkedList<SingleTip>();
	@OneToMany(mappedBy="draw")
	protected List<GroupTip> groupTips = new LinkedList<GroupTip>();

	@Deprecated
	protected Draw(){}

	public Draw(DateTime planedEvaluationDate)
	{
		this.planedEvaluationDate = planedEvaluationDate.toDate();
	}

	/**
	 * [intended for direct usage by controller]
	 * Evaluates the "Draw" with all implications (creating and sending "Winnings", updating the "Jackpot", updating the "LotteryCredits",...).
	 * @return
	 */
	public boolean evaluate()
	{
		drawEvaluationResult = new DrawEvaluationResult(1);

		evaluated = true;

		//accumulate the amount of spent money and all SingleTips:
		for(SingleTip tip : singleTips)
			prizePotential = prizePotential.add(tip.getTipTicket().getPerTicketPaidPurchasePrice());

		allSingleTips.addAll(singleTips);

		for(GroupTip groupTip : groupTips)
		{
			allSingleTips.addAll(groupTip.getTips());
			
			for(SingleTip tip :  groupTip.getTips())
				prizePotential = prizePotential.add(tip.getTipTicket().getPerTicketPaidPurchasePrice());
		}

		prizePotential = drawEvaluationResult.initReceiptsDistributionResult(prizePotential);
		
		//treasury must pay for PermaTT discount:
		for(SingleTip tip : allSingleTips)
		{
			CDecimal currentValue = tip.getTipTicket().getRemainingValue();
			CDecimal updatedValue = currentValue.subtract(tip.getTipTicket().getPerTicketPaidPurchasePrice());
			tip.getTipTicket().setRemainingValue(updatedValue);

			if(updatedValue.signum() == -1)
				if(currentValue.signum() > -1)
					drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(updatedValue);
				else
					drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(tip.getTipTicket().getPerTicketPaidPurchasePrice().negate());
		}
		
		DB_UPDATE(); 
		
		return true;
	}

	/**
	 * [intended for direct usage by controller]
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 */
	public abstract int createAndSubmitSingleTip(TipTicket ticket, int[] tipTip);

	/**
	 * [intended for direct usage by controller]
	 * Returns true if there is still time to change or 'unsubmit' tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForChanges()
	{
		Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), new DateTime(planedEvaluationDate));
		return duration.isLongerThan(Lottery.getInstance().getTipManagement().getTipSubmissionTimeLimit());		
	}

	/**
	 * [intended for direct usage by controller]
	 * Returns true if there is still time to submit tips, otherwise false.
	 * @return
	 */
	public abstract boolean isTimeLeftUntilEvaluationForSubmission();
	
	/**
	 * submits the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
	public abstract boolean addTip(SingleTip tip);

	/**
	 * submits the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
	public abstract boolean addTip(GroupTip tip);

	/**
	 * removes the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
	public abstract boolean removeTip(SingleTip tip);

	/**
	 * removes the tip if there is enough time left till the planned evaluation and returns true if so, otherwise false
	 * @param tip
	 * @return
	 */
	public abstract boolean removeTip(GroupTip tip);

	//////////////////////////////////////////////////////////check for correct type://
	//===============================================================================//
	protected boolean addTip(SingleTip tip, Class<?> tipType)
	{ 		
		assert tip.getClass() == tipType : "Wrong type given to Draw.addTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		
		if(isTimeLeftUntilEvaluationForSubmission())
		{
			singleTips.add(tip); 
			DB_UPDATE(); 
			
			return true;
		}
		else 
			return false;
	}

	protected boolean addTip(GroupTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.addTip(GroupTip tip)! Expected: " + tipType.getSimpleName() + " !";
		
		if(isTimeLeftUntilEvaluationForSubmission())
		{
			groupTips.add(tip); 
			DB_UPDATE(); 
			
			return true;
		}
		else 
			return false;
	}

	protected boolean removeTip(SingleTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.removeTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";

		if(isTimeLeftUntilEvaluationForSubmission())
		{
			boolean result = singleTips.remove(tip); 
			DB_UPDATE(); 
			
			return result;
		}
		else 
			return false;
	}

	protected boolean removeTip(GroupTip tip, Class<?> tipType)
	{ 	
		assert tip.getClass() == tipType : "Wrong type given to Draw.removeTip(GroupTip tip)! Expected: " + tipType.getSimpleName() + " !";

		if(isTimeLeftUntilEvaluationForSubmission())
		{
			boolean result = groupTips.remove(tip); 
			DB_UPDATE(); 
			
			return result;
		}
		else 
			return false;
	}	 
	//===============================================================================//
	///////////////////////////////////////////////////////////////////////////////////

	public List<SingleTip> getSingleTips(){ return singleTips; }
	public List<GroupTip> getGroupTips(){ return groupTips; }

	public DrawEvaluationResult getDrawEvaluationResult(){ return drawEvaluationResult; }
	public boolean getEvaluated(){ return evaluated; }

	public DateTime getPlanedEvaluationDate(){ return new DateTime(planedEvaluationDate); }
	public DateTime getActualEvaluationDate(){ return new DateTime(drawEvaluationResult.getEvaluationDate()); }

	public abstract int[] getResult();
	
	/**
	 * [intended for direct usage by controller]
	 * Sets the drawn results for this draw type. 
	 * Has to be done before evaluation.
	 * @param result
	 */
//	public abstract void setResult(int[] result);
}
