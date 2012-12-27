package gmb.model.financial.container;

import gmb.model.CDecimal;
import gmb.model.Lottery;
import gmb.model.PersiObject;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class ReceiptsDistributionResult extends PersiObject
{	
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="winnersDue",precision = 10, scale = 2))
	protected CDecimal winnersDue;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="treasuryDue",precision = 10, scale = 2))
	protected CDecimal treasuryDue;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="lotteryTaxDue",precision = 10, scale = 2))
	protected CDecimal lotteryTaxDue;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="managementDue",precision = 10, scale = 2))
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
		
//		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(this);
	}
	
	public void addToTreasuryDue(CDecimal dec){ treasuryDue = treasuryDue.add(dec); DB_UPDATE(); }
	public void addWinnersDueToTreasuryDue(){ treasuryDue = treasuryDue.add(winnersDue); winnersDue = new CDecimal(0); DB_UPDATE(); }
	
	public CDecimal getWinnersDue(){ return winnersDue; }
	public CDecimal getTreasuryDue(){ return treasuryDue; }
	public CDecimal getLotteryTaxDue(){ return lotteryTaxDue; }
	public CDecimal getManagementDue(){ return managementDue; }
}
