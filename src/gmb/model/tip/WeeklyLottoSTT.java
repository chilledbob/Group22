package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.user.Customer;

public class WeeklyLottoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected WeeklyLottoSTT(){}

	public WeeklyLottoSTT(Customer owner)
	{
		super(owner);
		drawType = 0;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public void setTip(SingleTip tip){ super.setTip(tip, WeeklyLottoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoSTTPrice(); }
}