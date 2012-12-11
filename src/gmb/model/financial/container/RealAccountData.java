package gmb.model.financial.container;

import gmb.model.PersiObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RealAccountData extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int realAccountDataId;
	
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
