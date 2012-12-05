package gmb.model.financial;

import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Embeddable;;

@Embeddable
public class ExternalTransaction extends Transaction 
{
	@Deprecated
	protected ExternalTransaction(){}
	
	public ExternalTransaction(Customer affectedCustomer, BigDecimal amount, Date date)
	{
		super( affectedCustomer,  amount,  date);
	}
}
