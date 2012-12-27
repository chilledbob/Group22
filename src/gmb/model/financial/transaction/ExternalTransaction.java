package gmb.model.financial.transaction;

import gmb.model.Lottery;
import gmb.model.member.Customer;

import gmb.model.CDecimal;

import javax.persistence.Entity;

/**
 * A transaction type for moving money to or from an
 * imaginary real bank account from or to a lottery bank account.
 *
 */
@Entity
public class ExternalTransaction extends Transaction 
{
	@Deprecated
	protected ExternalTransaction(){}
	
	public ExternalTransaction(Customer affectedCustomer, CDecimal amount)
	{
		super( affectedCustomer,  amount);
	}
	
	/**
	 * Initializes an "ExternalTransaction".<br>
	 * A reference to the "ExternalTransaction" will be added to the "FinancialManagement"'s list and the "affectedCustomer"'s list.<br>
	 * The "credit" of the "affectedCustomer"'s "LotterybankAccount" will be updated.
	 * @param transaction
	 */
	public void init()
	{
		super.init();//update user credit	
		Lottery.getInstance().getFinancialManagement().addTransaction(this);	
	}
}
