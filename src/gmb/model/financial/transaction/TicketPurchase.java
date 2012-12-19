package gmb.model.financial.transaction;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import gmb.model.Lottery;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.tip.tipticket.TipTicket;


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
	 * initializes an internal transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * the credit of the customer will be updated
	 * the credit and prize amount of the lottery will be updated
	 * @param transaction
	 */
	public TicketPurchase(TipTicket ticket)
	{
		super(ticket.getOwner(),  ticket.getPrice().negate());
		this.ticket = ticket;
	}
	
	public void init()
	{
		super.init();//update user credit		
		Lottery.getInstance().getFinancialManagement().addTransaction(this);
	}
	
	public TipTicket getTipTicket(){ return ticket; }
}
