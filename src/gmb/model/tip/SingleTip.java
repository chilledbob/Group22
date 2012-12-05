package gmb.model.tip;


public class SingleTip extends Tip 
{
	protected int[] tip;
	protected TipTicket tipTicket;
	protected GroupTip groupTip = null;
	
	@Deprecated
	protected SingleTip(){}
	
	public SingleTip(TipTicket tipTicket, GroupTip groupTip, int[] tip) 
	{
		super(groupTip.getDraw());
		
		this.tip = tip;
		this.tipTicket = tipTicket;
		this.groupTip = groupTip;
	}

	public SingleTip(TipTicket tipTicket, Draw draw, int[] tip) 
	{
		super(draw);
		
		this.tip = tip;
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
}
