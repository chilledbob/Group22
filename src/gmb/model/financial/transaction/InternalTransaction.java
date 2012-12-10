package gmb.model.financial.transaction;

import gmb.model.member.Customer;

import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public abstract class InternalTransaction extends Transaction 
{
	@Deprecated
	protected InternalTransaction(){}
	
	public InternalTransaction(Customer affectedCustomer, BigDecimal amount)
	{
		super( affectedCustomer,  amount);
	}
}
