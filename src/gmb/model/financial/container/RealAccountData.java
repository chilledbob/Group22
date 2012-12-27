package gmb.model.financial.container;

import gmb.model.PersiObject;

import javax.persistence.Entity;

/**
 * Container class storing information about 
 * the real bank account which is associated with 
 * the lottery bank account. 
 */
@Entity
public class RealAccountData extends PersiObject
{	
	protected String bankCode;
	protected String accountNumber;
	
	@Deprecated
	protected RealAccountData(){}
	
	public RealAccountData(String bankCode, String accountNumber)
	{
		this.bankCode = bankCode;
		this.accountNumber = accountNumber;
	}
	
//	public void setBankCode(String bankCode){ this.bankCode = bankCode; DB_UPDATE(); }
//	public void setAccountNumber(String accountNumber){ this.accountNumber = accountNumber; DB_UPDATE(); }
	
	public String getBankCode(){ return bankCode; }	
	public String getAccountNumber(){ return accountNumber; }	
}
