package gmb.model.tip.draw.container;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.PersiObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.tip.single.SingleTip;

@Entity
public class WeeklyLottoDrawEvaluationResult extends PersiObject
{
	protected ReceiptsDistributionResult receiptsDistributionResult;
	@ElementCollection
	protected List<Winnings> winnings;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date evaluationDate;

	protected List<CDecimal> jackpotImageBefore;
	protected List<CDecimal> jackpotImageAfter;
	protected List<CDecimal> undistributedPrizes;

	protected List<CDecimal> perCategoryPrizePotential;
	protected List<CDecimal> perCategoryWinningsUnMerged;
	protected List<CDecimal> perCategoryWinningsMerged;
	protected ArrayList<LinkedList<SingleTip>> tipsInCategory;

	protected CDecimal normalizationAmount;

	@Deprecated
	protected WeeklyLottoDrawEvaluationResult(){}

	public WeeklyLottoDrawEvaluationResult(Object dummy)
	{
		receiptsDistributionResult = null;
		winnings = new LinkedList<Winnings>();

		perCategoryPrizePotential = ArrayListFac.new_CDecimalArray(8);
		perCategoryWinningsUnMerged = ArrayListFac.new_CDecimalArray(8);
		perCategoryWinningsMerged = ArrayListFac.new_CDecimalArray(8);
		tipsInCategory = ArrayListFac.new_SingleTipLinkedListArray(8);

		evaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}

	protected ArrayList<CDecimal> createDeepCopyForCDecimalArray(ArrayList<CDecimal> array)
	{
		List<CDecimal> copy = ArrayListFac.new_CDecimalArray(array.size());

		for(int i = 0; i < array.size(); ++i)
			copy.set(i, array.get(i));

		return (ArrayList<CDecimal>)copy;
	}

	protected ArrayList<LinkedList<SingleTip>> createDeepCopyForSingleTipLLArray(ArrayList<LinkedList<SingleTip>> array)
	{
		ArrayList<LinkedList<SingleTip>> copy = ArrayListFac.new_SingleTipLinkedListArray(array.size());

		for(int i = 0; i < array.size(); ++i)
			copy.set(i, array.get(i));

		return copy;
	}

	public void createJackpotImageBefore(ArrayList<CDecimal> jackpot){ jackpotImageBefore = createDeepCopyForCDecimalArray(jackpot); DB_UPDATE(); }

	public void createJackpotImageAfterAndUndistributedPrizes(ArrayList<CDecimal> jackpot)
	{ 
		assert jackpotImageBefore.size() == jackpot.size() : "Jackpot image length does not fit in DrawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(CDecimal[] jackpot)";

		jackpotImageAfter = createDeepCopyForCDecimalArray(jackpot); 

		undistributedPrizes = new ArrayList<CDecimal>(jackpotImageAfter.size()); 

		for(int i = 0; i < undistributedPrizes.size(); ++i)
			undistributedPrizes.set(i, jackpotImageAfter.get(i).subtract(jackpotImageBefore.get(i)));

		DB_UPDATE(); 
	}

	public CDecimal initReceiptsDistributionResult(CDecimal drawReceipts)
	{
		receiptsDistributionResult = GmbFactory.new_ReceiptsDistributionResult(drawReceipts);
		
		DB_UPDATE(); 

		return receiptsDistributionResult.getWinnersDue();
	}

	public void copyCategoryPrizePotential(ArrayList<CDecimal> perCategoryPrizePotential)
	{ 
		this.perCategoryPrizePotential = createDeepCopyForCDecimalArray(perCategoryPrizePotential); 
		DB_UPDATE(); 
	}

	public void copyCategoryWinningsUnMerged(ArrayList<CDecimal> perCategoryPrizePotentialUnMerged)
	{ 
		this.perCategoryWinningsUnMerged = createDeepCopyForCDecimalArray(perCategoryPrizePotentialUnMerged); 
		DB_UPDATE(); 
	}

	public void copyCategoryWinningsMerged(ArrayList<CDecimal> perCategoryPrizePotentialMerged)
	{ 
		this.perCategoryWinningsMerged = createDeepCopyForCDecimalArray(perCategoryPrizePotentialMerged); 
		DB_UPDATE(); 
	}

	public void copyTipsInCategory(ArrayList<LinkedList<SingleTip>> tipsInCategory)
	{ 
		this.tipsInCategory = createDeepCopyForSingleTipLLArray(tipsInCategory); 
		DB_UPDATE(); 
	}

	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); DB_UPDATE(); }
	public void addWinnings(LinkedList<Winnings> winnings){ this.winnings.addAll(winnings); DB_UPDATE(); }

	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; DB_UPDATE(); }

	public void setNormalizationAmount(CDecimal normalizationAmount){ this.normalizationAmount = normalizationAmount; DB_UPDATE(); }

	public CDecimal getNormalizationAmount(){ return normalizationAmount; }

	public ArrayList<CDecimal> getCategoryPrizePotential(){ return (ArrayList<CDecimal>) perCategoryPrizePotential; }
	public ArrayList<CDecimal> getCategoryWinningsUnMerged(){ return (ArrayList<CDecimal>) perCategoryWinningsUnMerged; }
	public ArrayList<CDecimal> getCategoryWinningsMerged(){ return (ArrayList<CDecimal>) perCategoryWinningsMerged; }
	public ArrayList<LinkedList<SingleTip>> getTipsInCategory(){ return tipsInCategory; }

	public LinkedList<SingleTip> getTipsInCategory(int categoryID){ return tipsInCategory.get(categoryID); }

	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }

	public ArrayList<CDecimal> getJackpotImageBefore(){ return (ArrayList<CDecimal>) jackpotImageBefore; }
	public ArrayList<CDecimal> getJackpotImageAfter(){ return (ArrayList<CDecimal>) jackpotImageAfter; }
	public ArrayList<CDecimal> getUndistributedPrizes(){ return (ArrayList<CDecimal>) undistributedPrizes; }
}
