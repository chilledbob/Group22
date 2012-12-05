package gmb.model.financial;

import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;

@Embeddable
public class InternalTransaction extends Transaction 
{
	@Deprecated
	protected InternalTransaction(){}
	
	public InternalTransaction(Customer affectedCustomer, BigDecimal amount, Date date)
	{
		super( affectedCustomer,  amount,  date);
	}
}
