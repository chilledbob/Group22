package gmb.model.financial;

import gmb.model.Lottery;
import gmb.model.request.RealAccountDataUpdateRequest;
import gmb.model.user.Customer;

import java.math.BigDecimal;
import java.util.LinkedList;


public class LotteryBankAccount 
{
	protected Customer owner;
	protected BigDecimal credit;	
	protected RealAccountData realAccountData;
	protected LinkedList<InternalTransaction> internalTransactions;
	protected LinkedList<ExternalTransaction> externalTransactions;
	protected LinkedList<Winnings> winnings;
	protected LinkedList<RealAccountDataUpdateRequest> realAccounDataUpdateRequests;
	
	@Deprecated
	protected LotteryBankAccount(){}
	
	public LotteryBankAccount(Customer owner, RealAccountData realAccountData)
	{
		this.owner = owner;
		
		credit = new BigDecimal(0);
		this.realAccountData = realAccountData;
		internalTransactions = new LinkedList<InternalTransaction>();
		externalTransactions = new LinkedList<ExternalTransaction>();
		winnings = new LinkedList<Winnings>();
		realAccounDataUpdateRequests = new LinkedList<RealAccountDataUpdateRequest>();
	}
	
	/**
	 * updates the credit and adds the transaction to the list
	 * @param transaction
	 */
	public void updateCredit(Transaction transaction)
	{
		credit.add(transaction.getAmount());
		this.addTransaction(transaction);
	}
	
	public void sendDataUpdateRequest(String note, RealAccountData updatedData)
	{
		RealAccountDataUpdateRequest request = new RealAccountDataUpdateRequest(updatedData, owner, note);
		
		Lottery.getInstance().getFinancialManagement().addRealAccountDataUpdateRequest(request);

		realAccounDataUpdateRequests.add(request);
	}
	
	public void setCredit(BigDecimal credit){ this.credit = credit; }
	public void setRealAccountData(RealAccountData realAccountData){ this.realAccountData = realAccountData; }	
	
	//delegate method:
	protected void addTransaction(Transaction transaction)
	{ 
		if(transaction instanceof InternalTransaction)
			addTransaction((InternalTransaction)transaction);
		else
			addTransaction((ExternalTransaction)transaction);		
	}
	
	protected void addTransaction(InternalTransaction transaction){ internalTransactions.add(transaction); }
	protected void addTransaction(ExternalTransaction transaction){ externalTransactions.add(transaction); }
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); }
	public void addRealAccountDataUpdateRequest(RealAccountDataUpdateRequest request){ realAccounDataUpdateRequests.add(request); }
	
	public BigDecimal getCredit(){ return credit; }
	public Customer getOwner(){ return owner; }
	public RealAccountData getRealAccountData(){ return realAccountData; }
	
	public LinkedList<InternalTransaction> getInternalTransactions(){ return internalTransactions; }
	public LinkedList<ExternalTransaction> getExternalTransactions(){ return externalTransactions; }	
	public LinkedList<Winnings> getWinnings(){ return winnings; }
	public LinkedList<RealAccountDataUpdateRequest> getRealAccountDataUpdateRequest(){ return realAccounDataUpdateRequests; }
}
