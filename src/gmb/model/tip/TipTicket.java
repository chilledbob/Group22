package gmb.model.tip;

import gmb.model.Lottery;
import gmb.model.user.Customer;


import org.joda.time.DateTime;


public abstract class TipTicket 
{
	protected DateTime purchaseDate;
	protected Customer owner;
	
	@Deprecated
	protected TipTicket(){}
	
	public TipTicket(Customer owner)
	{
		this.owner = owner;
		
		purchaseDate = Lottery.getInstance().getTimer().getDateTime();
	}

	public Customer getOwner(){ return owner; }
	public DateTime getPurchaseDate(){ return purchaseDate; }	
	
	public abstract boolean removeTip(SingleTip tip); 
}
