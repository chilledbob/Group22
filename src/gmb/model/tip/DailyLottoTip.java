package gmb.model.tip;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class DailyLottoTip extends SingleTip 
{
	@ManyToOne
	protected GroupTip groupTip;
	
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
