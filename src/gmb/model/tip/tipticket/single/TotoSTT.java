package gmb.model.tip.tipticket.single;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;

import javax.persistence.Entity;

@Entity
public class TotoSTT extends SingleTT 
{
	public TotoSTT()
	{
		super();
		drawType = 2;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, TotoTip.class); }
	
	public CDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getTotoSTTPrice(); }
}