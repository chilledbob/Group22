package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.user.Customer;

import java.math.BigDecimal;

public class ExternalTransaction extends Transaction 
{
	@Deprecated
	protected ExternalTransaction(){}
	
	public ExternalTransaction(Customer affectedCustomer, BigDecimal amount)
	{
		super( affectedCustomer,  amount);
	}
	
	/**
	 * Initializes an "ExternalTransaction".
	 * A reference to the "ExternalTransaction" will be added to the "FinancialManagement"'s list and the "affectedCustomer"'s list.
	 * The "credit" of the "affectedCustomer"'s "LotterybankAccount" will be updated
	 * @param transaction
	 */
	public void init()
	{
		super.init();//update user credit	
		Lottery.getInstance().getFinancialManagement().addTransaction(this);	
	}
}
