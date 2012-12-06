package gmb.model.request;

import gmb.model.financial.ExternalTransaction;

public class ExternalTransactionRequest extends Request 
{
	protected ExternalTransaction transaction;

	@Deprecated
	protected ExternalTransactionRequest(){}
	
	public ExternalTransactionRequest(ExternalTransaction transaction, String note)
	{
		super(transaction.getAffectedCustomer(), note);
		this.transaction = transaction;
	}
	
	/**
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
