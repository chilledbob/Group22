package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.tip.TipTicket;

public class TicketPurchase extends InternalTransaction
{
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
		
		super.init();//update user credit		
		Lottery.getInstance().getFinancialManagement().updateCredit(this);
	}
	
	public TipTicket getTipTicket(){ return ticket; }
}
