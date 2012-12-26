package gmb.model.financial;

import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.ReturnBox;
import gmb.model.financial.container.RealAccountData;
import gmb.model.financial.transaction.ExternalTransaction;
import gmb.model.financial.transaction.TicketPurchase;
import gmb.model.financial.transaction.Transaction;
import gmb.model.financial.transaction.Winnings;
import gmb.model.member.Customer;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.data.RealAccountDataUpdateRequest;

import gmb.model.CDecimal;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.FetchType;

/**
 * The lottery bank account associated with a specific customer.<br>
 * Contains various customer specific financial data.
 */
@Entity
public class LotteryBankAccount extends PersiObject
{	
	@OneToOne(fetch=FetchType.LAZY)
	protected Customer owner;
	@Embedded
	protected CDecimal credit;	
	@OneToOne//(cascade=CascadeType.ALL)
    @JoinColumn(name="REALACCOUNTDATAID") 
	protected RealAccountData realAccountData;
	@OneToMany
	protected List<TicketPurchase> ticketPurchases;
	@OneToMany
	protected List<Winnings> winnings;
	@OneToMany
	protected List<ExternalTransaction> externalTransactions;
	@OneToMany
	protected List<ExternalTransactionRequest> externalTransactionRequests;	
	@OneToMany
	protected List<RealAccountDataUpdateRequest> realAccountDataUpdateRequests;
	
	@Deprecated
	protected LotteryBankAccount(){}
	
	public LotteryBankAccount(RealAccountData realAccountData)
	{
		credit = new CDecimal(0);
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
		DB_UPDATE(); 
	}
	
	/**
	 * [Intended for direct usage by controller] <br>
	 * Creates a "DataUpdateRequest" based on "updatedData" <br>
	 * and adds references to the lists of this "LotteryBankAccount" and the "FinancialManagement". <br>
	 * @param note
	 * @param updatedData
	 * @return the created request
	 */
	public RealAccountDataUpdateRequest sendDataUpdateRequest(RealAccountData updatedData, String note)
	{
		RealAccountDataUpdateRequest request = GmbFactory.new_RealAccountDataUpdateRequest(updatedData, owner, note);
		
		Lottery.getInstance().getFinancialManagement().addRealAccountDataUpdateRequest(request);

		realAccountDataUpdateRequests.add(request);
		
		DB_UPDATE();
		
		return request;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Creates an "ExternalTransactionRequest" based on "transaction" <br>
	 * and adds references to the lists of this "LotteryBankAccount" and the "FinancialManagement".<br>
	 * Returns 1 (var1) if the "transaction" is invalid which is the case when the customer tries <br>
	 * to transact more money to his real account than he is capable to based on his "LotteryBankAccount"'s "credit", <br>
	 * otherwise 0 (var1). Also returns the created request (var2).
	 * @param note
	 * @param updatedData
	 * @return {@link ReturnBox} with:<br>
	 * var1 as {@link Integer}: <br>
	 * <ul>
	 * <li> 0 - successful
	 * <li> 1 - the customer doesn't have enough money (only occurs when transferring money to the real bank account)
	 * </ul>
	 * var2 as {@link ExternalTransactionRequest}:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created ExternalTransactionRequest
	 * <li> var1 != 1 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, ExternalTransactionRequest> sendExternalTransactionRequest(CDecimal amount, String note)
	{
		if(amount.signum() != -1 || owner.hasEnoughMoneyToPurchase(amount))
		{
			ExternalTransactionRequest request = GmbFactory.new_ExternalTransactionRequest(GmbFactory.new_ExternalTransaction(owner, amount), note);
			
			externalTransactionRequests.add(request);
			Lottery.getInstance().getFinancialManagement().addExternalTransactionRequest(request);
			
			DB_UPDATE(); 
			
			return new ReturnBox<Integer, ExternalTransactionRequest>(new Integer(0), request);
		}
		else
			return new ReturnBox<Integer, ExternalTransactionRequest>(new Integer(1), null);
	}
	
	public void setOwner(Customer owner){ this.owner = owner; DB_UPDATE(); }
	public void setCredit(CDecimal credit){ this.credit = credit; DB_UPDATE(); }
	public void setRealAccountData(RealAccountData realAccountData){ this.realAccountData = realAccountData; DB_UPDATE(); }	
	
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
	
	public void addTransaction(TicketPurchase purchase){ ticketPurchases.add(purchase); DB_UPDATE(); }
	public void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction); DB_UPDATE(); }
	public void addTransaction(Winnings transaction){ winnings.add(transaction); DB_UPDATE(); }
	
	public void addExternalTransactionRequest(ExternalTransactionRequest request){ externalTransactionRequests.add(request); DB_UPDATE(); }
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccountDataUpdateRequests.add(request); DB_UPDATE(); }
	
	public CDecimal getCredit(){ return credit; }
	public Customer getOwner(){ return owner; }
	public RealAccountData getRealAccountData(){ return realAccountData; }
	
	public List<TicketPurchase> getTicketPurchases(){ return ticketPurchases; }
	public List<ExternalTransaction> getExternalTransactions(){ return externalTransactions; }	
	public List<Winnings> getWinnings(){ return winnings; }
	
	public List<ExternalTransactionRequest> getExternalTransactionRequest() { return externalTransactionRequests; }
	public List<RealAccountDataUpdateRequest> getRealAccountDataUpdateRequest(){ return realAccountDataUpdateRequests; }
}
