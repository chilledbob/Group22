package gmb.model.financial;

import java.math.BigDecimal;

import gmb.model.Lottery;
import gmb.model.tip.SingleTip;

public class Winnings extends InternalTransaction
{
	protected SingleTip tip;

	@Deprecated
	protected Winnings(){}

	/**
	 * initializes an internal transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * the credit of the customer will be updated
	 * the credit and prize amount of the lottery will be updated
	 * @param transaction
	 */
	public Winnings(SingleTip tip, BigDecimal amount)
	{
		super(tip.getTipTicket().getOwner(),  amount);
		this.tip = tip;
		
		super.init();//update user credit		
		Lottery.getInstance().getFinancialManagement().updateCredit(this);
		
//		tip.getTipTicket().getOwner().addNotification(new Notification(""));
	}
	
	public SingleTip getTip(){ return tip; }
}
