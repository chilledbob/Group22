package gmb.model.financial;

public class RealAccountData 
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
	
//	public void setBankCode(String bankCode){ this.bankCode = bankCode; }
//	public void setAccountNumber(String accountNumber){ this.accountNumber = accountNumber; }
	
	public String getBankCode(){ return bankCode; }	
	public String getAccountNumber(){ return accountNumber; }	
}
