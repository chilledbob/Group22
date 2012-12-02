package gmb.model.tip;


public class DailyLottoTip extends SingleTip 
{
	protected int[] tip;

	@Deprecated
	protected DailyLottoTip(){}

	public DailyLottoTip(DailyLottoSTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super((SingleTT)tipTicket, groupTip);

		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip!";
		this.tip = tip;
	}
	
	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip.setTip(int[] tip)!";
		
		if(!draw.isTimeLeftUntilEvaluation()) return false;
		
		this.tip = tip; 
		
		return true;
	}
	
	public int[] getTip(){ return tip; }
}
