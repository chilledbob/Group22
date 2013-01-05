package gmb.model.tip.tipticket.perma;

import java.util.ArrayList;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import javax.persistence.Entity;

/**
 * The perma tip ticket type for the weekly 6/49 lottery.
 */
@Entity
public class WeeklyLottoPTT extends PermaTT implements WeeklyLottoTT
{
	@Deprecated
	protected WeeklyLottoPTT(){}

	public WeeklyLottoPTT(PTTDuration duration)
	{
		super(duration);
		drawType = 0;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	
	public CDecimal getPrice()
	{ 
		switch(durationType)
		{
		case 1: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_HalfYear(); 
		case 2: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_Year(); 
		default : return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_Month(); 
		}
	}
	
	public CDecimal getPricePerTicket(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoSTTPrice(); }
	
	/**
	 * @return
	 * <ul>
	 * <li> 3 - a tipped number is smaller than 1 oder greater than 49
	 * <li> 4 - the same number has been tipped multiple times
	 * </ul>
	 */
	public int validateTip(int[] tip)
	{
		assert tip.length == 6 : "Wrong tip length (!=6) given to WeeklyLottoPTT!";
		
		return (new WeeklyLottoTip((WeeklyLottoTT)null, new WeeklyLottoDraw(Lottery.getInstance().getTimer().getDateTime().plusDays(5)))).validateTip(tip);//use temporary objects for validation
	}
}
