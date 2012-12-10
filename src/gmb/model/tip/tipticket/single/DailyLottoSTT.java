package gmb.model.tip.tipticket.single;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.type.DailyLottoTT;

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
