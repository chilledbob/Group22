package gmb.model.financial.transaction;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import gmb.model.Lottery;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.tip.tipticket.TipTicket;

/**
 * A transaction type for ticket purchase purposes.
 *
 */
@Entity
public class TicketPurchase extends InternalTransaction
{	
	@ManyToOne
	protected LotteryBankAccount lotteryBankAccount;
	
	@ManyToOne
	protected FinancialManagement financialManagementId;
	
	@OneToOne
	@JoinColumn(name ="tipTicketId")
	protected TipTicket ticket;

	@Deprecated
	protected TicketPurchase(){}

	/**
	 * Initializes an internal transaction.<br>
	 * A reference to the transaction will be added to the FinancialManagement and the affected user.<br>
	 * The credit of the customer will be updated.<br>
	 * The credit and prize amount of the lottery will be updated.<br>
	 * @param transaction
	 */
	public TicketPurchase(TipTicket ticket)
	{
		super(ticket.getOwner(),  ticket.getPrice().negate());
		this.ticket = ticket;
		financialManagementId = Lottery.getInstance().getFinancialManagement();
	}
	
	/**
	 * Calls the init() method of the super class.<br>
	 * Adds a reference to this transaction to the FinancialManagement.
	 */
	public void init()
	{
		super.init();//update user credit		
		Lottery.getInstance().getFinancialManagement().addTransaction(this);
	}
	
	public TipTicket getTipTicket(){ return ticket; }
}
