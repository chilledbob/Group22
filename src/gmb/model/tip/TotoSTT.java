package gmb.model.tip;

import java.math.BigDecimal;

import gmb.model.Lottery;
import javax.persistence.Entity;

import gmb.model.user.Customer;

@Entity
public class TotoSTT extends SingleTT 
{
	@Deprecated
	protected TotoSTT(){}

	public TotoSTT(Customer owner)
	{
		super(owner);
		drawType = 2;
	}

	public void addToOwner(){ owner.addTipTicket(this); }
	
	public void setTip(SingleTip tip){ super.setTip(tip, TotoTip.class); }
	
	public BigDecimal getPrice(){ return Lottery.getInstance().getFinancialManagement().getTipTicketPrices().getTotoSTTPrice(); }
}