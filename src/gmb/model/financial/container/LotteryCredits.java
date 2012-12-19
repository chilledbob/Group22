package gmb.model.financial.container;

import gmb.model.CDecimal;
import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class LotteryCredits extends PersiObject
{
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	@Embedded
	protected CDecimal treasuryCedit;
	@Embedded
	protected CDecimal lotteryTaxCedit;
	@Embedded
	protected CDecimal managementCedit;
	
	@Deprecated
	protected LotteryCredits(){}
	
	public LotteryCredits(Object dummy)
	{
		treasuryCedit = new CDecimal(0);
		lotteryTaxCedit = new CDecimal(0);
		managementCedit = new CDecimal(0);
	}
	
	public void setTreasuryDue(CDecimal treasuryCedit){ this.treasuryCedit = treasuryCedit; DB_UPDATE(); }
	public void setLotteryTaxDue(CDecimal lotteryTaxCedit){ this.lotteryTaxCedit = lotteryTaxCedit; DB_UPDATE(); }
	public void setManagementDue(CDecimal managementCedit){ this.managementCedit = managementCedit; DB_UPDATE(); }	
	
	public CDecimal getTreasuryCedit(){ return treasuryCedit; }
	public CDecimal getLotteryTaxCedit(){ return lotteryTaxCedit; }
	public CDecimal getManagementCedit(){ return managementCedit; }
	
	public void update(ReceiptsDistributionResult receipts)
	{
		treasuryCedit = treasuryCedit.add(receipts.getTreasuryDue());
		lotteryTaxCedit = lotteryTaxCedit.add(receipts.getLotteryTaxDue());
		managementCedit = managementCedit.add(receipts.getManagementDue());
		DB_UPDATE(); 
	}
}
