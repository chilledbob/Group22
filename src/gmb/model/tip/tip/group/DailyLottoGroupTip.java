package gmb.model.tip.tip.group;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import gmb.model.GmbFactory;
import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.DailyLottoTT;

@Entity
public class DailyLottoGroupTip extends GroupTip 
{	
	
	@Deprecated
	protected DailyLottoGroupTip(){}

	public DailyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
	}
	
	/**
	 * tries to withdraw the "GroupTip" and removes all existing references in the system.
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
	 * @param tips
	 * @return
	 */
	public int createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		if(tickets.size() == 0 || tipTips.size() == 0) return 5;

		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in DailyLottoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof DailyLottoTT : "Wrong TipTicket type given to DailyLottoGroupTip.createAndSubmitSingleTipList()!";
		
		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}
	
	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new DailyLottoTip((DailyLottoTT)ticket, this);
	}
	
	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_DailyLottoTip((DailyLottoTT)ticket, this);
	}
}
