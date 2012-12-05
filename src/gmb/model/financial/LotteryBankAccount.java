package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.request.RealAccountDataUpdateRequest;
import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.LinkedList;


public class LotteryBankAccount 
{
	protected Customer owner;
	protected BigDecimal credit;	
	protected RealAccountData realAccountData;
	
	protected LinkedList<TicketPurchase> ticketPurchases;
	protected LinkedList<Winnings> winnings;
	protected LinkedList<ExternalTransaction> externalTransactions;

	protected LinkedList<ExternalTransactionRequest> externalTransactionRequests;	
	protected LinkedList<RealAccountDataUpdateRequest> realAccountDataUpdateRequests;
	
	@Deprecated
	protected LotteryBankAccount(){}
	
	public LotteryBankAccount(Customer owner, RealAccountData realAccountData)
	{
		this.owner = owner;
		
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
		credit.add(transaction.getAmount());
		this.addTransaction(transaction);
	}
	
	/**
	 * Creates a "DataUpdateRequest" based on "updatedData" 
	 * and adds references to the lists of this "LotteryBankAccount" and the "FinancialManagement".
	 * @param note
	 * @param updatedData
	 */
	public void sendDataUpdateRequest(String note, RealAccountData updatedData)
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
	public boolean sendExternalTransactionRequest(String note, ExternalTransaction transaction)
	{
		if(transaction.getAmount().signum() != -1 || owner.hasEnoughMoneyToPurchase(transaction.getAmount()))
		{
			ExternalTransactionRequest request = new ExternalTransactionRequest(transaction, owner, note);
			
			externalTransactionRequests.add(request);
			Lottery.getInstance().getFinancialManagement().addExternalTransactionRequest(request);
			
			return true;
		}
		else
			return false;
	}
	
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
	
	public LinkedList<TicketPurchase> getTicketPurchases(){ return ticketPurchases; }
	public LinkedList<ExternalTransaction> getExternalTransactions(){ return externalTransactions; }	
	public LinkedList<Winnings> getWinnings(){ return winnings; }
	
	public LinkedList<ExternalTransactionRequest> getExternalTransactionRequest() { return externalTransactionRequests; }
	public LinkedList<RealAccountDataUpdateRequest> getRealAccountDataUpdateRequest(){ return realAccountDataUpdateRequests; }
}
