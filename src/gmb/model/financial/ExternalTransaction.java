package gmb.model.financial;

import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;

public class ExternalTransaction extends Transaction 
{
	public ExternalTransaction(Customer affectedCustomer, BigDecimal amount, Date date)
	{
		super( affectedCustomer,  amount,  date);
	}
}
