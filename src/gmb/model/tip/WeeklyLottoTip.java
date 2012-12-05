package gmb.model.tip;

import javax.persistence.Entity;

@Entity
public class WeeklyLottoTip extends SingleTip 
{
	@Deprecated
	protected WeeklyLottoTip(){}

	public WeeklyLottoTip(WeeklyLottoSTT tipTicket, WeeklyLottoDraw draw, int[] tip)
	{
		super((SingleTT)tipTicket, draw, tip);

		assert tip.length == 7 : "Wrong tip length (!=7) given to WeeklyLottoTip!";
	}
	
	public WeeklyLottoTip(WeeklyLottoSTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super((SingleTT)tipTicket, groupTip, tip);

		assert tip.length == 7 : "Wrong tip length (!=7) given to WeeklyLottoTip!";
	}

	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 7 : "Wrong tip length (!=7) given to DailyLottoTip.setTip(int[] tip)!";
		
		return super.setTip(tip);
	}
}
