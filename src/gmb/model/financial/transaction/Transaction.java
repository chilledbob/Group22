package gmb.model.financial.transaction;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.member.Customer;

import java.util.Date;
import gmb.model.CDecimal;

import org.joda.time.DateTime;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Embeddable
public abstract class Transaction extends PersiObject
{
	@OneToOne 
    @JoinColumn(name="userIdentifier", referencedColumnName="userIdentifier")
	protected Customer affectedCustomer; 
	protected CDecimal amount;
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date date;
	
	@Deprecated
	protected Transaction(){}
	
	public Transaction(Customer affectedCustomer, CDecimal amount)
	{
		this.affectedCustomer = affectedCustomer;
		this.amount = amount;
		this.date = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	public Customer getAffectedCustomer(){ return affectedCustomer; }
	public CDecimal getAmount(){ return amount; }
	public DateTime getDate(){ return new DateTime(date); }
	
	public void init()
	{
		affectedCustomer.getBankAccount().updateCredit(this);
	}
}
