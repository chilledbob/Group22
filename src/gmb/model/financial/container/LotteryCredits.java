package gmb.model.financial.container;

import java.math.BigDecimal;

import gmb.model.financial.FinancialManagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class LotteryCredits
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int lotteryCreditId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected BigDecimal treasuryCedit = new BigDecimal(0);
	protected BigDecimal lotteryTaxCedit = new BigDecimal(0);
	protected BigDecimal managementCedit = new BigDecimal(0);
	
	public LotteryCredits(){}
	
	public void setTreasuryDue(BigDecimal treasuryCedit){ this.treasuryCedit = treasuryCedit; }
	public void setLotteryTaxDue(BigDecimal lotteryTaxCedit){ this.lotteryTaxCedit = lotteryTaxCedit; }
	public void setManagementDue(BigDecimal managementCedit){ this.managementCedit = managementCedit; }	
	
	public BigDecimal getTreasuryCedit(){ return treasuryCedit; }
	public BigDecimal getLotteryTaxCedit(){ return lotteryTaxCedit; }
	public BigDecimal getManagementCedit(){ return managementCedit; }
	
	public void update(ReceiptsDistributionResult receipts)
	{
		treasuryCedit = treasuryCedit.add(receipts.getTreasuryDue());
		lotteryTaxCedit = lotteryTaxCedit.add(receipts.getLotteryTaxDue());
		managementCedit = managementCedit.add(receipts.getManagementDue());
	}
}
