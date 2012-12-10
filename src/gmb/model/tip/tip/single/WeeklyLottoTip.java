package gmb.model.tip.tip.single;

import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import javax.persistence.Entity;

@Entity
public class WeeklyLottoTip extends SingleTip 
{
	@Deprecated
	protected WeeklyLottoTip(){}

	public WeeklyLottoTip(WeeklyLottoTT tipTicket, WeeklyLottoDraw draw, int[] tip)
	{
		super(tipTicket, draw, tip);

		assert tip.length == 6 : "Wrong tip length (!=6) given to WeeklyLottoTip!";
	}
	
	public WeeklyLottoTip(WeeklyLottoTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super(tipTicket, groupTip, tip);

		assert tip.length == 6 : "Wrong tip length (!=6) given to WeeklyLottoTip!";
	}

	public boolean setTip(int[] tip)
	{ 
		assert tip.length == 6 : "Wrong tip length (!=6) given to DailyLottoTip.setTip(int[] tip)!";
		
		return super.setTip(tip);
	}
}
