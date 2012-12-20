package gmb.model.tip.tip.group;

import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.container.EvaluationResult;
import gmb.model.tip.draw.container.ExtendedEvaluationResult;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.ReturnBox;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

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

//"draw" is already used by Tip
//	@ManyToOne(fetch=FetchType.LAZY)
//	@JoinColumn(name="draw_id")
//	protected Draw draw;

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
	 * Calculates the overallWinnings and averageWinnings based on the "allWinnings" list 
	 * and sends each contributer averageWinnings.
	 * The error caused by the divide operation is implicitly returned in the re-calculated overall amount for normalization purposes.
	 * @return
	 */
	public CDecimal finalizeWinnings(EvaluationResult drawEvaluationResult)
	{	
		if(allWinnings.size() > 0)
		{
			CDecimal overallAmount = new CDecimal(0);
			
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
	 * [intended for direct usage by controller]
	 * submit "groupTip" to "draw" if all criteria were met
	 * return false if submission failed, otherwise true
	 * @return
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

				DB_UPDATE(); 
				
				return true;
			}

		return false;
	}

	/**
	 * [intended for direct usage by controller]
	 * 'unsubmit' from "draw" if possible
	 * @return
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
	 * [intended for direct usage by controller]
	 * Submit tickets and tips if the amount matches the "minimumStake" criteria, 
	 * increment "currentOverallMinimumStake" by the amount of newly created tips. 
	 * @param tips
	 * @return
	 */
	public int createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{	
		if(tickets.size() == 0 || tipTips.size() == 0) return 5;
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
			
			//now for real:
			for(int i = 0; i < tickets.size(); ++i)
			{
				SingleTip tip = this.createSingleTipPersistent(tickets.get(i));
				tip.setTip(tipTips.get(i));
				tickets.get(i).addTip(tip);
				
				this.tips.add(tip);
			}
			
			currentOverallMinimumStake += tipTips.size();
			
			DB_UPDATE(); 
			
			return 0;
		}
		else
			return 6;
	}
	
	protected abstract SingleTip createSingleTipSimple(TipTicket ticket);
	protected abstract SingleTip createSingleTipPersistent(TipTicket ticket);
	
	/**
	 * removes a single tip if possible, can lead to annulation of the submission
	 * return code:
	 * 0 - successful
	 *-1 - not enough time left until evaluation
	 * 1 - the associated group member would fall under his minimumStake limit
	 * 2 - can not 'unsubmit' the group tip and therefore not remove tip
	 * @param tip
	 * @return
	 */
	public int removeSingleTip(SingleTip tip)
	{
		if(!tips.contains(tip)) return 3;		
		if(submitted && !draw.isTimeLeftUntilEvaluationForChanges()) return -1;

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
	 * [intended for direct usage by controller]
	 * Removes all tips associated with "groupMember" if possible, can lead to annulment of the submission.
	 * @param groupMember
	 * @return
	 */
	public int removeAllTipsOfGroupMember(Customer groupMember)
	{
		if(!draw.isTimeLeftUntilEvaluationForChanges()) return -1;

		int stake = getGroupMemberStake(groupMember);

		if(currentOverallMinimumStake - stake < overallMinimumStake)
		{
			if(!unsubmit())
				return 2;			
		}

		//remove tips from group tip:
		for(SingleTip tip : getAllTipsOfGroupMember(groupMember))
			tips.remove(tip);

		currentOverallMinimumStake -= stake;

		DB_UPDATE(); 
		
		return 0;
	}

	/**
	 * [intended for direct usage by controller]
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
	 * [intended for direct usage by controller]
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
	 * [intended for direct usage by controller]
	 * Tries to delete this "GroupTip" with all implications.
	 */
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
	public LinkedList<Winnings> getAllWinnings(){ return (LinkedList<Winnings>)allWinnings; }

	public int getCurrentOverallMinimumStake(){ return currentOverallMinimumStake; }

	public int getMinimumStake(){ return minimumStake; }
	public int getOverallMinimumStake(){ return overallMinimumStake; }

	public Group getGroup(){ return group; }	
	public boolean isSubmitted(){ return submitted; }
	public LinkedList<SingleTip> getTips(){ return (LinkedList<SingleTip>)tips; }

	public Customer getOwner(){ return group.getGroupAdmin(); }
}
