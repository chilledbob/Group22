package gmb.model.financial;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.RealAccountDataUpdateRequest;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;


public class FinancialManagement 
{	
	protected BigDecimal credit;
	protected BigDecimal weeklyLottoPrize;
	protected BigDecimal dailyLottoPrize;
	protected BigDecimal totoPrize;
	protected BigDecimal[] prizes;//use this field to implicitly access the prizes using the integer representation of DrawType (0 = WeeklyLotto, 1 = DailyLotto, 2 = Toto)
	
	protected TipTicketPrices tipTicketPrices;
	protected ReceiptsDistribution receiptsDistribution;
	
	protected List<TicketPurchase> ticketPurchases;
	protected List<Winnings> winnings;
	protected List<ExternalTransaction> externalTransactions;

	protected List<ExternalTransactionRequest> externalTransactionRequests;	
	protected List<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;

	@Deprecated
	protected FinancialManagement(){}
	
	public FinancialManagement(TipTicketPrices tipTicketPrices, ReceiptsDistribution receiptsDistribution)
	{
		credit = new BigDecimal(0);
		weeklyLottoPrize = new BigDecimal(0);
		dailyLottoPrize = new BigDecimal(0);
		totoPrize = new BigDecimal(0);
		
		prizes = new BigDecimal[3];
		prizes[0] = weeklyLottoPrize;
		prizes[1] = dailyLottoPrize;
		prizes[2] = totoPrize;
		
		this.tipTicketPrices = tipTicketPrices;
		this.receiptsDistribution = receiptsDistribution;
		
		ticketPurchases = new LinkedList<TicketPurchase>();
		winnings = new LinkedList<Winnings>();
		externalTransactions = new LinkedList<ExternalTransaction>();
	
		externalTransactionRequests = new LinkedList<ExternalTransactionRequest>();
		realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
	}
	
	/**
	 * Updates the "credit" and the "prizes". 
	 * Adds the purchase to the list.
	 * @param purchase
	 */
	public void updateCredit(TicketPurchase purchase)
	{
		BigDecimal ticketPrice = purchase.getAmount().abs();
		BigDecimal toPrize = ticketPrice.multiply(new BigDecimal(receiptsDistribution.getWinnersDue())).divide(new BigDecimal(100));
		BigDecimal revenue = ticketPrice.subtract(toPrize);		
		
		prizes[purchase.getTipTicket().getDrawTypeAsInt()].add(toPrize);
		credit.add(revenue);
		
		ticketPurchases.add(purchase);
	}
	
	/**
	 * Updates the "prizes". 
	 * Adds the winnings to the list.
	 * @param winnings
	 */
	public void updateCredit(Winnings winnings)
	{
		prizes[winnings.getTip().getTipTicket().getDrawTypeAsInt()].subtract(winnings.getAmount());
		this.winnings.add(winnings);
	}
	
	public void addExternalTransactionRequest(ExternalTransactionRequest request){ externalTransactionRequests.add(request); }
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccounDataUpdateRequests.add(request); }

	public void addTransaction(TicketPurchase transaction){ ticketPurchases.add(transaction); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction); }
	public void addTransaction(Winnings transaction){ winnings.add(transaction); }
	
	//delegate method:
	public void addTransaction(Transaction transaction)
	{ 
		if(transaction instanceof Winnings)
			addTransaction((Winnings)transaction);
		else
		if(transaction instanceof InternalTransaction)
			addTransaction((TicketPurchase)transaction);
		else
			addTransaction((ExternalTransaction)transaction);		
	}
	
	public void setCredit(BigDecimal credit){ this.credit = credit; }
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; }
	public void setReceiptsDistribution(ReceiptsDistribution receiptsDistribution){ this.receiptsDistribution = receiptsDistribution; }

	public BigDecimal getCredit(){ return credit; }
	public BigDecimal getWeeklyLottoPrize(){ return weeklyLottoPrize; }
	public BigDecimal getDailyLottoPrize(){ return dailyLottoPrize; }
	public BigDecimal getTotoPrize(){ return totoPrize; }
	
	public TipTicketPrices getTipTicketPrices() { return tipTicketPrices; }
	public ReceiptsDistribution getReceiptsDistribution() { return receiptsDistribution; }
	
	public List<ExternalTransaction> getExternalTransactions() { return externalTransactions; }	
	public List<TicketPurchase> getTicketPurchases() { return ticketPurchases; }
	public List<Winnings> getWinnings() { return winnings; }
	
	public List<ExternalTransactionRequest> getExternalTransactionRequest() { return externalTransactionRequests; }
	public List<RealAccountDataUpdateRequest> getRealAccounDataUpdateRequests() { return realAccounDataUpdateRequests; }
}
