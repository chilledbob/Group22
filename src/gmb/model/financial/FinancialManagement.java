package gmb.model.financial;
import gmb.model.request.RealAccountDataUpdateRequest;

import java.math.BigDecimal;
import java.util.LinkedList;


public class FinancialManagement 
{	
	protected BigDecimal credit;
	protected TipTicketPrices tipTicketPrices;
	protected ReceiptsDistribution receiptsDistribution;
	protected LinkedList<InternalTransaction> internalTransactions;
	protected LinkedList<ExternalTransaction> externalTransactions;
	protected LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;

	@Deprecated
	protected FinancialManagement(){}
	
	public FinancialManagement(TipTicketPrices tipTicketPrices, ReceiptsDistribution receiptsDistribution)
	{
		credit = new BigDecimal(0);
		this.tipTicketPrices = tipTicketPrices;
		this.receiptsDistribution = receiptsDistribution;
		internalTransactions = new LinkedList<InternalTransaction>();
		externalTransactions = new LinkedList<ExternalTransaction>();
		realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
	}

	/**
	 * initializes an internal transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * @param transaction
	 */
	public void initTransaction(InternalTransaction transaction)
	{

		addInternalTransaction(transaction);
//		transaction.getAffectedCustomer().getBankAccount().
	}

	/**
	 * initializes an external transaction
	 * a reference to the transaction will be added to the FinancialManagement and the affected user
	 * @param transaction
	 */
	public void initTransaction(ExternalTransaction transaction)
	{

	}

	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request)
	{
		realAccounDataUpdateRequests.add(request);
	}

	public void addInternalTransaction(InternalTransaction transaction)
	{
		internalTransactions.add(transaction);
	}

	public void addExternalTransaction(ExternalTransaction transaction)
	{
		externalTransactions.add(transaction);
	}

	public void setCredit(BigDecimal credit){ this.credit = credit; }
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; }
	public void setReceiptsDistribution(ReceiptsDistribution receiptsDistribution){ this.receiptsDistribution = receiptsDistribution; }

	public BigDecimal getCredit(){ return credit; }
	public TipTicketPrices getTipTicketPrices() { return tipTicketPrices; }
	public ReceiptsDistribution getReceiptsDistribution() { return receiptsDistribution; }
	
	public LinkedList<ExternalTransaction> getExternalTransactions() { return externalTransactions; }	
	public LinkedList<InternalTransaction> getInternalTransactions() { return internalTransactions; }
	public LinkedList<RealAccountDataUpdateRequest> getRealAccounDataUpdateRequests() { return realAccounDataUpdateRequests; }
}
