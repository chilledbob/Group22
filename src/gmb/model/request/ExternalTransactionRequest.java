package gmb.model.request;

import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.FinancialManagement;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.member.Customer;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 * A request type for money transaction to or from the lottery bank account.
 */
@Entity
public class ExternalTransactionRequest extends Request 
{
	@OneToOne
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
		
		this.financialManagementId = Lottery.getInstance().getFinancialManagement();	
		this.lotteryBankAccount = ((Customer)transaction.getAffectedCustomer()).getBankAccount();
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
