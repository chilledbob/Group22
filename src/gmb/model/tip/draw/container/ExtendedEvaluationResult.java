package gmb.model.tip.draw.container;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;


@Entity
public class ExtendedEvaluationResult extends EvaluationResult
{
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="jackpotImageBefore"))
	protected List<CDecimal> jackpotImageBefore;
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="jackpotImageAfter"))
	protected List<CDecimal> jackpotImageAfter;
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="undistributedPrizes"))
	protected List<CDecimal> undistributedPrizes;

	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="perCategoryPrizePotential"))
	protected List<CDecimal> perCategoryPrizePotential;
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="perCategoryWinningsUnMerged"))
	protected List<CDecimal> perCategoryWinningsUnMerged;
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="perCatagoryWinningsMerged"))
	protected List<CDecimal> perCategoryWinningsMerged;

	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="normalizationAmount"))
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
