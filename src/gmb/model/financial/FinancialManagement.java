package gmb.model.financial;
import gmb.model.financial.container.Jackpots;
import gmb.model.financial.container.LotteryCredits;
import gmb.model.financial.container.PrizeCategories;
import gmb.model.financial.container.ReceiptsDistribution;
import gmb.model.financial.container.TipTicketPrices;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.financial.transaction.InternalTransaction;
import gmb.model.financial.transaction.TicketPurchase;
import gmb.model.financial.transaction.Transaction;
import gmb.model.financial.transaction.Winnings;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;

@Entity
public class FinancialManagement 
{	
	@Id
	protected int finacialManagementId = 1;
	
	@OneToOne(mappedBy="financialManagementId")
	LotteryCredits lotteryCredits = new LotteryCredits();
	
	@OneToOne(mappedBy="financialManagementId")
	Jackpots jackpots = new Jackpots();
	
	@OneToOne(mappedBy="financialManagementId")
	PrizeCategories prizeCategories = new PrizeCategories();
	
	@OneToOne(mappedBy="financialManagementId")
	protected TipTicketPrices tipTicketPrices;
	
	@OneToOne(mappedBy="financialManagementId")
	protected ReceiptsDistribution receiptsDistribution;
	
	@OneToMany(mappedBy="financialManagementId")
	protected List<TicketPurchase> ticketPurchases;
	
	@ElementCollection
	protected List<Winnings> winnings;
	
	@ElementCollection
	protected List<ExternalTransaction> externalTransactions;

	@OneToMany(mappedBy="financialManagementId")
	protected List<ExternalTransactionRequest> externalTransactionRequests;	
	@ElementCollection
	protected List<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;

	@Deprecated
	protected FinancialManagement(){}
	
	public FinancialManagement(TipTicketPrices tipTicketPrices, ReceiptsDistribution receiptsDistribution)
	{	
		this.tipTicketPrices = tipTicketPrices;
		this.receiptsDistribution = receiptsDistribution;
		
		ticketPurchases = new LinkedList<TicketPurchase>();
		winnings = new LinkedList<Winnings>();
		externalTransactions = new LinkedList<ExternalTransaction>();
	
		externalTransactionRequests = new LinkedList<ExternalTransactionRequest>();
		realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
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
	
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; }
	public void setReceiptsDistribution(ReceiptsDistribution receiptsDistribution){ this.receiptsDistribution = receiptsDistribution; }
	
	public void setLotteryCredits(LotteryCredits lotteryCredits){ this.lotteryCredits = lotteryCredits; }
	public void setJackpots(Jackpots jackpots){ this.jackpots = jackpots; }
	public void setPrizeCategories(PrizeCategories prizeCategories){ this.prizeCategories = prizeCategories; }
	
	public LotteryCredits getLotteryCredits(){ return lotteryCredits; }
	public Jackpots getJackpots(){ return jackpots; }
	public PrizeCategories getPrizeCategories(){ return prizeCategories; }
	
	public TipTicketPrices getTipTicketPrices() { return tipTicketPrices; }
	public ReceiptsDistribution getReceiptsDistribution() { return receiptsDistribution; }
	
	public List<ExternalTransaction> getExternalTransactions() { return externalTransactions; }	
	public List<TicketPurchase> getTicketPurchases() { return ticketPurchases; }
	public List<Winnings> getWinnings() { return winnings; }
	
	public List<ExternalTransactionRequest> getExternalTransactionRequests() { return externalTransactionRequests; }
	public List<RealAccountDataUpdateRequest> getRealAccounDataUpdateRequests() { return realAccounDataUpdateRequests; }
}
