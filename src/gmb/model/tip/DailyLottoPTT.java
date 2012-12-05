package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.user.Customer;

public class DailyLottoPTT extends PermaTT 
{
	@Deprecated
	protected DailyLottoPTT(){}

	public DailyLottoPTT(Customer owner, PTTDuration duration)
	{
		super(owner, duration);
		drawType = 1;
	}

	public int addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public BigDecimal getPrice()
	{ 
		switch(durationType)
		{
		case 1: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_HalfYear(); 
		case 2: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_Year(); 
		default : return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoPTTPrice_Month(); 
		}
	}
}
