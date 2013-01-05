package gmb.model.tip.draw;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.financial.transaction.Winnings;
import gmb.model.group.Group;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.draw.container.ExtendedEvaluationResult;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

/**
 * The class representing the implementation of the weekly football-toto evaluation.
 */
@Entity
public class TotoEvaluation extends Draw 
{
	@OneToMany(mappedBy="totoEvaluation",fetch=FetchType.EAGER)
	@JoinColumn(name="TOTOEVALUATION_PERSISTENCEID")
	protected List<FootballGameData> gameData;
	
	protected static final int categoryCount = 5;
	
	
	@Deprecated
	protected TotoEvaluation(){}

	public TotoEvaluation(DateTime planedEvaluationDate, ArrayList<FootballGameData> gameData)
	{
		super(planedEvaluationDate);
		
		assert gameData.size() == 9 : "Wrong gameData size (!=9) given to TotoEvaluation.setGameData(ArrayList<FootballGameData> gameData)!";
		this.gameData = gameData;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Evaluates the "Draw" with all implications (creating and sending "Winnings", updating the "Jackpot", updating the "LotteryCredits",...).
	 * @return false if this Draw is already evaluated, otherwise true
	 */
	public boolean evaluate(int[] result) 
	{
		if(evaluated) return false;
		evaluated = true;
		
		assert result.length == 18 : "Wrong result length (!=18) given to TotoEvaluation.evaluate(int[] result)! (9 x [homeResult, visitorResult])";
		
		//withdraw all not submitted GroupTips associated with this draw:
		for(Group group : Lottery.getInstance().getGroupManagement().getGroups())
			for(TotoGroupTip tip : group.getTotoGroupTips())
				if(!tip.isSubmitted() && tip.getDraw() == this)
					tip.withdraw();
		
		//create the actual result array containing only the general results of the games, copy exact results to gameData:
		int[] newResult = new int[9];
		for(int i = 0; i < 9; ++i)
		{
			gameData.get(i).setResults(result[i*2], result[i*2+1]);
			
			if(result[i*2] == result[i*2+1])
				newResult[i] = 0;//standoff
			else
				if(result[i*2] > result[i*2+1])
					newResult[i] = 1;//home club wins
				else
					newResult[i] = 2;//visitor club wins
		}
		result = newResult;//just to make sure the right array is used later
		
		drawEvaluationResult = GmbFactory.new_ExtendedEvaluationResult(categoryCount);
		
		super.evaluate(newResult);//set actualEvaluationDate and init prizePotential 

		ArrayList<CDecimal> jackpot = Lottery.getInstance().getFinancialManagement().getJackpots().getTotoJackpot();

		((ExtendedEvaluationResult) drawEvaluationResult).createJackpotImageBefore(jackpot);

		//calculate the overall amount of money to be processed (must stay the same):
		CDecimal mustOverallAmount = prizePotential;
		for(CDecimal categoryJackpot : jackpot)
			mustOverallAmount = mustOverallAmount.add(categoryJackpot);

		//calculate the prize potential per prize category:
		ArrayList<CDecimal> prizeCatagories = Lottery.getInstance().getFinancialManagement().getPrizeCategories().getTotoCategories();

		ArrayList<CDecimal> perCategoryPrizePotential = ArrayListFac.new_CDecimalArray(categoryCount); 

		for(int i = 0; i < categoryCount; ++i)
			perCategoryPrizePotential.set(i, prizePotential.multiply(prizeCatagories.get(i)).divide(dec100).add(jackpot.get(i)));

		((ExtendedEvaluationResult) drawEvaluationResult).createCategoryPrizePotential(perCategoryPrizePotential);

		//array which will store the SingleTips for each prize category in lists:
		ArrayList<LinkedList<SingleTip>> category = ArrayListFac.new_SingleTipLinkedListArray(categoryCount);

		//put SingleTips in the prize category array:
		for(SingleTip tip : allSingleTips)
		{
			int hitCount = -5;

			for(int i = 0; i < 9; ++i)
					if(tip.getTip()[i] == this.result[i])
						++hitCount;

			if(hitCount > -1)
			category.get(4 - hitCount).add(tip);
		}

		drawEvaluationResult.setTipsInCategory(category);

		//count number of SingleTips in each category:
		ArrayList<CDecimal> tipCountPerCategory = ArrayListFac.new_CDecimalArray(categoryCount);
		for(int i = 0; i < categoryCount; ++i)
			tipCountPerCategory.set(i, new CDecimal(category.get(i).size()));

		//calculate the winnings for each SingleTip in each category, and build new jackpot from empty categories:
		ArrayList<CDecimal> newJackpot = ArrayListFac.new_CDecimalArray(categoryCount);
		ArrayList<CDecimal> categoryWinnings = ArrayListFac.new_CDecimalArray(categoryCount);

		for(int i = 0; i < categoryCount; ++i)
			if(tipCountPerCategory.get(i).signum() > 0)
			{
				categoryWinnings.set(i, perCategoryPrizePotential.get(i).divide(tipCountPerCategory.get(i)));
				newJackpot.set(i, new CDecimal(0));
			}
			else
			{
				categoryWinnings.set(i, new CDecimal(0));
				newJackpot.set(i, perCategoryPrizePotential.get(i));
			}

		//merge prize categories if lower category has higher winnings per SingleTip:
		((ExtendedEvaluationResult) drawEvaluationResult).createCategoryWinningsUnMerged(categoryWinnings);

		int m = 0;
		for(; m < 100; ++m)//limit loop count for the case of unpredicted rounding behavior which would lead to infinite looping
		{		
			boolean noMerge = true;

			//			for(int i = 0; i < 8; ++i)
			//			{
			//				System.out.print(perCategoryPrizePotential[i]);
			//				System.out.print(" ");
			//			}
			//			System.out.println(" ");

			for(int i = categoryCount-1; i > 0; --i)//start with lowest category
			{
				for(int j = i-1; j >= 0; --j)//compare with all higher categories
				{
					if(tipCountPerCategory.get(i).signum() > 0 && tipCountPerCategory.get(j).signum() > 0)//only compare if there are actually SingleTips in both categories
						if(categoryWinnings.get(i).compareTo(categoryWinnings.get(j)) > 0)//compare if lower category has higher winnings
						{
							noMerge = false;

							CDecimal mergedPrizePotential = (perCategoryPrizePotential.get(i).add(perCategoryPrizePotential.get(j))).divide(dec2);//average winnings
							perCategoryPrizePotential.set(i, mergedPrizePotential);
							perCategoryPrizePotential.set(j, mergedPrizePotential);

							break;
						}
				}

				if(!noMerge) break;//break to start with lowest category again
			}

			if(noMerge) break;//only exit if there hasn't been another merge

			//re-calculate the winnings for each SingleTip in each category:
			for(int i = 0; i < categoryCount; ++i)
				if(tipCountPerCategory.get(i).signum() > 0)
					categoryWinnings.set(i, perCategoryPrizePotential.get(i).divide(tipCountPerCategory.get(i)));
		}

		if(m > 31)
			System.out.println("UNEXPECTED HIGH MERGE LOOP COUNT! Loop count was: " + (new Integer(m)).toString() + " !");

		Lottery.getInstance().getFinancialManagement().getJackpots().setTotoJackpot(newJackpot);//set new jackpot
		((ExtendedEvaluationResult) drawEvaluationResult).createJackpotImageAfterAndUndistributedPrizes(newJackpot);//create image of new jackpot and calculate the difference to the old jackpot

		((ExtendedEvaluationResult) drawEvaluationResult).createCategoryWinningsMerged(categoryWinnings);

		//create and send winnings, also calculate the actual overall amount of money that has been processed in the end:
		CDecimal actualOverallAmount = new CDecimal(0);

		for(int i = 0; i < categoryCount; ++i)
		{
			actualOverallAmount = actualOverallAmount.add(newJackpot.get(i));

			for(SingleTip tip : category.get(i))
			{
				Winnings newWinnings = GmbFactory.new_Winnings(tip, categoryWinnings.get(i), i + 1);
				tip.setOverallWinnings(newWinnings);
				drawEvaluationResult.addWinnings(newWinnings);
				
				if(tip.getGroupTip() != null)
				{
					//add group associated winnings to list in group:
					tip.getGroupTip().addWinnings(newWinnings);
				}
				else
				{
					actualOverallAmount = actualOverallAmount.add(categoryWinnings.get(i));

					//directly send all other winnings to their respective customers:
					newWinnings.init();
				}				
			}
		}

		//evaluate average winnings for all contributers of a group tip and send them the respective winnings:
		for(GroupTip tip : groupTips)
		{
			actualOverallAmount = actualOverallAmount.add(tip.finalizeWinnings(drawEvaluationResult));
		}

		//normalize overall amount of processed money:
		CDecimal normalizationAmount = mustOverallAmount.subtract(actualOverallAmount);

		drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(normalizationAmount);
		((ExtendedEvaluationResult) drawEvaluationResult).setNormalizationAmount(normalizationAmount);

		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(drawEvaluationResult.getReceiptsDistributionResult());
		
		DB_UPDATE(); 
		
		return false;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Returns true if there is still time to (un-)submit tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForSubmission()
	{
		return Lottery.getInstance().getTimer().getDateTime().isBefore((new DateTime(planedEvaluationDate)).minusHours(24));
	}
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, TotoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, TotoGroupTip.class); }
	
	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, TotoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, TotoGroupTip.class); }
	
	public ArrayList<FootballGameData> getGameData(){ return (ArrayList<FootballGameData>) gameData; }
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * Creates and submits a SingleTip <br>
	 * @param ticket The {@link TipTicket} required for the {@link SingleTip} creation.
	 * @param tipTip The int[] storing the tipped results.
	 * @return {@link ReturnBox} with:<br>
	 * var1 as {@link Integer}: <br>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <li>-1 - the duration of the "PermaTT" has expired
	 * <li> 1 - the "SingleTT" is already associated with another "SingleTip"
	 * <li> [2 - the list of the "PermaTT" already contains the "tip"]
	 * <li> 3 - a tipped number is smaller than 1 oder greater than 49
	 * <li> 4 - the same number has been tipped multiple times
	 * <li> 5 - the ticket is already associated with this draw
	 * </ul>
	 * var2 as {@link SingleTip}:<br>
	 * <ul>
	 * <li> var1 == 0 -> the created SingleTip
	 * <li> var1 != 0 -> null 
	 * </ul>
	 */
	public ReturnBox<Integer, SingleTip> createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof TotoSTT : "Wrong TipTicket type given to TotoEvaluation.createAndSubmitSingleTip()! Expected TotoSTT!";
		
	return super.createAndSubmitSingleTip(ticket, tipTip);		
	}
	
	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new TotoTip((TotoSTT)ticket, this);
	}
	
	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_TotoTip((TotoSTT)ticket, this);
	}
}
