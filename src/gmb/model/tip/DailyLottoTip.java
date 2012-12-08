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

	public DailyLottoTip(DailyLottoTT tipTicket, DailyLottoDraw draw, int[] tip)
	{
		super(tipTicket, draw, tip);

		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip!";
	}
	
	public DailyLottoTip(DailyLottoTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super(tipTicket, groupTip, tip);

		assert tip.length == 7 : "Wrong tip length (!=10) given to DailyLottoTip!";
	}
	
	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip.setTip(int[] tip)!";
		
		return super.setTip(tip);
	}
}
