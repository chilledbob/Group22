package gmb.model.tip.tipticket;

import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.PersiObject;
import gmb.model.PersiObjectSingleTable;

import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToOne;

import gmb.model.Lottery;
import gmb.model.financial.transaction.TicketPurchase;
import gmb.model.member.Customer;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.type.DrawType;
import gmb.model.tip.tipticket.type.GenericTT;

import org.joda.time.DateTime;

/**
 * Abstract super class for all tip ticket types.
 * Tip tickets are required for submission of a tip to 
 * a respective lottery drawing or football-toto evaluation.
 */
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public abstract class TipTicket extends PersiObjectSingleTable implements  GenericTT
{	
	@Temporal(value = TemporalType.DATE)
	protected Date purchaseDate;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="paidPurchasePrice", precision = 10, scale = 2))
	protected CDecimal paidPurchasePrice;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="perTicketPaidPurchasePrice", precision = 10, scale = 2))
	protected CDecimal perTicketPaidPurchasePrice;//the price of the corresponding SingleTT (the same like paidPurchasePrice for SingleTTs of course)
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="remainingValue", precision = 10, scale = 2))
	protected CDecimal remainingValue;//decrement this by perTicketPaidPurchasePrice for each submitted tip (in the case of PermaTTs the treasury has to pay as soon this one goes under 0)
	
	@OneToOne
	protected TicketPurchase ticketPurchaseId;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="USER_ID", referencedColumnName="USER_ID")
	protected Customer owner;
	protected int drawType;
	
	
	public TipTicket(){}

	/**
	 * If the "customer" has enough money a "TicketPurchase" instance will be created, the "TipTicket"<br>
	 * will be added to the "customers" list and "true" will be returned, otherwise "false".<br>
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

			ticketPurchaseId = GmbFactory.new_TicketPurchase(this);
			this.ticketPurchaseId.init();
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
	 * Return Code:<br>
	 * 0 - successful<br>
	 *-1 - the duration of the "PermaTT" has expired<br>
	 * 1 - the "SingleTT" is already associated with another "SingleTip"<br>
	 * 2 - the list of the "PermaTT" already contains the "tip"
	 * @param tip
	 * @return
	 */
	public abstract int addTip(SingleTip tip);
	
	public abstract boolean removeTip(SingleTip tip); 
	public abstract CDecimal getPrice();
	public abstract CDecimal getPricePerTicket();
}
