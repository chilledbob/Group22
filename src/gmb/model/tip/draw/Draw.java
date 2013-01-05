package gmb.model.tip.draw;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.PersiObjectSingleTable;
import gmb.model.ReturnBox;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.EvaluationResult;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import gmb.model.CDecimal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 * Abstract super class for all drawings.
 * Implements logic for creation/submission of a tip 
 * and the final evaluation.
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class Draw extends PersiObjectSingleTable
{
	protected int[] result;
	
	protected boolean evaluated = false;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date planedEvaluationDate;	

	//temporary variables used for evaluation:
	@Transient
	protected CDecimal prizePotential;//temp
	@Transient
	protected List<SingleTip> allSingleTips;//temp

	
	@OneToOne
	@PrimaryKeyJoinColumn
	protected EvaluationResult drawEvaluationResult;

	@OneToMany(mappedBy="draw")
	protected List<SingleTip> singleTips;
	@OneToMany(mappedBy="draw")
	protected List<GroupTip> groupTips;

	protected static final CDecimal dec100 = new CDecimal(100);
	protected static final CDecimal dec2 = new CDecimal(2);
	
	@ManyToOne
	protected TipManagement tipManagementId;
	
	@Deprecated
	protected Draw(){}

	
	public Draw(DateTime planedEvaluationDate)
	{
		prizePotential = new CDecimal(0);
		allSingleTips = new LinkedList<SingleTip>();
		
		singleTips = new LinkedList<SingleTip>();
		groupTips = new LinkedList<GroupTip>();
		
		this.planedEvaluationDate = planedEvaluationDate.toDate();
		
		this.tipManagementId = Lottery.getInstance().getTipManagement();
		
		result = null;
		
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Evaluates the "Draw" with all implications (creating and sending "Winnings", updating the "Jackpot", updating the "LotteryCredits",...).
	 * @return false if this Draw is already evaluated, otherwise true
	 */
	public boolean evaluate(int[] result)
	{	
		if(this.result == null)
		this.result = result; 
		
		prizePotential = new CDecimal(0);
		allSingleTips = new LinkedList<SingleTip>();
		
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

			if(updatedValue.signum() == -1)//always updatedValue.signum() == 0 for SingleTTs.
				if(currentValue.signum() > -1)//just became negative?
					drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(updatedValue);
				else
					drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(tip.getTipTicket().getPerTicketPaidPurchasePrice().negate());
		}
		
		
		DB_UPDATE(); 
		
		return true;
	}

	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * SIMULATES: Creates and submits a SingleTip. <br>
	 * @param ticket The {@link TipTicket} required for the {@link SingleTip} creation.
	 * @param tipTip The int[] storing the tipped results.
	 * @return return code:<br>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <li>-1 - the duration of the "PermaTT" has expired
	 * <li> 1 - the "SingleTT" is already associated with another "SingleTip"
	 * <li> [2 - the list of the "PermaTT" already contains the "tip"]
	 * <li> 3 - a tipped number is smaller than 1 oder greater than 49
	 * <li> 4 - the same number has been tipped multiple times
	 * <li> 5 - the ticket is already associated with this draw
	 * </ul>
	 */
	public int check_createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{	
		SingleTip tip = this.createSingleTipSimple(ticket);

		//first try whether it would work:
		int result1 = tip.setTip(tipTip);
		if(result1 != 0) return result1;	

		if(!this.addTip(tip)) return -2;
		this.removeTip(tip);//clean up

		int result2 = ticket.addTip(tip);
		if(result2 != 0) return result2;

		ticket.removeTip(tip);//clean up
		
		
		return 0;			
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Creates and submits a SingleTip. <br>
	 * @param ticket The {@link TipTicket} required for the {@link SingleTip} creation.
	 * @param tipTip The int[] storing the tipped results.
	 * @return {@link ReturnBox} with:<br>
	 * var1 as {@link Integer}: <br>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <li>-1 - the duration of the "PermaTT" has expired
	 * <li> 1 - the "SingleTT" is already associated with another "SingleTip"
	 * <li> [2 - the list of the "PermaTT" already contains the "tip"]
	 * <li> 3 - a tipped number is smaller than 1 or greater than 49
	 * <li> 4 - the same number has been tipped multiple times
	 * <li> 5 - the ticket is already associated with this draw
	 * </ul>
	 * var2 as {@link SingleTip}:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created SingleTip
	 * <li> var1 != 0 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, SingleTip> createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{	
//		int result = check_createAndSubmitSingleTip(ticket, tipTip);
//		if(result!=0) return new ReturnBox<Integer, SingleTip>(new Integer(result), null);
		
		SingleTip tip = this.createSingleTipPersistent(ticket);
		tip.setTip(tipTip);
		this.addTip(tip);
		ticket.addTip(tip);

		DB_UPDATE();
		
		return new ReturnBox<Integer, SingleTip>(new Integer(0), tip);			
	}

	protected abstract SingleTip createSingleTipSimple(TipTicket ticket);
	protected abstract SingleTip createSingleTipPersistent(TipTicket ticket);
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns true if there is still time to change tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForChanges()
	{
		Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), new DateTime(planedEvaluationDate));
		return duration.isLongerThan(Lottery.getInstance().getTipManagement().getTipSubmissionTimeLimit());		
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns true if there is still time to (un-)submit tips, otherwise false.
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
			GmbPersistenceManager.remove(tip);
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
			GmbPersistenceManager.remove(tip);
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

	public EvaluationResult getDrawEvaluationResult(){ return drawEvaluationResult; }
	public boolean getEvaluated(){ return evaluated; }
	public boolean isEvaluated(){ return evaluated; }
	
	public DateTime getPlanedEvaluationDate(){ return new DateTime(planedEvaluationDate); }
	public DateTime getActualEvaluationDate(){ return new DateTime(drawEvaluationResult.getEvaluationDate()); }

	public int[] getResult(){ return result; }
	
	/**
	 * [intended for direct usage by controller]
	 * Sets the drawn results for this draw type. 
	 * Has to be done before evaluation.
	 * @param result
	 */
//	public abstract void setResult(int[] result);
	

}
