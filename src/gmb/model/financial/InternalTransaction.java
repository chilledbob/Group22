package gmb.model.financial;

import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;

public class InternalTransaction extends Transaction 
{
	public InternalTransaction(Customer affectedCustomer, BigDecimal amount, Date date)
	{
		super( affectedCustomer,  amount,  date);
	}
}
