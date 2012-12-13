package gmb.model.tip.tipticket;

import gmb.model.CDecimal;
import gmb.model.PersiObject;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;

import gmb.model.Lottery;
import gmb.model.financial.transaction.TicketPurchase;
import gmb.model.member.Customer;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.type.DailyLottoTT;
import gmb.model.tip.tipticket.type.DrawType;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;


import org.joda.time.DateTime;

@Entity
public abstract class TipTicket extends PersiObject implements  WeeklyLottoTT, DailyLottoTT
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int tipTicketId;
	
	@Temporal(value = TemporalType.DATE)
	protected Date purchaseDate;
	protected CDecimal paidPurchasePrice;
	protected CDecimal perTicketPaidPurchasePrice;//the price of the corresponding SingleTT (the same like paidPurchasePrice for SingleTTs of course)
	protected CDecimal remainingValue;//decrement this by perTicketPaidPurchasePrice for each submitted tip (in the case of PermaTTs the treasury has to pay as soon this one goes under 0)
	
	@OneToOne(mappedBy="ticket")
	protected TicketPurchase ticketPurchaseId;
	
	@ManyToOne
	protected Customer owner;
	protected int drawType;
	
	
	public TipTicket(){}

	/**
	 * [intended for direct usage by controller]
	 * If the "customer" has enough money a "TicketPurchase" instance will be created, the "TipTicket"
	 * will be added to the "customers" list and "true" will be returned, otherwise "false".
	 * Also the actually paid price will be saved in "paidPurchasePrice".
	 * @param customer
	 * @return
	 */
	public boolean purchase(Customer customer)
	{	
		if(customer.hasEnoughMoneyToPurchase(this.getPrice()))
		{
			this.owner = customer;
			purchaseDate = Lottery.getInstance().getTimer().getDateTime().toDate();
			paidPurchasePrice = getPrice();			

			paidPurchasePrice = this.getPrice();//we need this information since the price can change over time
			remainingValue = paidPurchasePrice;

			perTicketPaidPurchasePrice = this.getPricePerTicket();

			new TicketPurchase(this);
			this.addToOwner();

			DB_UPDATE(); 

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
	
	public void setRemainingValue(CDecimal remainingValue){ this.remainingValue = remainingValue; DB_UPDATE(); }
	
	public CDecimal getRemainingValue(){ return remainingValue; }
	public CDecimal getPerTicketPaidPurchasePrice(){ return perTicketPaidPurchasePrice; }
	
	public Customer getOwner(){ return owner; }
	public DateTime getPurchaseDate(){ return new DateTime(purchaseDate); }	
	public CDecimal getPaidPurchasePrice(){ return paidPurchasePrice; }	
	
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
	public abstract CDecimal getPrice();
	public abstract CDecimal getPricePerTicket();
}
