package gmb.model.tip;


public class WeeklyLottoTip extends SingleTip 
{
	protected int[] tip;

	@Deprecated
	protected WeeklyLottoTip(){}

	public WeeklyLottoTip(WeeklyLottoSTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super((SingleTT)tipTicket, groupTip);

		assert tip.length == 9 : "Wrong tip length (!=9) given to WeeklyLottoTip!";
		this.tip = tip;
	}

	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 9 : "Wrong tip length (!=9) given to DailyLottoTip.setTip(int[] tip)!";
		
		if(!draw.isTimeLeftUntilEvaluation()) return false;
		
		this.tip = tip; 
		
		return true;
	}
	
	public int[] getTip(){ return tip; }
}
