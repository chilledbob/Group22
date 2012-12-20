package gmb.model.tip.tip.single;

import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.perma.PermaTT;
import gmb.model.tip.tipticket.type.GenericTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * Abstract super class for all tip classes which<br>
 * representing single tips (the common tip type).
 */
@Entity
public abstract class SingleTip extends Tip 
{
	protected int[] tip;
	@ManyToOne
	protected PermaTT permaTT;
	@OneToOne
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
	}

	public SingleTip(GenericTT tipTicket, Draw draw) 
	{
		super(draw);
		
		groupTip = null;
		this.tipTicket = (TipTicket)tipTicket;
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Tries to withdraw the tip with all implications which also depend
	 * on whether the "SingleTip" is associated with a "GroupTip".
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-1 - not enough time left until the planned evaluation of the draw
	 * <li> 2 - removing tip from tip ticket failed
	 * <ul>
	 */
	public int withdraw()
	{
		int result = super.withdraw();//draw already evaluated?		
		if(result != 0) return result;
		
		if(!draw.isTimeLeftUntilEvaluationForSubmission()) return -1;
		
		tipTicket.removeTip(this);
		
		if(groupTip == null)
		{
			draw.removeTip(this);
//			if(!tipTicket.removeTip(this)) return 2;
			
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
	 * [Intended for direct usage by controller]<br>
	 * Checks whether "tip" would be a valid result to be tipped.
	 * @param tip
	 * @return
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <ul>
	 */
	public int validateTip(int[] tip)
	{
		if(draw.isTimeLeftUntilEvaluationForChanges())
			return 0;
		else
			return -2;
	}
	
	public int[] getTip(){ return tip; }
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public GroupTip getGroupTip(){ return groupTip; }
	
	public Customer getOwner(){ return tipTicket.getOwner(); }
}
