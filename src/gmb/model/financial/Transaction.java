package gmb.model.financial;
import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public class Transaction 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int transactionId;
	
	@OneToOne 
    @JoinColumn(name="userIdentifier", referencedColumnName="userIdentifier")
	protected Customer affectedCustomer; 
	protected BigDecimal amount;
	@Temporal(value = TemporalType.DATE)
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
