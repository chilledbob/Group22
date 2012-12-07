package gmb.model.financial;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class ReceiptsDistribution 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int receiptsDistributionId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected int winnersDue;
	protected int treasuryDue;
	protected int lotteryTaxDue;
	protected int managementDue;
	
	@Deprecated
	protected ReceiptsDistribution(){}
	
	public ReceiptsDistribution(int winnersDue, int treasuryDue, int lotteryTaxDue, int managementDue)
	{
		this.winnersDue = winnersDue;
		this.treasuryDue = treasuryDue;
		this.lotteryTaxDue = lotteryTaxDue;
		this.managementDue = managementDue;
	}
	
	public void setWinnersDue(int winnersDue){ this.winnersDue = winnersDue; }
	public void setTreasuryDue(int treasuryDue){ this.treasuryDue = treasuryDue; }
	public void setLotteryTaxDue(int lotteryTaxDue){ this.lotteryTaxDue = lotteryTaxDue; }
	public void setManagementDue(int managementDue){ this.managementDue = managementDue; }	
	
	public int getWinnersDue(){ return winnersDue; }
	public int getTreasuryDue(){ return treasuryDue; }
	public int getLotteryTaxDue(){ return lotteryTaxDue; }
	public int getManagementDue(){ return managementDue; }
}
