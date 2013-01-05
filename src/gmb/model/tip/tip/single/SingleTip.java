package gmb.model.tip.tip.single;

import java.util.ArrayList;
import java.util.List;

import gmb.model.Lottery;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.PermaTT;
import gmb.model.tip.tipticket.type.GenericTT;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;


/**
 * Abstract super class for all tip classes which
 * representing single tips (the common tip type).
 */
@Entity
public abstract class SingleTip extends Tip 
{
	protected int[] tip;
	@ManyToOne
	protected PermaTT permaTT;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TIPTICKET_PERSISTENCEID")
	protected TipTicket tipTicket;
	@ManyToOne
	protected GroupTip groupTip;
	
	
	@Deprecated
	protected SingleTip(){}
	
	public SingleTip(GenericTT tipTicket, GroupTip groupTip) 
	{
		super(groupTip.getDraw());
		
		this.tipTicket = (TipTicket)tipTicket;
		
		this.groupTip = groupTip;
		
		this.submissionDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}

	public SingleTip(GenericTT tipTicket, Draw draw) 
	{
		super(draw);
		
		this.tipTicket = (TipTicket)tipTicket;
		
		groupTip = null;
		
		this.submissionDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * SIMULATES: Tries to withdraw the tip with all implications which also depends
	 * on whether this SingleTip is associated with a GroupTip.
	 * This can lead to annulment of the submission of an associated GroupTip.<br>
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until the planned evaluation of the draw
	 * <li> 1 - the associated group member would fall under his minimumStake limit (only tested if minimumStake > 1).
	 * Use GroupTip.removeAllTipsOfGroupMember(Customer groupMember) to withdraw the tips in this case.
	 * <li> 2 - can not 'unsubmit' the group tip from draw and therefore not remove tip
	 * <ul>
	 */
	public int check_withdraw()
	{	
		if(groupTip == null)
		{
			if(!draw.isTimeLeftUntilEvaluationForSubmission()) 
				return -1;
			
			return 0;
		}
		else
		{
			return groupTip.check_removeSingleTip(this);
		}
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Tries to withdraw the tip with all implications which also depends
	 * on whether this SingleTip is associated with a GroupTip.
	 * This can lead to annulment of the submission of an associated GroupTip.<br>
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until the planned evaluation of the draw
	 * <li> 1 - the associated group member would fall under his minimumStake limit (only tested if minimumStake > 1).
	 * Use GroupTip.removeAllTipsOfGroupMember(Customer groupMember) to withdraw the tips in this case.
	 * <li> 2 - can not 'unsubmit' the group tip from draw and therefore not remove tip
	 * <ul>
	 */
	public int withdraw()
	{	
		if(groupTip == null)
		{
			if(!draw.isTimeLeftUntilEvaluationForSubmission()) 
				return -1;
			
			draw.removeTip(this);
//			tipTicket.removeTip(this);
			
			return 0;
		}
		else
		{
			return groupTip.removeSingleTip(this);
		}
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Changes the tipped result if it is valid.
	 * @param tip The new tipped result.
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <li>   - check {@link WeeklyLottoTip.validateTip(int[] tip)} and {@link DailyLottoTip.validateTip(int[] tip)} for further failure codes
	 * <ul>
	 */
	public int setTip(int[] tip)
	{ 		
		int result = this.validateTip(tip);
		if(result != 0) return result;
		
		this.tip = tip;
		DB_UPDATE();
		
		return 0;
	}
	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * Checks whether "tip" would be a valid result to be tipped.
	 * @param tip2
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <ul>
	 */
	public int validateTip(int[] tip2)
	{
		if(draw.isTimeLeftUntilEvaluationForChanges())
			return 0;
		else
			return -2;
	}
	
	public int[] getTip(){ return tip; }
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public GroupTip getGroupTip(){ return groupTip; }
	
//	public Customer getOwner(){ return tipTicket.getOwner(); }
}
