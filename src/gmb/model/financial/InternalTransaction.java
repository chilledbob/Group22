package gmb.model.financial;

import gmb.model.user.Customer;

import java.math.BigDecimal;

public abstract class InternalTransaction extends Transaction 
{
	@Deprecated
	protected InternalTransaction(){}
	
	public InternalTransaction(Customer affectedCustomer, BigDecimal amount)
	{
		super( affectedCustomer,  amount);
	}
}
