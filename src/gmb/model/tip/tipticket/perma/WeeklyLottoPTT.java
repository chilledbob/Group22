package gmb.model.tip.tipticket.perma;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import javax.persistence.Entity;


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
}
