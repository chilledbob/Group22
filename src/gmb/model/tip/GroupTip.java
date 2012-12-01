package gmb.model.tip;

import gmb.model.user.Customer;
import gmb.model.user.Group;

import java.util.LinkedList;

public abstract class GroupTip extends Tip 
{
	protected Group group;
	protected boolean submitted = false;
	
	protected int minimumStake;
	protected int overallMinimumStake;
	
	protected int currentOverallMinimumStake = 0;

	protected LinkedList<SingleTip> tips;
	
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
	 * submit "groupTip" to "draw" if all criterias were met
	 * @return
	 */
	public boolean submit()
	{	
		if(submitted) return true;
		if(draw.getEvaluated()) return false;
		
		if(draw.isTimeLeftUntilEvaluation())
		if(overallMinimumStake >= currentOverallMinimumStake)
		{
			draw.addTip(this);
			submitted = true;
			
			return true;
		}
		
		return false;
	}
	
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
			
			tips.remove(tip);
			--currentOverallMinimumStake;
			
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
			
			return 0;
		}
		else
			return 2;
	}
	
	public int getCurrentOverallMinimumStake(){ return currentOverallMinimumStake; }
	
	public int getMinimumStake(){ return minimumStake; }
	public int getOverallMinimumStake(){ return overallMinimumStake; }
	
	public Group getGroup(){ return group; }	
	public boolean getSubmitted(){ return submitted; }
}
