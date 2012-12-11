package gmb.model.tip.tipticket.perma;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Entity;

@Entity
public class DailyLottoPTT extends PermaTT implements DailyLottoTT
{
	@Deprecated
	protected DailyLottoPTT(){}

	public DailyLottoPTT(PTTDuration duration)
	{
		super(duration);
		drawType = 1;
	}

	public int addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public CDecimal getPrice()
	{ 
		switch(durationType)
		{
		case 1: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_HalfYear(); 
		case 2: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_Year(); 
		default : return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_Month(); 
		}
	}
	
	public CDecimal getPricePerTicket(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoSTTPrice(); }
}
