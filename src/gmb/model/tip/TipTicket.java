package gmb.model.tip;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import gmb.model.Lottery;
import gmb.model.user.Customer;


import org.joda.time.DateTime;

@Entity
public abstract class TipTicket 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int tipTicketId;
	
	//TemporalType nur Date,Time oder Timestamp
	//@Temporal(value = TemporalType.DATE)
	protected DateTime purchaseDate;
	
	@ManyToOne
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
