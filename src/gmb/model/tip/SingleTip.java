package gmb.model.tip;


public class SingleTip extends Tip 
{
	protected TipTicket tipTicket;
	protected GroupTip groupTip = null;
	
	@Deprecated
	protected SingleTip(){}
	
	public SingleTip(TipTicket tipTicket, GroupTip groupTip) 
	{
		super(groupTip.getDraw());
		
		this.tipTicket = tipTicket;
		this.groupTip = groupTip;
	}

	public SingleTip(TipTicket tipTicket, Draw draw) 
	{
		super(draw);
		
		this.tipTicket = tipTicket;
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
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public GroupTip getGroupTip(){ return groupTip; }
}
