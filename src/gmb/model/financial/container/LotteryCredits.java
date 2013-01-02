package gmb.model.financial.container;

import gmb.model.CDecimal;
import gmb.model.PersiObject;
import javax.persistence.Column;

import javax.persistence.AttributeOverride;
import javax.persistence.Embedded;
import javax.persistence.Entity;

/**
 * Container class storing the credits of
 * the treasury, the management and the lottery taxes.
 */
@Entity
public class LotteryCredits extends PersiObject
{
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="treasurcCedit",precision = 10, scale = 2))
	protected CDecimal treasuryCedit;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="lotteryTaxCedit",precision = 10, scale = 2))
	protected CDecimal lotteryTaxCedit;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="managementCedit",precision = 10, scale = 2))
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
	
	/**
	 * Adds the receipts to the respective credits.
	 * @param receipts
	 */
	public void update(ReceiptsDistributionResult receipts)
	{
		treasuryCedit = treasuryCedit.add(receipts.getTreasuryDue());
		lotteryTaxCedit = lotteryTaxCedit.add(receipts.getLotteryTaxDue());
		managementCedit = managementCedit.add(receipts.getManagementDue());
		DB_UPDATE(); 
	}
}
