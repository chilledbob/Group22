package gmb.model.request;

import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.transaction.ExternalTransaction;

import javax.persistence.Entity;
import javax.persistence.Embedded;
import javax.persistence.ManyToOne;

@Entity
public class ExternalTransactionRequest extends Request 
{
	@Embedded
	protected ExternalTransaction transaction;
	
	@ManyToOne
	protected LotteryBankAccount lotteryBankAccount;
	
	@ManyToOne
	protected FinancialManagement financialManagementId;

	@Deprecated
	protected ExternalTransactionRequest(){}
	
	public ExternalTransactionRequest(ExternalTransaction transaction, String note)
	{
		super(transaction.getAffectedCustomer(), note);
		this.transaction = transaction;
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
