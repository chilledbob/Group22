package gmb.model.financial.container;

import gmb.model.Lottery;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReceiptsDistributionResult 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int receiptsDistributionResultId;
	
	protected BigDecimal winnersDue;
	protected BigDecimal treasuryDue;
	protected BigDecimal lotteryTaxDue;
	protected BigDecimal managementDue;
	
	protected static final BigDecimal dec100 = new BigDecimal("100");
	
	@Deprecated
	protected ReceiptsDistributionResult(){}
	
	public ReceiptsDistributionResult(BigDecimal drawReceipts)
	{
		ReceiptsDistribution receiptsDistribution = Lottery.getInstance().getFinancialManagement().getReceiptsDistribution();
		
		winnersDue = drawReceipts.multiply(new BigDecimal(receiptsDistribution.getWinnersDue())).divide(dec100);
		treasuryDue = drawReceipts.multiply(new BigDecimal(receiptsDistribution.getTreasuryDue())).divide(dec100);
		lotteryTaxDue = drawReceipts.multiply(new BigDecimal(receiptsDistribution.getLotteryTaxDue())).divide(dec100);
		managementDue = drawReceipts.multiply(new BigDecimal(receiptsDistribution.getManagementDue())).divide(dec100);
		
		//normalize receipts:
		treasuryDue = treasuryDue.add(drawReceipts.subtract(winnersDue.add(treasuryDue.add(lotteryTaxDue.add(managementDue)))));
		
		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(this);
	}
	
	public void addToTreasuryDue(BigDecimal dec){ treasuryDue = treasuryDue.add(dec); }
	
	public BigDecimal getWinnersDue(){ return winnersDue; }
	public BigDecimal getTreasuryDue(){ return treasuryDue; }
	public BigDecimal getLotteryTaxDue(){ return lotteryTaxDue; }
	public BigDecimal getManagementDue(){ return managementDue; }
}
