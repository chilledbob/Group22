package gmb.model.tip;


public class DailyLottoTip extends SingleTip 
{
	@Deprecated
	protected DailyLottoTip(){}

	public DailyLottoTip(DailyLottoSTT tipTicket, DailyLottoDraw draw, int[] tip)
	{
		super((SingleTT)tipTicket, draw, tip);

		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip!";
	}
	
	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip.setTip(int[] tip)!";
		
		return super.setTip(tip);
	}
}
