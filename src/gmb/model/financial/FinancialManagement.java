package gmb.model.financial;
import gmb.model.PersiObject;
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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;

@Entity
public class FinancialManagement extends PersiObject
{	
	@Id
	protected int finacialManagementId = 1;
	
	@OneToOne(cascade = CascadeType.ALL)
	LotteryCredits lotteryCredits = new LotteryCredits();
	
	@OneToOne(cascade = CascadeType.ALL)
	Jackpots jackpots = new Jackpots();
	
	@OneToOne(cascade = CascadeType.ALL)
	PrizeCategories prizeCategories = new PrizeCategories();
	
	@OneToOne(cascade = CascadeType.ALL)
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
	
	public void addExternalTransactionRequest(ExternalTransactionRequest request){ externalTransactionRequests.add(request);  DB_UPDATE(); }
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccounDataUpdateRequests.add(request);  DB_UPDATE(); }

	public void addTransaction(TicketPurchase transaction){ ticketPurchases.add(transaction);  DB_UPDATE(); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction);  DB_UPDATE(); }
	public void addTransaction(Winnings transaction){ winnings.add(transaction);  DB_UPDATE(); }
	
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
	
	/**
	 * [intended for direct usage by controller]
	 * @param tipTicketPrices
	 */
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param receiptsDistribution
	 */
	public void setReceiptsDistribution(ReceiptsDistribution receiptsDistribution){ this.receiptsDistribution = receiptsDistribution; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param lotteryCredits
	 */
	public void setLotteryCredits(LotteryCredits lotteryCredits){ this.lotteryCredits = lotteryCredits; DB_UPDATE(); }
	public void setJackpots(Jackpots jackpots){ this.jackpots = jackpots; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * @param prizeCategories
	 */
	public void setPrizeCategories(PrizeCategories prizeCategories){ this.prizeCategories = prizeCategories; DB_UPDATE(); }
	
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
