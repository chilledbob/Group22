package gmb.model.tip.tip.group;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Entity;

import gmb.model.GmbFactory;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;

/** 
 * A GroupTip for the weekly football-toto evaluation.
 */
@Entity
public class TotoGroupTip extends GroupTip 
{
	
	@Deprecated
	protected TotoGroupTip(){}

	public TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
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
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.removeGroupTip(this))
			return 0;
		else
			return 4;
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
	 * <li> 3 - a tipped number is smaller than 0 oder greater than 2
	 * <li> 5 - invalid sizes of the committed lists
	 * <li> 6 - not enough tickets submitted to reach the minimum stake per contributer
	 * </ul>
	 * var2 as  LinkedList<SingleTip>:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created list of SingleTips
	 * <li> var1 != 1 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, LinkedList<SingleTip>> createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in TotoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof TotoSTT : "Wrong TipTicket type given to TotoGroupTip.createAndSubmitSingleTipList()!";
		
		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}
	
	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new TotoTip((TotoSTT)ticket, this);
	}
	
	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_TotoTip((TotoSTT)ticket, this);
	}
}
