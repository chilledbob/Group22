package gmb.model.financial.transaction;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.member.Customer;

import java.util.Date;
import gmb.model.CDecimal;

import org.joda.time.DateTime;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
public abstract class Transaction extends PersiObject
{
	@OneToOne(fetch=FetchType.EAGER)
	protected Customer affectedCustomer; 
	@Embedded
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
