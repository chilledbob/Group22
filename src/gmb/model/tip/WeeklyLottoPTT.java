package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import javax.persistence.Entity;

import gmb.model.user.Customer;

@Entity
public class WeeklyLottoPTT extends PermaTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected WeeklyLottoPTT(){}

	public WeeklyLottoPTT(Customer owner, PTTDuration duration)
	{
		super(owner, duration);
		drawType = 0;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	
	public BigDecimal getPrice()
	{ 
		switch(durationType)
		{
		case 1: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_HalfYear(); 
		case 2: return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_Year(); 
		default : return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoPTTPrice_Month(); 
		}
	}
}
