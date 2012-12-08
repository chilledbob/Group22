package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import javax.persistence.Entity;

@Entity
public class TotoSTT extends SingleTT 
{
//	@Deprecated
//	protected TotoSTT(){}

	public TotoSTT()
	{
		super();
		drawType = 2;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public int addTip(SingleTip tip){ return super.addTip(tip, TotoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getTotoSTTPrice(); }
}