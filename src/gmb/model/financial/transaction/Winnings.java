package gmb.model.financial.transaction;

import javax.persistence.Embeddable;
import javax.persistence.OneToOne;

import gmb.model.CDecimal;

import gmb.model.Lottery;
import gmb.model.tip.tip.Tip;
import gmb.model.tip.tip.single.SingleTip;

@Embeddable
public class Winnings extends InternalTransaction
{
	@OneToOne
	protected Tip tip;

	@Deprecated
	protected Winnings(){}
	
	protected int prizeCategory = 11;

	/**
	 * initializes an internal transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * the credit of the customer will be updated
	 * the credit and prize amount of the lottery will be updated
	 * @param transaction
	 */
	public Winnings(Tip tip, CDecimal amount, int prizeCategory)
	{
		super(tip.getOwner(),  amount);
		this.tip = tip;
		this.prizeCategory = prizeCategory;
	}

	public void init()
	{
		if(tip instanceof SingleTip)
		{
			super.init();//update user credit		
			Lottery.getInstance().getFinancialManagement().addTransaction(this);

			//		tip.getTipTicket().getOwner().addNotification("");
		}
		else
		{

		}
	}
	
	public Tip getTip(){ return tip; }
	public int getPrizeCategory(){ return prizeCategory; }
}
