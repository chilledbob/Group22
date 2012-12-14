package gmb.model.tip.draw.container;

import gmb.model.CDecimal;
import gmb.model.PersiObject;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.tip.single.SingleTip;

@Entity
public class DrawEvaluationResult extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int drawEvaluationResultId;
	
	protected ReceiptsDistributionResult receiptsDistributionResult = null;
	@ElementCollection
	protected List<Winnings> winnings = new LinkedList<Winnings>();
	
	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date evaluationDate;
	
	protected CDecimal[] jackpotImageBefore;
	protected CDecimal[] jackpotImageAfter;
	protected CDecimal[] undistributedPrizes;
	
	protected CDecimal[] perCategoryPrizePotential = new CDecimal[8];
	protected CDecimal[] perCategoryWinningsUnMerged = new CDecimal[8];
	protected CDecimal[] perCategoryWinningsMerged = new CDecimal[8];
	protected Object[] tipsInCategory = new Object[8];
	
	protected CDecimal normalizationAmount;
	
	protected DrawEvaluationResult(){}
	
	public DrawEvaluationResult(int dummy)
	{
		evaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	protected CDecimal[] createDeepCopy(CDecimal[] array)
	{
		CDecimal[] copy = new CDecimal[array.length];
		
		for(int i = 0; i < array.length; ++i)
			copy[i] = array[i];
		
		return copy;
	}
	
	protected Object[] createDeepCopy(Object[] array)
	{
		Object[] copy = new Object[array.length];
		
		for(int i = 0; i < array.length; ++i)
			copy[i] = array[i];
		
		return copy;
	}
	
	public void createJackpotImageBefore(CDecimal[] jackpot){ jackpotImageBefore = (CDecimal[])createDeepCopy(jackpot); DB_UPDATE(); }
	
	public void createJackpotImageAfterAndUndistributedPrizes(CDecimal[] jackpot)
	{ 
		assert jackpotImageBefore.length == jackpot.length : "Jackpot image length does not fit in DrawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(CDecimal[] jackpot)";
		
		jackpotImageAfter = createDeepCopy(jackpot); 
		
		undistributedPrizes = new CDecimal[jackpotImageAfter.length]; 
		
		for(int i = 0; i < undistributedPrizes.length; ++i)
			undistributedPrizes[i] = jackpotImageAfter[i].subtract(jackpotImageBefore[i]);
		
		DB_UPDATE(); 
	}
	
	public CDecimal initReceiptsDistributionResult(CDecimal drawReceipts)
	{
		receiptsDistributionResult = new ReceiptsDistributionResult(drawReceipts);
		DB_UPDATE(); 
		
		return receiptsDistributionResult.getWinnersDue();
	}
	
	public void copyCategoryPrizePotential(CDecimal[] perCategoryPrizePotential)
	{ 
		this.perCategoryPrizePotential = createDeepCopy(perCategoryPrizePotential); 
		DB_UPDATE(); 
	}
	
	public void copyCategoryWinningsUnMerged(CDecimal[] perCategoryPrizePotentialUnMerged)
	{ 
		this.perCategoryWinningsUnMerged = createDeepCopy(perCategoryPrizePotentialUnMerged); 
		DB_UPDATE(); 
	}
	
	public void copyCategoryWinningsMerged(CDecimal[] perCategoryPrizePotentialMerged)
	{ 
		this.perCategoryWinningsMerged = createDeepCopy(perCategoryPrizePotentialMerged); 
		DB_UPDATE(); 
	}
	
	public void copyTipsInCategory(Object[] tipsInCategory)
	{ 
		this.tipsInCategory = createDeepCopy(tipsInCategory); 
		DB_UPDATE(); 
	}
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); DB_UPDATE(); }
	public void addWinnings(LinkedList<Winnings> winnings){ this.winnings.addAll(winnings); DB_UPDATE(); }
	
	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; DB_UPDATE(); }
	
	public void setNormalizationAmount(CDecimal normalizationAmount){ this.normalizationAmount = normalizationAmount; DB_UPDATE(); }
	
	public CDecimal getNormalizationAmount(){ return normalizationAmount; }
	
	public CDecimal[] getCategoryPrizePotential(){ return perCategoryPrizePotential; }
	public CDecimal[] getCategoryWinningsUnMerged(){ return perCategoryWinningsUnMerged; }
	public CDecimal[] getCategoryWinningsMerged(){ return perCategoryWinningsMerged; }
	public Object[] getTipsInCategory(){ return tipsInCategory; }
	
	@SuppressWarnings("unchecked")
	public LinkedList<SingleTip> getTipsInCategory(int categoryID){ return (LinkedList<SingleTip>)(tipsInCategory[categoryID]); }
	
	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }
	
	public CDecimal[] getJackpotImageBefore(){ return jackpotImageBefore; }
	public CDecimal[] getJackpotImageAfter(){ return jackpotImageAfter; }
	public CDecimal[] getUndistributedPrizes(){ return undistributedPrizes; }
}
