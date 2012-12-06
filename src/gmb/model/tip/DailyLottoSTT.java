package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import javax.persistence.Entity;

@Entity
public class DailyLottoSTT extends SingleTT implements DailyLottoTT 
{	
//	@Deprecated
//	protected DailyLottoSTT(){}

	public DailyLottoSTT()
	{
		super();
		drawType = 1;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getDailyLottoSTTPrice(); }
}
