package gmb.model.tip.tip.group;

import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.container.EvaluationResult;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.ReturnBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Abstract super class for all group tip types.
 * A group tip stores a list of single tips.
 * The winnings for the single tips will be distributed to
 * all contributers of the group tip.
 */
@Entity
public abstract class GroupTip extends Tip 
{
	@ManyToOne
	protected Group group;
	protected boolean submitted;

	protected int minimumStake;
	protected int overallMinimumStake;

	protected int currentOverallMinimumStake;

	@OneToOne
	@JoinColumn(name="TIP_PERSISTENCEID")
	protected Winnings averageWinnings;
	@OneToMany
	@JoinColumn(name="TIP_PERSISTENCEID")
	protected List<Winnings> allWinnings;
	
	@OneToMany(mappedBy="groupTip")
	protected List<SingleTip> tips;

	@Deprecated
	protected GroupTip(){}

	public GroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw);
		
		allWinnings = new LinkedList<Winnings>();
		tips = new LinkedList<SingleTip>();
		currentOverallMinimumStake = 0;
		submitted = false;
		
		this.group = group;
		this.minimumStake = minimumStake;
		this.overallMinimumStake = overallMinimumStake;
	}

	/**
	 * Calculates the overallWinnings and averageWinnings based on the "allWinnings" list <br>
	 * and sends each contributer averageWinnings. <br>
	 * @return The error caused by the divide operation is implicitly returned in the re-calculated overall amount for normalization purposes.
	 */
	public CDecimal finalizeWinnings(EvaluationResult drawEvaluationResult)
	{	
		if(allWinnings.size() > 0)
		{
			CDecimal overallAmount = new CDecimal(0);
			
			int highestPrizeCategory = 10;
			for(Winnings winnings : allWinnings)
			{
				overallAmount = overallAmount.add(winnings.getAmount());
				highestPrizeCategory = Math.min(highestPrizeCategory, winnings.getPrizeCategory());
			}

			CDecimal averageAmount = overallAmount.divide(new CDecimal(allWinnings.size()));

			//send average winnings to all contributers:
			for(SingleTip tip : tips)
			{
				Winnings newWinnings = GmbFactory.new_Winnings(tip, averageAmount, -1);
				newWinnings.init();

				drawEvaluationResult.addWinnings(newWinnings);
			}

			overallWinnings = GmbFactory.new_Winnings(this, averageAmount.multiply(new CDecimal(allWinnings.size())), highestPrizeCategory);
			averageWinnings = GmbFactory.new_Winnings(this, averageAmount, -1);
			
			DB_UPDATE(); 
			
			//return overall amount with error for normalization:
			return overallWinnings.getAmount();
		}
					
		return new CDecimal(0);
	}

	/**
	 * [Intended for direct usage by controller][check method]<br>
	 * SIMULATES: Submits this GroupTip to "draw" if all criteria were met.
	 * @return false if submission would fail, otherwise true
	 */
	public boolean check_submit()
	{	
		if(draw.isTimeLeftUntilEvaluationForSubmission())
			if(currentOverallMinimumStake >= overallMinimumStake)	
				return true;

		return false;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Submits this GroupTip to "draw" if all criteria were met.
	 * @return false if de-submission failed, otherwise true
	 */
	public boolean submit()
	{	
		if(submitted) return true;
		if(draw.getEvaluated()) return false;

		if(draw.isTimeLeftUntilEvaluationForSubmission())
			if(currentOverallMinimumStake >= overallMinimumStake)
			{
				draw.addTip(this);
				submitted = true;
				submissionDate = Lottery.getInstance().getTimer().getDateTime().toDate();
				
				DB_UPDATE(); 
				
				return true;
			}

		return false;
	}

	/**
	 * [Intended for direct usage by controller][check method]<br>
	 * SIMULATES: 'Unsubmit's this GroupTip from "draw" if possible.
	 * @return false if de-submission would fail, otherwise true.
	 */
	public boolean check_unsubmit()
	{
		if(draw.isTimeLeftUntilEvaluationForSubmission())
			return true;
		else
			return false;
	}

	
	/**
	 * [Intended for direct usage by controller]<br>
	 * 'Unsubmit's this GroupTip from "draw" if possible.
	 * @return false if submission failed, otherwise true
	 */
	public boolean unsubmit()
	{
		if(!submitted) return true;
		if(draw.getEvaluated()) return false;

		if(draw.isTimeLeftUntilEvaluationForSubmission())
		{
			draw.removeTip(this);
			submitted = false;

			DB_UPDATE(); 
			
			return true;
		}
		else
			return false;
	}

	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * SIMULATES: Submits tickets and tips if the amount matches the "minimumStake" criteria,
	 * increment "currentOverallMinimumStake" by the amount of newly created tips. 
	 * @param tips
	 * @return return code:<br>
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left for submission until evaluation
	 * <li> 3 - invalid  tip(s)
	 * <li> 4 - invalid  tip(s)
	 * <li> 5 - invalid sizes of the committed lists
	 * <li> 6 - not enough tickets submitted to reach the minimum stake per contributer
	 * </ul>
	 */
	public int check_createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<ArrayList<Integer>> tipTips)
	{	
		if(tickets.size() == 0 || tipTips.size() == 0 || (tickets.size() != tipTips.size())) return 5;
		if(!(this.draw.isTimeLeftUntilEvaluationForSubmission())) return -2;
	
		int stake = getGroupMemberStake((tickets.getFirst()).getOwner());

		if(tickets.size() >= minimumStake || stake != 0)
		{				
			ArrayList<SingleTip> cleanupList = new ArrayList<SingleTip>();
			
			//first try whether it would work:
			for(int i = 0; i < tickets.size(); ++i)
			{
				SingleTip tip = this.createSingleTipSimple(tickets.get(i));
				
				int result1 = tip.setTip(tipTips.get(i));
				if(result1 != 0) 
				{
					//reset changes of the try before error return:
					for(int j = 0; j < i; ++j)
						tickets.get(j).removeTip(cleanupList.get(j));
					
					return result1;	
				}					

				int result2 = tickets.get(i).addTip(tip);
				if(result2 != 0) 
				{
					//reset changes of the try before error return:
					for(int j = 0; j < i; ++j)
						tickets.get(j).removeTip(cleanupList.get(j));
					
					return result2;		
				}
				
				//prepare for cleanup:
				cleanupList.add(tip);		
			}
				
			//reset changes of the try:
			for(int i = 0; i < tickets.size(); ++i)
				tickets.get(i).removeTip(cleanupList.get(i));
			
			return 0;
		}
		else
			return 6;
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Submits tickets and tips if the amount matches the "minimumStake" criteria,
	 * increment "currentOverallMinimumStake" by the amount of newly created tips. 
	 * @param tips
	 * @return {@link ReturnBox} with:<br>
	 * var1 as {@link Integer}: <br>
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left for submission until evaluation
	 * <li> 3 - invalid  tip(s)
	 * <li> 4 - invalid  tip(s)
	 * <li> 5 - invalid sizes of the committed lists
	 * <li> 6 - not enough tickets submitted to reach the minimum stake per contributer
	 * </ul>
	 * var2 as  LinkedList<SingleTip>:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created list of SingleTips
	 * <li> var1 != 1 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, LinkedList<SingleTip>> createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<ArrayList<Integer>> tipTips)
	{	
		int result = check_createAndSubmitSingleTipList(tickets, tipTips);
		if(result!=0) return new ReturnBox<Integer, LinkedList<SingleTip>>(new Integer(result), null);

		LinkedList<SingleTip> newTips = new LinkedList<SingleTip>();

		for(int i = 0; i < tickets.size(); ++i)
		{
			SingleTip tip = this.createSingleTipPersistent(tickets.get(i));
			tip.setTip(tipTips.get(i));
			tickets.get(i).addTip(tip);

			newTips.add(tip);
			this.tips.add(tip);
		}

		currentOverallMinimumStake += tipTips.size();

		DB_UPDATE(); 

		return new ReturnBox<Integer, LinkedList<SingleTip>>(new Integer(0), newTips);
	}
	
	protected abstract SingleTip createSingleTipSimple(TipTicket ticket);
	protected abstract SingleTip createSingleTipPersistent(TipTicket ticket);
	
	/**
	 * [check-method]<br>
	 * SIMULATES: Removes a single tip if possible.<br>
	 * This can lead to annulment of the submission of this group tip.<br>
	 * This method shouldn't be called from the controller directly, but by using
	 * the SingleTip.withdraw() method of the SingleTip class.
	 * return code:
	 * @param tip The SingleTip to be removed.
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until the planned evaluation of the draw
	 * <li> 1 - the associated group member would fall under his minimumStake limit
	 * <li> 2 - can not 'unsubmit' the group tip and therefore not remove tip
	 * <ul>
	 */
	public int check_removeSingleTip(SingleTip tip)
	{
		if(!tips.contains(tip)) return 3;		
		if(submitted && !draw.isTimeLeftUntilEvaluationForSubmission()) return -1;

		if(getGroupMemberStake(tip.getTipTicket().getOwner()) > minimumStake || minimumStake == 1)
		{	
			if(currentOverallMinimumStake <= overallMinimumStake)
			{
				if(!check_unsubmit())
					return 2;
			}
			
			return 0;
		}
		else
			return 1;
	}
	
	/**
	 * Removes a single tip if possible.<br>
	 * This can lead to annulment of the submission of this group tip.<br>
	 * This method shouldn't be called from the controller directly, but by using
	 * the SingleTip.withdraw() method of the SingleTip class.
	 * return code:
	 * @param tip The SingleTip to be removed.
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until the planned evaluation of the draw
	 * <li> 1 - the associated group member would fall under his minimumStake limit
	 * <li> 2 - can not 'unsubmit' the group tip and therefore not remove tip
	 * <ul>
	 */
	public int removeSingleTip(SingleTip tip)
	{
		if(!tips.contains(tip)) return 3;		
		if(submitted && !draw.isTimeLeftUntilEvaluationForSubmission()) return -1;

		if(getGroupMemberStake(tip.getTipTicket().getOwner()) > minimumStake || minimumStake == 1)
		{	
			if(currentOverallMinimumStake <= overallMinimumStake)
			{
				if(!unsubmit())
					return 2;
			}

			tip.getTipTicket().removeTip(tip);
			tips.remove(tip);
			--currentOverallMinimumStake;

			DB_UPDATE(); 
			
			return 0;
		}
		else
			return 1;
	}

	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * SIMULATES: Removes all tips associated with "groupMember" if possible, can lead to annulment of the submission.
	 * @param groupMember
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until submission
	 * <li>-2 - failed to 'unsubmit'
	 * <ul>
	 */
	public int check_removeAllTipsOfGroupMember(Customer groupMember)
	{
		if(submitted && !draw.isTimeLeftUntilEvaluationForSubmission()) return -1;

		int stake = getGroupMemberStake(groupMember);

		if(currentOverallMinimumStake - stake < overallMinimumStake)
		{
			if(!check_unsubmit())
				return -2;			
		}
		
		return 0;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Removes all tips associated with "groupMember" if possible, can lead to annulment of the submission.
	 * @param groupMember
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until submission
	 * <li>-2 - failed to 'unsubmit'
	 * <ul>
	 */
	public int removeAllTipsOfGroupMember(Customer groupMember)
	{
		if(submitted && !draw.isTimeLeftUntilEvaluationForSubmission()) return -1;

		int stake = getGroupMemberStake(groupMember);

		if(currentOverallMinimumStake - stake < overallMinimumStake)
		{
			if(!unsubmit())
				return -2;			
		}

		//remove tips from group tip:
		for(SingleTip tip : getAllTipsOfGroupMember(groupMember))
			tips.remove(tip);

		currentOverallMinimumStake -= stake;

		DB_UPDATE(); 
		
		return 0;
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns a list of all "SingleTips" the "groupMember" contributed to the "GroupTip".
	 * @param groupMember
	 * @return
	 */
	public LinkedList<SingleTip> getAllTipsOfGroupMember(Customer groupMember)
	{
		LinkedList<SingleTip> memberTips = new LinkedList<SingleTip>();

		for(SingleTip tip : tips)
		{
			if(tip.getTipTicket().getOwner() == groupMember)
				memberTips.add(tip);
		}
		
		return memberTips;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns the count of all "SingleTips" the "groupMember" contributed to the "GroupTip".
	 * @param groupMember
	 * @return
	 */
	public int getGroupMemberStake(Customer groupMember)
	{
		int stake = 0;
		for(SingleTip tip : tips)
		{
			if(tip.getTipTicket().getOwner().equals(groupMember))
				++stake;
		}

		return stake;
	}

	/**
	 * [Intended for direct usage by controller]<br>
	 * SIMULATES: Tries to delete this "GroupTip" with all implications.
	 */
	public int check_withdraw()
	{
		if(unsubmit())
		{		
			return 0;
		}
		else
			return 2;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Tries to delete this "GroupTip" with all implications.
	 * @return return code:<br>
	 * <ul>
	 * <li> 0 - successful
	 * <li> 2 - this GroupTip could not be 'unsubmitted'
	 * <li> 4 - this GroupTip could not be removed from the associated GroupTip list in "group"
	 * <ul>
	 */
	public int withdraw()
	{
		if(unsubmit())
		{
			for(SingleTip tip : tips)
				tip.getTipTicket().removeTip(tip);

			tips.clear();//not really necessary

			DB_UPDATE(); 
			
			return 0;
		}
		else
			return 2;
	}

//	public void setAverageWinnings(Winnings averageWinnings){ this.averageWinnings = averageWinnings; DB_UPDATE(); }
	public void addWinnings(Winnings winnings){ this.allWinnings.add(winnings); DB_UPDATE(); }

	public Winnings getAverageWinnings(){ return averageWinnings; }	
	public LinkedList<Winnings> getAllWinnings(){ return (LinkedList<Winnings>)allWinnings; }

	public int getCurrentOverallMinimumStake(){ return currentOverallMinimumStake; }

	public int getMinimumStake(){ return minimumStake; }
	public int getOverallMinimumStake(){ return overallMinimumStake; }

	public Group getGroup(){ return group; }	
	public boolean isSubmitted(){ return submitted; }
	public LinkedList<SingleTip> getTips(){ return (LinkedList<SingleTip>)tips; }

	public Customer getOwner(){ return group.getGroupAdmin(); }
}
