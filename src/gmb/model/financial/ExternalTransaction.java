package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.user.Customer;

import java.math.BigDecimal;

import org.joda.time.DateTime;

public class ExternalTransaction extends Transaction 
{
	@Deprecated
	protected ExternalTransaction(){}
	
	public ExternalTransaction(Customer affectedCustomer, BigDecimal amount, DateTime date)
	{
		super( affectedCustomer,  amount,  date);
	}
	
	/**
	 * initializes an external transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * the credit of the customer will be updated
	 * @param transaction
	 */
	public void init()
	{
		super.init();//update user credit	
		Lottery.getInstance().getFinancialManagement().addTransaction(this);	
	}
}
