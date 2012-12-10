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

@Entity
public abstract class SingleTip extends Tip 
{
	protected int[] tip;
	@ManyToOne
	protected PermaTT permaTT;
	protected TipTicket tipTicket;
	@ManyToOne
	protected GroupTip groupTip = null;

	
	@Deprecated
	protected SingleTip(){}
	
	public SingleTip(GenericTT tipTicket, GroupTip groupTip, int[] tip) 
	{
		super(groupTip.getDraw());
		
		this.tip = tip;
		this.tipTicket = (TipTicket)tipTicket;
		
		this.groupTip = groupTip;
	}

	public SingleTip(GenericTT tipTicket, Draw draw, int[] tip) 
	{
		super(draw);
		
		this.tip = tip;
		this.tipTicket = (TipTicket)tipTicket;
	}
	
	public int withdraw()
	{
		int result = super.withdraw();//draw already evaluated?		
		if(result != 0) return result;
		
		if(!draw.isTimeLeftUntilEvaluation()) return -1;
		
		tipTicket.removeTip(this);
		
		if(groupTip == null)
		{
			draw.removeTip(this);
			tipTicket.removeTip(this);
			
			return 0;
		}
		else
		{
			return groupTip.removeSingleTip(this);
		}
	}
	
	public boolean setTip(int[] tip)
	{ 		
		if(draw.isTimeLeftUntilEvaluation())
		{
			this.tip = tip;
			return true;
		}
		else
		return false;
	}
	
	public int[] getTip(){ return tip; }
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public GroupTip getGroupTip(){ return groupTip; }
	
	public Customer getOwner(){ return tipTicket.getOwner(); }
}
