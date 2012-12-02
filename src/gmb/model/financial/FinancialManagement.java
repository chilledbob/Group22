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
	protected LinkedList<Winnings> winnings;
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
		winnings = new LinkedList<Winnings>();
		realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
	}

	/**
	 * updates the credit and adds the transaction to the list
	 * @param transaction
	 */
	public void updateCredit(InternalTransaction transaction)
	{
		credit.subtract(transaction.getAmount());
		this.addTransaction(transaction);
	}
	
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccounDataUpdateRequests.add(request); }

	public void addTransaction(InternalTransaction transaction){ internalTransactions.add(transaction); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction); }
	public void addTransaction(Winnings transaction){ winnings.add(transaction); }
	
	//delegate method:
	public void addTransaction(Transaction transaction)
	{ 
		if(transaction instanceof Winnings)
			addTransaction((Winnings)transaction);
		else
		if(transaction instanceof InternalTransaction)
			addTransaction((InternalTransaction)transaction);
		else
			addTransaction((ExternalTransaction)transaction);		
	}
	
	public void setCredit(BigDecimal credit){ this.credit = credit; }
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; }
	public void setReceiptsDistribution(ReceiptsDistribution receiptsDistribution){ this.receiptsDistribution = receiptsDistribution; }

	public BigDecimal getCredit(){ return credit; }
	public TipTicketPrices getTipTicketPrices() { return tipTicketPrices; }
	public ReceiptsDistribution getReceiptsDistribution() { return receiptsDistribution; }
	
	public LinkedList<ExternalTransaction> getExternalTransactions() { return externalTransactions; }	
	public LinkedList<InternalTransaction> getInternalTransactions() { return internalTransactions; }
	public LinkedList<Winnings> getWinnings() { return winnings; }
	public LinkedList<RealAccountDataUpdateRequest> getRealAccounDataUpdateRequests() { return realAccounDataUpdateRequests; }
}
