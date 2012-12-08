package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import javax.persistence.Entity;


@Entity
public class WeeklyLottoSTT extends SingleTT  implements WeeklyLottoTT
{
//	@Deprecated
//	protected WeeklyLottoSTT(){}

	public WeeklyLottoSTT()
	{
		super();
		drawType = 0;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoSTTPrice(); }
}