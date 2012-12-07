package gmb.model.tip;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;

import gmb.model.Lottery;
import gmb.model.financial.TicketPurchase;
import gmb.model.user.Customer;

import java.util.Date;
import org.joda.time.DateTime;

@Entity
public abstract class TipTicket 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int tipTicketId;
	
	@Temporal(value = TemporalType.DATE)
	protected Date purchaseDate;
	
	@OneToOne(mappedBy="ticket")
	protected TicketPurchase ticketPurchaseId;
	
	@ManyToOne
	protected Customer owner;
	protected int drawType;
	
	@Deprecated
	protected TipTicket(){}
	
	public TipTicket(Customer owner)
	{
		this.owner = owner;
		
		purchaseDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}

	/**
	 * If the "customer" has enough money a "TicketPurchase" instance will be created, the "TipTicket"
	 * will be added to the "customers" list and "true" will be returned, otherwise "false".
	 * @param customer
	 * @return
	 */
	public boolean purchase(Customer customer)
	{
		if(customer.hasEnoughMoneyToPurchase(this.getPrice()))
		{
			new TicketPurchase(this);
			addToOwner();
			
			return true;
		}
		else
			return false;
	}
	
	protected abstract void addToOwner();
	
	public DrawType getDrawType()
	{
		switch(drawType)
		{
		case 1: return DrawType.DailyLotto;
		case 2: return DrawType.Toto;
		default: return DrawType.WeeklyLotto;
		}
	}
	
	public int getDrawTypeAsInt(){ return drawType; }
	
	public Customer getOwner(){ return owner; }
	public DateTime getPurchaseDate(){ return new DateTime(purchaseDate); }	
	
	/**
	 * Return Code:
	 * 0 - successful
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * 2 - the list of the "PermaTT" already contains the "tip"
	 * @param tip
	 * @return
	 */
	public abstract int addTip(SingleTip tip);
	
	public abstract boolean removeTip(SingleTip tip); 
	public abstract BigDecimal getPrice();
}
