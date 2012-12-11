package gmb.model.tip.tipticket.single;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import javax.persistence.Entity;


@Entity
public class WeeklyLottoSTT extends SingleTT  implements WeeklyLottoTT
{
	public WeeklyLottoSTT()
	{
		super();
		drawType = 0;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	
	public CDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getWeeklyLottoSTTPrice(); }
}