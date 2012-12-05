package gmb.model.financial;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.OneToOne;

import gmb.model.tip.Tip;

@Embeddable
public class Winnings 
{
	@OneToOne
	protected Tip tip;
	@Embedded
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
