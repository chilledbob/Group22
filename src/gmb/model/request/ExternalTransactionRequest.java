package gmb.model.request;

import gmb.model.financial.ExternalTransaction;
import gmb.model.user.Member;

public class ExternalTransactionRequest extends Request 
{
	protected ExternalTransaction transaction;

	@Deprecated
	protected ExternalTransactionRequest(){}
	
	public ExternalTransactionRequest(ExternalTransaction transaction, Member member, String note)
	{
		super(member, note);
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
