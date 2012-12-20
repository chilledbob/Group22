package gmb.model.tip.draw.container;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;

/**
 * Container class extending {@link EvaluationResult} with more data.<br>
 * Created while evaluating a WeeklyLottoDraw or a TotoEvaluation.
 *
 */
@Entity
public class ExtendedEvaluationResult extends EvaluationResult
{
	protected List<CDecimal> jackpotImageBefore;
	protected List<CDecimal> jackpotImageAfter;
	protected List<CDecimal> undistributedPrizes;

	protected List<CDecimal> perCategoryPrizePotential;
	protected List<CDecimal> perCategoryWinningsUnMerged;
	protected List<CDecimal> perCategoryWinningsMerged;

	protected CDecimal normalizationAmount;

	@Deprecated
	protected ExtendedEvaluationResult(){}

	public ExtendedEvaluationResult(int categoryCount)
	{
		super(categoryCount);

		perCategoryPrizePotential = ArrayListFac.new_CDecimalArray(categoryCount);
		perCategoryWinningsUnMerged = ArrayListFac.new_CDecimalArray(categoryCount);
		perCategoryWinningsMerged = ArrayListFac.new_CDecimalArray(categoryCount);
		tipsInCategory = ArrayListFac.new_SingleTipLinkedListArray(categoryCount);
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

	public void createCategoryPrizePotential(ArrayList<CDecimal> perCategoryPrizePotential)
	{ 
		this.perCategoryPrizePotential = createDeepCopyForCDecimalArray(perCategoryPrizePotential); 
		DB_UPDATE(); 
	}

	public void createCategoryWinningsUnMerged(ArrayList<CDecimal> perCategoryPrizePotentialUnMerged)
	{ 
		this.perCategoryWinningsUnMerged = createDeepCopyForCDecimalArray(perCategoryPrizePotentialUnMerged); 
		DB_UPDATE(); 
	}

	public void createCategoryWinningsMerged(ArrayList<CDecimal> perCategoryPrizePotentialMerged)
	{ 
		this.perCategoryWinningsMerged = createDeepCopyForCDecimalArray(perCategoryPrizePotentialMerged); 
		DB_UPDATE(); 
	}

	public void setNormalizationAmount(CDecimal normalizationAmount){ this.normalizationAmount = normalizationAmount; DB_UPDATE(); }

	public CDecimal getNormalizationAmount(){ return normalizationAmount; }

	public ArrayList<CDecimal> getCategoryPrizePotential(){ return (ArrayList<CDecimal>) perCategoryPrizePotential; }
	public ArrayList<CDecimal> getCategoryWinningsUnMerged(){ return (ArrayList<CDecimal>) perCategoryWinningsUnMerged; }
	public ArrayList<CDecimal> getCategoryWinningsMerged(){ return (ArrayList<CDecimal>) perCategoryWinningsMerged; }

	public ArrayList<CDecimal> getJackpotImageBefore(){ return (ArrayList<CDecimal>) jackpotImageBefore; }
	public ArrayList<CDecimal> getJackpotImageAfter(){ return (ArrayList<CDecimal>) jackpotImageAfter; }
	public ArrayList<CDecimal> getUndistributedPrizes(){ return (ArrayList<CDecimal>) undistributedPrizes; }
}
