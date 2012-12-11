package gmb.model.tip.tip.group;

import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.container.DrawEvaluationResult;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.single.SingleTip;

import gmb.model.CDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

@Entity
public abstract class GroupTip extends Tip 
{
	@ManyToOne
	protected Group group;
	protected boolean submitted = false;

	protected int minimumStake;
	protected int overallMinimumStake;

	protected int currentOverallMinimumStake = 0;

	protected Winnings averageWinnings;
	protected List<Winnings> allWinnings = new LinkedList<Winnings>();

	@OneToMany(mappedBy="groupTip")
	protected List<SingleTip> tips = new LinkedList<SingleTip>();

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="draw_id")
	private Draw draw;

	@Deprecated
	protected GroupTip(){}

	public GroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw);
		this.group = group;
		this.minimumStake = minimumStake;
		this.overallMinimumStake = overallMinimumStake;
	}

	/**
	 * Calculates the overallWinnings and averageWinnings based on the "allWinnings" list 
	 * and sends each contributer averageWinnings.
	 * The error caused by the divide operation is implicitly returned in the re-calculated overall amount for normalization purposes.
	 * @return
	 */
	public CDecimal finalizeWinnings(DrawEvaluationResult drawEvaluationResult)
	{	
		CDecimal overallAmount = new CDecimal(0);

		if(allWinnings.size() > 0)
		{
			int highestPrizeCategory = 8;
			for(Winnings winnings : allWinnings)
			{
				overallAmount = overallAmount.add(winnings.getAmount());
				highestPrizeCategory = Math.min(highestPrizeCategory, winnings.getPrizeCategory());
			}

			CDecimal averageAmount = overallAmount.divide(new CDecimal(allWinnings.size()));

			//send average winnings to all contributers:
			for(SingleTip tip : tips)
			{
				Winnings newWinnings = new Winnings(tip, averageAmount, -1);
				newWinnings.init();

				drawEvaluationResult.addWinnings(newWinnings);
			}

			overallWinnings = new Winnings(this, averageAmount.multiply(new CDecimal(allWinnings.size())), highestPrizeCategory);
			averageWinnings = new Winnings(this, averageAmount, -1);
		}
		
		DB_UPDATE(); 
		
		//return overall amount with error for normalization:
		return overallWinnings.getAmount();
	}

	/**
	 * submit "groupTip" to "draw" if all criteria were met
	 * return false if submission failed, otherwise true
	 * @return
	 */
//	public boolean submit()
//	{	
//		if(submitted) return true;
//		if(draw.getEvaluated()) return false;
//
//		if(draw.isTimeLeftUntilEvaluation())
//			if(overallMinimumStake >= currentOverallMinimumStake)
//			{
//				draw.addTip(this);
//				submitted = true;
//
//				DB_UPDATE(); 
//				
//				return true;
//			}
//
//		return false;
//	}

	/**
	 * 'unsubmit' from "draw" if possible
	 * @return
	 */
	public boolean unsubmit()
	{
		if(!submitted) return true;
		if(draw.getEvaluated()) return false;

		if(draw.isTimeLeftUntilEvaluation())
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
	 * add tips if the amount matches the "minimumStake" criteria, 
	 * increment "currentOverallMinimumStake" by the amount of tips 
	 * @param tips
	 * @return
	 */
	public boolean addTips(LinkedList<SingleTip> tips)
	{
		if(tips.size() == 0) return false;
		if(!draw.isTimeLeftUntilEvaluation()) return false;

		int stake = getGroupMemberStake(tips.getFirst().getTipTicket().getOwner());

		if(tips.size() >= minimumStake || stake != 0)
		{
			currentOverallMinimumStake += tips.size();
			this.tips.addAll(tips);

			DB_UPDATE(); 
			
			return true;
		}
		else
			return false;
	}

	/**
	 * removes a single tip if possible, can lead to annulation of the submission
	 * @param tip
	 * @return
	 */
	public int removeSingleTip(SingleTip tip)
	{
		if(!tips.contains(tip)) return 3;		
		if(!draw.isTimeLeftUntilEvaluation()) return -1;

		if(getGroupMemberStake(tip.getTipTicket().getOwner()) > minimumStake)
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
	 * removes all tips associated with "groupMember" if possible, can lead to annulation of the submission
	 * @param groupMember
	 * @return
	 */
	public int removeAllTipsOfGroupMember(Customer groupMember)
	{
		if(!draw.isTimeLeftUntilEvaluation()) return -1;

		int stake = getGroupMemberStake(groupMember);

		if(currentOverallMinimumStake - stake < overallMinimumStake)
		{
			if(!unsubmit())
				return 2;			
		}

		//create list of tips which belong to the group member:
		LinkedList<SingleTip> memberTips = new LinkedList<SingleTip>();

		for(SingleTip tip : tips)
		{
			if(tip.getTipTicket().getOwner().equals(groupMember))
				memberTips.add(tip);
		}

		//remove tips from group tip:
		for(SingleTip tip : memberTips)
			tips.remove(tip);

		currentOverallMinimumStake -= stake;

		DB_UPDATE(); 
		
		return 0;
	}

	/**
	 * returns the count of tips associated with the "groupMember" 
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

	public int withdraw()
	{
		int result = super.withdraw();//draw already evaluated?	
		if(result != 0) return result;

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

	public void setAverageWinnings(Winnings averageWinnings){ this.averageWinnings = averageWinnings; DB_UPDATE(); }
	public void addWinnings(Winnings winnings){ this.allWinnings.add(winnings); DB_UPDATE(); }

	public Winnings getAverageWinnings(){ return averageWinnings; }	
	public List<Winnings> getAllWinnings(){ return allWinnings; }

	public int getCurrentOverallMinimumStake(){ return currentOverallMinimumStake; }

	public int getMinimumStake(){ return minimumStake; }
	public int getOverallMinimumStake(){ return overallMinimumStake; }

	public Group getGroup(){ return group; }	
	public boolean getSubmitted(){ return submitted; }
	public List<SingleTip> getTips(){ return tips; }

	public Customer getOwner(){ return group.getGroupAdmin(); }
}
