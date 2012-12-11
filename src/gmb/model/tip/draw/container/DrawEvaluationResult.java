package gmb.model.tip.draw.container;

import gmb.model.CDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

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
public class DrawEvaluationResult 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int drawEvaluationResultId;
	
	ReceiptsDistributionResult receiptsDistributionResult = null;
	List<Winnings> winnings = new LinkedList<Winnings>();
	
	@Temporal(value = TemporalType.TIMESTAMP)
	Date evaluationDate;
	
	CDecimal[] jackpotImageBefore;
	CDecimal[] jackpotImageAfter;
	CDecimal[] undistributedPrizes;
	
	CDecimal[] perCategoryPrizePotential = new CDecimal[8];
	CDecimal[] perCategoryWinningsUnMerged = new CDecimal[8];
	CDecimal[] perCategoryWinningsMerged = new CDecimal[8];
	Object[] tipsInCategory = new Object[8];
	
	public DrawEvaluationResult()
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
	
	public void createJackpotImageBefore(CDecimal[] jackpot){ jackpotImageBefore = (CDecimal[])createDeepCopy(jackpot); }
	
	public void createJackpotImageAfterAndUndistributedPrizes(CDecimal[] jackpot)
	{ 
		assert jackpotImageBefore.length == jackpot.length : "Jackpot image length does not fit in DrawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(CDecimal[] jackpot)";
		
		jackpotImageAfter = (CDecimal[])createDeepCopy(jackpot); 
		
		undistributedPrizes = new CDecimal[jackpotImageAfter.length]; 
		
		for(int i = 0; i < undistributedPrizes.length; ++i)
			undistributedPrizes[i] = jackpotImageAfter[i].subtract(jackpotImageBefore[i]);
	}
	
	public CDecimal initReceiptsDistributionResult(CDecimal drawReceipts)
	{
		receiptsDistributionResult = new ReceiptsDistributionResult(drawReceipts);
		return receiptsDistributionResult.getWinnersDue();
	}
	
	public void copyCategoryPrizePotential(CDecimal[] perCategoryPrizePotential)
	{ 
		this.perCategoryPrizePotential = (CDecimal[])createDeepCopy(perCategoryPrizePotential); 
	}
	
	public void copyCategoryWinningsUnMerged(CDecimal[] perCategoryPrizePotentialUnMerged)
	{ 
		this.perCategoryWinningsUnMerged = (CDecimal[])createDeepCopy(perCategoryPrizePotentialUnMerged); 
	}
	
	public void copyCategoryWinningsMerged(CDecimal[] perCategoryPrizePotentialMerged)
	{ 
		this.perCategoryWinningsMerged = (CDecimal[])createDeepCopy(perCategoryPrizePotentialMerged); 
	}
	
	public void copyTipsInCategory(Object[] tipsInCategory)
	{ 
		this.tipsInCategory = createDeepCopy(tipsInCategory); 
	}
	
	public CDecimal[] getCategoryPrizePotential(){ return perCategoryPrizePotential; }
	public CDecimal[] getCategoryWinningsUnMerged(){ return perCategoryWinningsUnMerged; }
	public CDecimal[] getCategoryWinningsMerged(){ return perCategoryWinningsMerged; }
	public Object[] getTipsInCategory(){ return tipsInCategory; }
	
	@SuppressWarnings("unchecked")
	public LinkedList<SingleTip> getTipsInCategory(int categoryID){ return (LinkedList<SingleTip>)(tipsInCategory[categoryID]); }
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); }
	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; }
	
	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }
	
	public CDecimal[] getJackpotImageBefore(){ return jackpotImageBefore; }
	public CDecimal[] getJackpotImageAfter(){ return jackpotImageAfter; }
	public CDecimal[] getUndistributedPrizes(){ return undistributedPrizes; }
}
