package gmb.model.tip.tip.single;

import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class DailyLottoTip extends SingleTip 
{
	@ManyToOne
	protected GroupTip groupTip;
	
	@Deprecated
	protected DailyLottoTip(){}

	public DailyLottoTip(DailyLottoTT tipTicket, DailyLottoDraw draw)
	{
		super(tipTicket, draw);
	}
	
	public DailyLottoTip(DailyLottoTT tipTicket, GroupTip groupTip)
	{
		super(tipTicket, groupTip);
	}
	
	public int validateTip(int[] tip)
	{ 
		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip.setTip(int[] tip)!";
		
		return super.validateTip(tip);
	}
}
