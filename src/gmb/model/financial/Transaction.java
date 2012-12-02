package gmb.model.financial;
import gmb.model.user.Customer;

import java.math.BigDecimal;

import org.joda.time.DateTime;


public abstract class Transaction 
{
	protected Customer affectedCustomer; 
	protected BigDecimal amount;
	protected DateTime date;
	
	@Deprecated
	protected Transaction(){}
	
	public Transaction(Customer affectedCustomer, BigDecimal amount, DateTime date)
	{
		this.affectedCustomer = affectedCustomer;
		this.amount = amount;
		this.date = date;
	}
	
	public Customer getAffectedCustomer(){ return affectedCustomer; }
	public BigDecimal getAmount(){ return amount; }
	public DateTime getDate(){ return date; }
	
	public void init()
	{
		affectedCustomer.getBankAccount().updateCredit(this);
	}
}
