package gmb.model.tip.draw.container;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.tip.single.SingleTip;

@Entity
public class DrawEvaluationResult 
{
	ReceiptsDistributionResult receiptsDistributionResult = null;
	List<Winnings> winnings = new LinkedList<Winnings>();
	
	@Temporal(value = TemporalType.TIMESTAMP)
	Date evaluationDate;
	
	BigDecimal[] jackpotImageBefore;
	BigDecimal[] jackpotImageAfter;
	BigDecimal[] undistributedPrizes;
	
	BigDecimal[] perCategoryPrizePotential = new BigDecimal[8];
	BigDecimal[] perCategoryWinningsUnMerged = new BigDecimal[8];
	BigDecimal[] perCategoryWinningsMerged = new BigDecimal[8];
	Object[] tipsInCategory = new Object[8];
	
	public DrawEvaluationResult()
	{
		evaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	protected BigDecimal[] createDeepCopy(BigDecimal[] array)
	{
		BigDecimal[] copy = new BigDecimal[array.length];
		
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
	
	public void createJackpotImageBefore(BigDecimal[] jackpot){ jackpotImageBefore = (BigDecimal[])createDeepCopy(jackpot); }
	
	public void createJackpotImageAfterAndUndistributedPrizes(BigDecimal[] jackpot)
	{ 
		assert jackpotImageBefore.length == jackpot.length : "Jackpot image length does not fit in DrawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(BigDecimal[] jackpot)";
		
		jackpotImageAfter = (BigDecimal[])createDeepCopy(jackpot); 
		
		undistributedPrizes = new BigDecimal[jackpotImageAfter.length]; 
		
		for(int i = 0; i < undistributedPrizes.length; ++i)
			undistributedPrizes[i] = jackpotImageAfter[i].subtract(jackpotImageBefore[i]);
	}
	
	public BigDecimal initReceiptsDistributionResult(BigDecimal drawReceipts)
	{
		receiptsDistributionResult = new ReceiptsDistributionResult(drawReceipts);
		return receiptsDistributionResult.getWinnersDue();
	}
	
	public void copyCategoryPrizePotential(BigDecimal[] perCategoryPrizePotential)
	{ 
		this.perCategoryPrizePotential = (BigDecimal[])createDeepCopy(perCategoryPrizePotential); 
	}
	
	public void copyCategoryWinningsUnMerged(BigDecimal[] perCategoryPrizePotentialUnMerged)
	{ 
		this.perCategoryWinningsUnMerged = (BigDecimal[])createDeepCopy(perCategoryPrizePotentialUnMerged); 
	}
	
	public void copyCategoryWinningsMerged(BigDecimal[] perCategoryPrizePotentialMerged)
	{ 
		this.perCategoryWinningsMerged = (BigDecimal[])createDeepCopy(perCategoryPrizePotentialMerged); 
	}
	
	public void copyTipsInCategory(Object[] tipsInCategory)
	{ 
		this.tipsInCategory = createDeepCopy(tipsInCategory); 
	}
	
	public BigDecimal[] getCategoryPrizePotential(){ return perCategoryPrizePotential; }
	public BigDecimal[] getCategoryWinningsUnMerged(){ return perCategoryWinningsUnMerged; }
	public BigDecimal[] getCategoryWinningsMerged(){ return perCategoryWinningsMerged; }
	public Object[] getTipsInCategory(){ return tipsInCategory; }
	
	@SuppressWarnings("unchecked")
	public LinkedList<SingleTip> getTipsInCategory(int categoryID){ return (LinkedList<SingleTip>)(tipsInCategory[categoryID]); }
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); }
	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; }
	
	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }
	
	public BigDecimal[] getJackpotImageBefore(){ return jackpotImageBefore; }
	public BigDecimal[] getJackpotImageAfter(){ return jackpotImageAfter; }
	public BigDecimal[] getUndistributedPrizes(){ return undistributedPrizes; }
}
