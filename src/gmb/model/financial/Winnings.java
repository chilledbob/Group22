package gmb.model.financial;

import gmb.model.tip.Tip;

public class Winnings 
{
	protected Tip tip;
	protected InternalTransaction transaction;

	@Deprecated
	protected Winnings(){}

	public Winnings(Tip tip, InternalTransaction transaction)
	{
		this.tip = tip;
		this.transaction = transaction;
	}

	public Tip getTip(){ return tip; }
	public InternalTransaction getTransaction(){ return transaction; }
}
