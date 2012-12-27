package gmb.model.financial;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;

@Entity
public class FinancialManagement extends PersiObject
{	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="LOTTERYCREDITS_PERSISTENCEID")
	protected LotteryCredits lotteryCredits;

	@OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinColumn(name="JACKPOTS_PERSISTENCEID")
	protected Jackpots jackpots;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PRIZECATEGORIES_PERSISTENCEID")
	protected PrizeCategories prizeCategories;

	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="TIPTICKETPRICES_PERSISTENCEID")
	protected TipTicketPrices tipTicketPrices;

	@OneToOne
	@JoinColumn(name="RECEIPTSDISTRIBUTION_PERSISTENCEID")
	protected ReceiptsDistribution receiptsDistribution;

	@OneToMany
	protected List<TicketPurchase> ticketPurchases;

	@OneToMany
	protected List<Winnings> winnings;

	@OneToMany
	protected List<ExternalTransaction> externalTransactions;

	@OneToMany(mappedBy="financialManagementId")
	protected List<ExternalTransactionRequest> externalTransactionRequests;	
	@OneToMany
	protected List<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;

	@Deprecated
	protected FinancialManagement(){}

	public FinancialManagement(TipTicketPrices tipTicketPrices, ReceiptsDistribution receiptsDistribution)
	{	
		lotteryCredits = GmbFactory.new_LotteryCredits();
		jackpots = new Jackpots(null);
		prizeCategories = GmbFactory.new_PrizeCategories();

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

	public void addTransaction(TicketPurchase transaction){ ticketPurchases.add(transaction); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction);  /*DB_UPDATE();*/ }
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
	public void setTipTicketPrices(TipTicketPrices tipTicketPrices){ this.tipTicketPrices = tipTicketPrices; /*DB_UPDATE();*/ }

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
