package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.user.Customer;

public class DailyLottoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected DailyLottoSTT(){}

	public DailyLottoSTT(Customer owner)
	{
		super(owner);
		drawType = 1;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public void setTip(SingleTip tip){ super.setTip(tip, DailyLottoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoSTTPrice(); }
}
