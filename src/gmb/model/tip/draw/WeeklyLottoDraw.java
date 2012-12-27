package gmb.model.tip.draw;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.ReturnBox;

import java.util.ArrayList;
import java.util.LinkedList;

import gmb.model.Lottery;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.ExtendedEvaluationResult;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import javax.persistence.*;

@Entity
public class WeeklyLottoDraw extends Draw
{
	@ManyToOne
	protected TipManagement tipManagementId;

	protected static final int categoryCount = 8;
	
	@Deprecated
	protected WeeklyLottoDraw(){}

	public WeeklyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
		this.tipManagementId = Lottery.getInstance().getTipManagement();
	}

	/**
	 * [intended for direct usage by controller]
	 * Evaluates the "Draw" with all implications (creating and sending "Winnings", updating the "Jackpot", updating the "LotteryCredits",...).
	 * @return
	 */
	public boolean evaluate(int[] result) 
	{
		assert result.length == 8 : "Wrong result length (!=8) given to WeeklyLottoDraw.evaluate(int[] result)! (6 + extraNumber + superNumber)";

		drawEvaluationResult = GmbFactory.new_WeeklyLottoDrawEvaluationResult(categoryCount);
		
		super.evaluate(result);//init prizePotential 
		
		ArrayList<CDecimal> jackpot = Lottery.getInstance().getFinancialManagement().getJackpots().getWeeklyLottoJackpot();

		((ExtendedEvaluationResult) drawEvaluationResult).createJackpotImageBefore(jackpot);

		//calculate the overall amount of money to be processed (must stay the same):
		CDecimal mustOverallAmount = prizePotential;
		for(CDecimal categoryJackpot : jackpot)
			mustOverallAmount = mustOverallAmount.add(categoryJackpot);

		//calculate the prize potential per prize category:
		ArrayList<CDecimal> prizeCatagories = Lottery.getInstance().getFinancialManagement().getPrizeCategories().getWeeklyLottoCategories();

		ArrayList<CDecimal> perCategoryPrizePotential = ArrayListFac.new_CDecimalArray(categoryCount); 

		for(int i = 0; i < categoryCount; ++i)
			perCategoryPrizePotential.set(i, prizePotential.multiply(prizeCatagories.get(i)).divide(dec100).add(jackpot.get(i)));

		((ExtendedEvaluationResult) drawEvaluationResult).createCategoryPrizePotential(perCategoryPrizePotential);

		//array which will store the SingleTips for each prize category in lists:
		ArrayList<LinkedList<SingleTip>> category = ArrayListFac.new_SingleTipLinkedListArray(categoryCount);

		//put SingleTips in the prize category array:
		for(SingleTip tip : allSingleTips)
		{
			//tip specific random superNumber 0-9:
			int superNumber = (int)(Math.random() * 100000) % 10;//not nicely generated, I know

			if(((WeeklyLottoTip)tip).getSuperNumber() != -1)//only use random number if a test hasn't set a specific one
				superNumber = ((WeeklyLottoTip)tip).getSuperNumber();
			else
				((WeeklyLottoTip)tip).setSuperNumber(superNumber);

			boolean[] hits = new boolean[]{false,false,false,false,false,false};
			int hitCount = 0;

			for(int i = 0; i < 6; ++i)
				for(int j = 0; j < 6; ++j)
					if(tip.getTip()[i] == this.result[j])
					{
						hits[i] = true;
						++hitCount;
					}

			if(hitCount == 6)//*drum roll...
			{
				if(superNumber == this.result[7])
					category.get(0).add(tip);//awesome! 8D
				else
					category.get(1).add(tip);//nearly awesome! xD
			}
			else
			{
				if(hitCount > 2)//still nice! :)
				{
					//check non-hits for extraNumber hit:
					int extraNumberHit = 0;

					for(int i = 0; i < 6; ++i)
						if(hits[i] == false)
							if(tip.getTip()[i] == this.result[6])
							{
								extraNumberHit = 1;//nicer! :D
								break;
							}

					int categoryID = 7 - ((hitCount-3)*2 + extraNumberHit);
					category.get(categoryID).add(tip);
				}
			}
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

		//no winner in second highest winning category but in the highest? -> if so add the prize potential to the highest:
		if(tipCountPerCategory.get(1).signum() == 0 && tipCountPerCategory.get(0).signum() > 0)
		{
			perCategoryPrizePotential.set(0, perCategoryPrizePotential.get(0).add(perCategoryPrizePotential.get(1)));
			categoryWinnings.set(0, perCategoryPrizePotential.get(0).divide(tipCountPerCategory.get(0)));

			perCategoryPrizePotential.set(1, new CDecimal(0));
			newJackpot.set(1, new CDecimal(0));
		}

		Lottery.getInstance().getFinancialManagement().getJackpots().setWeeklyLottoJackpot(newJackpot);//set new jackpot
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

		return true;
	}

	/**
	 * [intended for direct usage by controller]
	 * Sets the drawn results for this draw type. 
	 * Has to be done before evaluation.
	 * @param result
	 */
//	public void setResult(int[] result)
//	{ 
//		assert result.length == 8 : "Wrong result length (!=8) given to WeeklyLottoDraw.setResult(int[] result)! (6 + extraNumber + superNumber)";
//		this.result = result; 
//		DB_UPDATE(); 
//	}

	/**
	 * [intended for direct usage by controller]
	 * Returns true if there is still time to submit tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForSubmission()
	{
		DateTime peDate = new DateTime(planedEvaluationDate);
		DateTime endDate = new DateTime(peDate.getYear(), peDate.getMonthOfYear(), peDate.getDayOfMonth(), 0, 0, 0);//reset hours, minutes, seconds

		Duration duration = new Duration(Lottery.getInstance().getTimer().getDateTime(), endDate);
		return duration.isLongerThan(new Duration(0));	
	}

	public boolean addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, WeeklyLottoGroupTip.class); }

	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, WeeklyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, WeeklyLottoGroupTip.class); }


	/**
	 * [intended for direct usage by controller]
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 * 3 - a tipped number is smaller than 1 oder greater than 49
	 * 4 - the same number has been tipped multiple times
	 */
	public ReturnBox<Integer, SingleTip> createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof WeeklyLottoTT : "Wrong TipTicket type given to WeeklyLottoDraw.createAndSubmitSingleTip()! Expected WeeklyLottoTT!";

	return super.createAndSubmitSingleTip(ticket, tipTip);		
	}
	
	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new WeeklyLottoTip((WeeklyLottoTT)ticket, this);
	}
	
	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_WeeklyLottoTip((WeeklyLottoTT)ticket, this);
	}
}
