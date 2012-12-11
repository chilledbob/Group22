package gmb.model.financial.container;

import gmb.model.CDecimal;
import gmb.model.Lottery;
import gmb.model.PersiObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ReceiptsDistributionResult extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int receiptsDistributionResultId;
	
	protected CDecimal winnersDue;
	protected CDecimal treasuryDue;
	protected CDecimal lotteryTaxDue;
	protected CDecimal managementDue;
	
	protected static final CDecimal dec100 = new CDecimal(100);
	
	@Deprecated
	protected ReceiptsDistributionResult(){}
	
	public ReceiptsDistributionResult(CDecimal drawReceipts)
	{
		ReceiptsDistribution receiptsDistribution = Lottery.getInstance().getFinancialManagement().getReceiptsDistribution();
		
		winnersDue = drawReceipts.multiply(new CDecimal(receiptsDistribution.getWinnersDue())).divide(dec100);
		treasuryDue = drawReceipts.multiply(new CDecimal(receiptsDistribution.getTreasuryDue())).divide(dec100);
		lotteryTaxDue = drawReceipts.multiply(new CDecimal(receiptsDistribution.getLotteryTaxDue())).divide(dec100);
		managementDue = drawReceipts.multiply(new CDecimal(receiptsDistribution.getManagementDue())).divide(dec100);
		
		//normalize receipts:
		treasuryDue = treasuryDue.add(drawReceipts.subtract(winnersDue.add(treasuryDue.add(lotteryTaxDue.add(managementDue)))));
		
		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(this);
	}
	
	public void addToTreasuryDue(CDecimal dec){ treasuryDue = treasuryDue.add(dec); DB_UPDATE(); }
	
	public CDecimal getWinnersDue(){ return winnersDue; }
	public CDecimal getTreasuryDue(){ return treasuryDue; }
	public CDecimal getLotteryTaxDue(){ return lotteryTaxDue; }
	public CDecimal getManagementDue(){ return managementDue; }
}
