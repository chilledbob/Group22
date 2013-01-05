package gmb.model.tip.tip.single;

import java.util.ArrayList;

import gmb.model.member.Customer;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.DailyLottoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

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
		this.tipTicket = (DailyLottoSTT) tipTicket;
	}
	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * Checks whether "tip" would be a valid result to be tipped.
	 * @return return code:<br>
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
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
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public Customer getOwner(){return tipTicket.getOwner(); }
}
