package gmb.model.financial.transaction;

import gmb.model.Lottery;
import gmb.model.member.Customer;

import gmb.model.CDecimal;

import javax.persistence.Embeddable;;

@Embeddable
public class ExternalTransaction extends Transaction 
{
	@Deprecated
	protected ExternalTransaction(){}
	
	public ExternalTransaction(Customer affectedCustomer, CDecimal amount)
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
