package gmb.model.tip.tip.single;

import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/** 
 * A SingleTip for the daily numbers based lottery.
 */
@Entity
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
	
	public DailyLottoTip(DailyLottoTT tipTicket, DailyLottoGroupTip groupTip)
	{
		super(tipTicket, groupTip);
	}
	
	/**
	 * @return
	 * <ul>
	 * <li> 3 - a tipped number is smaller than 0 oder greater than 9
	 * </ul>
	 */
	public int validateTip(int[] tip)
	{ 
		assert tip.length == 10 : "Wrong tip length (!=10) given to DailyLottoTip.setTip(int[] tip)!";
		
		for(int i = 0; i < 10; ++i)
		{
			if(tip[i] < 0 || tip[i] > 9)
				return 3;
		}
		
		return super.validateTip(tip);
	}
}
