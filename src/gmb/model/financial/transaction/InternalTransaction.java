package gmb.model.financial.transaction;

import gmb.model.member.Customer;

import gmb.model.CDecimal;

import javax.persistence.Entity;

/**
 * Abstract super class for all system intern transaction types.
 */
@Entity
public abstract class InternalTransaction extends Transaction 
{
	@Deprecated
	protected InternalTransaction(){}
	
	public InternalTransaction(Customer affectedCustomer, CDecimal amount)
	{
		super( affectedCustomer,  amount);
	}
}
