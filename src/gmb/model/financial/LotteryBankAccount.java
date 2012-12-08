package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.RealAccountDataUpdateRequest;
import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.ElementCollection;
import javax.persistence.OneToMany;

@Entity
public class LotteryBankAccount 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int lotteryBankAccountId;
	
	@OneToOne 
    @JoinColumn(name="userIdentifier", referencedColumnName="userIdentifier") 
	protected Customer owner;
	protected BigDecimal credit;	
	@OneToOne 
    @JoinColumn(name="realAccountDataId") 
	protected RealAccountData realAccountData;
	@OneToMany(mappedBy="lotteryBankAccount")
	protected List<TicketPurchase> ticketPurchases;
	@ElementCollection
	protected List<Winnings> winnings;
	@ElementCollection
	protected List<ExternalTransaction> externalTransactions;
	@OneToMany
	protected List<ExternalTransactionRequest> externalTransactionRequests;	
	@ElementCollection
	protected List<RealAccountDataUpdateRequest> realAccountDataUpdateRequests;
	
	@Deprecated
	protected LotteryBankAccount(){}
	
	public LotteryBankAccount(RealAccountData realAccountData)
	{
		credit = new BigDecimal(0);
		this.realAccountData = realAccountData;
		ticketPurchases = new LinkedList<TicketPurchase>();
		externalTransactions = new LinkedList<ExternalTransaction>();
		winnings = new LinkedList<Winnings>();
		
		externalTransactionRequests = new LinkedList<ExternalTransactionRequest>();
		realAccountDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
	}
	
	/**
	 * Updates the "credit" and adds the "transaction" to the list.
	 * @param transaction
	 */
	public void updateCredit(Transaction transaction)
	{
		credit = credit.add(transaction.getAmount());
		this.addTransaction(transaction);
	}
	
	/**
	 * Creates a "DataUpdateRequest" based on "updatedData" 
	 * and adds references to the lists of this "LotteryBankAccount" and the "FinancialManagement".
	 * @param note
	 * @param updatedData
	 */
	public void sendDataUpdateRequest(RealAccountData updatedData, String note)
	{
		RealAccountDataUpdateRequest request = new RealAccountDataUpdateRequest(updatedData, owner, note);
		
		Lottery.getInstance().getFinancialManagement().addRealAccountDataUpdateRequest(request);

		realAccountDataUpdateRequests.add(request);
	}
	
	/**
	 * Creates an "ExternalTransactionRequest" based on "transaction" 
	 * and adds references to the lists of this "LotteryBankAccount" and the "FinancialManagement".
	 * Returns false if the "transaction" is invalid which is the case when the customer tries
	 * to transact more money to his real account than he is capable to based on his "LotteryBankAccount"'s "credit",
	 * otherwise true.
	 * @param note
	 * @param updatedData
	 */
	public boolean sendExternalTransactionRequest(BigDecimal amount, String note)
	{
		if(amount.signum() != -1 || owner.hasEnoughMoneyToPurchase(amount))
		{
			ExternalTransactionRequest request = new ExternalTransactionRequest(new ExternalTransaction(owner, amount), note);
			
			externalTransactionRequests.add(request);
			Lottery.getInstance().getFinancialManagement().addExternalTransactionRequest(request);
			
			return true;
		}
		else
			return false;
	}
	
	public void setOwner(Customer owner){ this.owner = owner; }
	public void setCredit(BigDecimal credit){ this.credit = credit; }
	public void setRealAccountData(RealAccountData realAccountData){ this.realAccountData = realAccountData; }	
	
	//delegate method:
	public void addTransaction(Transaction transaction)
	{ 
		if(transaction instanceof Winnings)
			addTransaction((Winnings)transaction);
		else
		if(transaction instanceof TicketPurchase)
			addTransaction((TicketPurchase)transaction);
		else
			addTransaction((ExternalTransaction)transaction);		
	}
	
	public void addTransaction(TicketPurchase purchase){ ticketPurchases.add(purchase); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction); }
	public void addTransaction(Winnings transaction){ winnings.add(transaction); }
	
	public void addExternalTransactionRequest(ExternalTransactionRequest request){ externalTransactionRequests.add(request); }
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccountDataUpdateRequests.add(request); }
	
	public BigDecimal getCredit(){ return credit; }
	public Customer getOwner(){ return owner; }
	public RealAccountData getRealAccountData(){ return realAccountData; }
	
	public List<TicketPurchase> getTicketPurchases(){ return ticketPurchases; }
	public List<ExternalTransaction> getExternalTransactions(){ return externalTransactions; }	
	public List<Winnings> getWinnings(){ return winnings; }
	
	public List<ExternalTransactionRequest> getExternalTransactionRequest() { return externalTransactionRequests; }
	public List<RealAccountDataUpdateRequest> getRealAccountDataUpdateRequest(){ return realAccountDataUpdateRequests; }
}
