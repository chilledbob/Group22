package gmb.model.tip.tip.group;

import java.util.ArrayList;
import java.util.LinkedList;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

import gmb.model.GmbFactory;
import gmb.model.ReturnBox;
import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

/** 
 * A GroupTip for the weekly 6/49 lottery.
 */
@Entity
public class WeeklyLottoGroupTip extends GroupTip 
{

	
	@Deprecated
	protected WeeklyLottoGroupTip(){}

	public WeeklyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
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
	 * <li> 3 - a tipped number is smaller than 1 oder greater than 49
	 * <li> 4 - the same number has been tipped multiple times
	 * <li> 5 - invalid sizes of the committed lists
	 * <li> 6 - not enough tickets submitted to reach the minimum stake per contributer
	 * </ul>
	 * var2 as  LinkedList<<SingleTip>>:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created list of SingleTips
	 * <li> var1 != 1 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, LinkedList<SingleTip>> createAndSubmitSingleTipList(LinkedList<TipTicket> tickets, LinkedList<int[]> tipTips)
	{
		assert tickets.size() == tipTips.size() : "Count of tickets does not fit count of tipTips in WeeklyLottoGroupTip.createAndSubmitSingleTipList()!";
		assert  tickets.getFirst() instanceof WeeklyLottoTT : "Wrong TipTicket type given to WeeklyLottoGroupTip.createAndSubmitSingleTipList()!";

		return super.createAndSubmitSingleTipList(tickets,  tipTips);
	}

	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new WeeklyLottoTip((WeeklyLottoTT)ticket, this);
	}

	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_WeeklyLottoTip((WeeklyLottoTT)ticket, this);
	}
}
