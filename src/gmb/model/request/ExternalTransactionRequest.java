package gmb.model.request;

import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.member.Customer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ExternalTransactionRequest extends Request 
{
	@OneToOne(cascade=CascadeType.ALL)
	protected ExternalTransaction transaction;
	
	@ManyToOne
	protected LotteryBankAccount lotteryBankAccount;
	
	@ManyToOne(cascade=CascadeType.ALL)
	protected FinancialManagement financialManagementId;

	@Deprecated
	protected ExternalTransactionRequest(){}
	
	public ExternalTransactionRequest(ExternalTransaction transaction, String note)
	{
		super(transaction.getAffectedCustomer(), note);
		this.transaction = transaction;
		this.financialManagementId = Lottery.getInstance().getFinancialManagement();
		Customer c = transaction.getAffectedCustomer();
		
		this.lotteryBankAccount = c.getBankAccount();
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Return code:
	 * 0 - successful
	 * 1 - failed because state was not "UNHANDLED"
	 */
	public int accept()
	{
		if(super.accept() == 0)
		{
			transaction.init();
			return 0;
		}
		else
			return 1;
	}
	
	public ExternalTransaction getTransaction(){ return transaction; }
}
