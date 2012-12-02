package gmb.model.financial;

import java.math.BigDecimal;

import org.joda.time.DateTime;

import gmb.model.tip.Tip;
import gmb.model.user.Customer;

public class Winnings extends InternalTransaction
{
	protected Tip tip;

	@Deprecated
	protected Winnings(){}

	public Winnings(Tip tip, Customer affectedCustomer, BigDecimal amount, DateTime date)
	{
		super( affectedCustomer,  amount,  date);
		this.tip = tip;
	}

	public Tip getTip(){ return tip; }
}
