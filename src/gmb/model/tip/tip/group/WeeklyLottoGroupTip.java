package gmb.model.tip.tip.group;

import java.util.LinkedList;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

@Entity
public class WeeklyLottoGroupTip extends GroupTip 
{
	@Deprecated
	protected WeeklyLottoGroupTip(){}

	public WeeklyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
		group.addGroupTip(this);
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Tries to delete this "GroupTip" with all implications.
	 * returns 0 if successful.
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
	 * [intended for direct usage by controller]
	 * Submits tickets and tips if the amount matches the "minimumStake" criteria, 
	 * increment "currentOverallMinimumStake" by the amount of newly created tips. 
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 * 3 - a tipped number is smaller than 1 oder greater than 49
	 * 4 - the same number has been tipped multiple times
	 * 5 - tickets or tipTips were empty
	 * 6 - not enough tickets
	 */
	public int createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		if(tickets.size() == 0 || tipTips.size() == 0) return 5;

		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in WeeklyLottoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof WeeklyLottoTT : "Wrong TipTicket type given to WeeklyLottoGroupTip.createAndSubmitSingleTipList()!";
		
		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}
	
	protected SingleTip createSingleTip(TipTicket ticket)
	{
		return new WeeklyLottoTip((WeeklyLottoTT)ticket, this);
	}
}
