package gmb.model.financial.container;

import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class ReceiptsDistribution extends PersiObject
{
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected int winnersDue;
	protected int treasuryDue;
	protected int lotteryTaxDue;
	protected int managementDue;
	
	@Deprecated
	protected ReceiptsDistribution(){}
	
	public ReceiptsDistribution(Object dummy)
	{
		winnersDue = 50;
		treasuryDue = 27;
		lotteryTaxDue = 20;
		managementDue = 3;
	}
	
	public ReceiptsDistribution(int winnersDue, int treasuryDue, int lotteryTaxDue, int managementDue)
	{
		this.winnersDue = winnersDue;
		this.treasuryDue = treasuryDue;
		this.lotteryTaxDue = lotteryTaxDue;
		this.managementDue = managementDue;
	}
	
	public void setWinnersDue(int winnersDue){ this.winnersDue = winnersDue; DB_UPDATE(); }
	public void setTreasuryDue(int treasuryDue){ this.treasuryDue = treasuryDue; DB_UPDATE(); }
	public void setLotteryTaxDue(int lotteryTaxDue){ this.lotteryTaxDue = lotteryTaxDue; DB_UPDATE(); }
	public void setManagementDue(int managementDue){ this.managementDue = managementDue; DB_UPDATE(); }	
	
	public int getWinnersDue(){ return winnersDue; }
	public int getTreasuryDue(){ return treasuryDue; }
	public int getLotteryTaxDue(){ return lotteryTaxDue; }
	public int getManagementDue(){ return managementDue; }
}
