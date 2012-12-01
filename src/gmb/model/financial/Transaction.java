package gmb.model.financial;
import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;



public class Transaction 
{
	protected Customer affectedCustomer; 
	protected BigDecimal amount;
	protected Date date;
	
	@Deprecated
	protected Transaction(){}
	
	public Transaction(Customer affectedCustomer, BigDecimal amount, Date date)
	{
		this.affectedCustomer = affectedCustomer;
		this.amount = amount;
		this.date = date;
	}
	
	public Customer getAffectedCustomer(){ return affectedCustomer; }
	public BigDecimal getAmount(){ return amount; }
	public Date getDate(){ return date; }
}
