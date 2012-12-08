package gmb.model.financial;
import gmb.model.Lottery;
import gmb.model.user.Customer;

import java.util.Date;
import java.math.BigDecimal;

import org.joda.time.DateTime;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public abstract class Transaction 
{
	@OneToOne 
    @JoinColumn(name="userIdentifier", referencedColumnName="userIdentifier")
	protected Customer affectedCustomer; 
	protected BigDecimal amount;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date date;
	
	@Deprecated
	protected Transaction(){}
	
	public Transaction(Customer affectedCustomer, BigDecimal amount)
	{
		this.affectedCustomer = affectedCustomer;
		this.amount = amount;
		this.date = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	public Customer getAffectedCustomer(){ return affectedCustomer; }
	public BigDecimal getAmount(){ return amount; }
	public DateTime getDate(){ return new DateTime(date); }
	
	public void init()
	{
		affectedCustomer.getBankAccount().updateCredit(this);
	}
}
