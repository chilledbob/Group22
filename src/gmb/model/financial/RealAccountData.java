package gmb.model.financial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RealAccountData 
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
	
//	public void setBankCode(String bankCode){ this.bankCode = bankCode; }
//	public void setAccountNumber(String accountNumber){ this.accountNumber = accountNumber; }
	
	public String getBankCode(){ return bankCode; }	
	public String getAccountNumber(){ return accountNumber; }	
}
