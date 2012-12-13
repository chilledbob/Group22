package gmb.model.tip.tip.group;

import java.util.LinkedList;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

@Entity
public class TotoGroupTip extends GroupTip 
{
	@Deprecated
	protected TotoGroupTip(){}

	public TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
		group.addGroupTip(this);
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Tries to delete this "GroupTip" with all implications.
	 * Returns 0 if successful.
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
	
	public int createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		if(tickets.size() == 0 || tipTips.size() == 0) return 5;

		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in TotoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof WeeklyLottoTT : "Wrong TipTicket type given to TotoGroupTip.createAndSubmitSingleTipList()!";
		
		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}
	
	protected SingleTip createSingleTip(TipTicket ticket)
	{
		return new TotoTip((TotoSTT)ticket, this);
	}
}
