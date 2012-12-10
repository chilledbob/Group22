package gmb.model.tip.draw;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import gmb.model.Lottery;
import gmb.model.financial.container.ReceiptsDistribution;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.TipManagement;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class WeeklyLottoDraw extends Draw
{
	protected int[] result = null;

	protected static final BigDecimal dec100 = new BigDecimal("100");
	protected static final BigDecimal dec2 = new BigDecimal("2");

	@ManyToOne
	protected TipManagement tipManagementId;

	@Deprecated
	protected WeeklyLottoDraw(){}

	public WeeklyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}

	@SuppressWarnings("unchecked")
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate and init prizePotential 

		BigDecimal[] jackpot = Lottery.getInstance().getFinancialManagement().getJackpots().getWeeklyLottoJackpot();
		
		drawEvaluationResult.createJackpotImageBefore(jackpot);

		//calculate the overall amount of money to be processed (must stay the same):
		BigDecimal mustOverallAmount = prizePotential;
		for(BigDecimal categoryJackpot : jackpot)
			mustOverallAmount = mustOverallAmount.add(categoryJackpot);

		//calculate the prize potential per prize category:
		BigDecimal[] prizeCatagories = Lottery.getInstance().getFinancialManagement().getPrizeCategories().getWeeklyLottoCategories();

		BigDecimal[] perCategoryPrizePotential = new BigDecimal[8]; 

		for(int i = 0; i < 8; ++i)
			perCategoryPrizePotential[i] = prizePotential.multiply(prizeCatagories[i]).divide(dec100).add(jackpot[i]);

		drawEvaluationResult.copyCategoryPrizePotential(perCategoryPrizePotential);

		//array which will store the SingleTips for each prize category in lists:
		Object[] category = new Object[8];
		
		for(int i = 0; i < category.length; ++i)
			category[i] = new LinkedList<SingleTip>();

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
					((LinkedList<SingleTip>)(category[0])).add(tip);//awesome! 8D
				else
					((LinkedList<SingleTip>)(category[1])).add(tip);//nearly awesome! xD
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
					((LinkedList<SingleTip>)(category[categoryID])).add(tip);
				}
			}
		}

		drawEvaluationResult.copyTipsInCategory(category);

		//count number of SingleTips in each category:
		BigDecimal[] tipCountPerCategory = new BigDecimal[8];
		for(int i = 0; i < 8; ++i)
			tipCountPerCategory[i] = new BigDecimal(((LinkedList<SingleTip>)(category[i])).size());

		//no winner in second highest winning category but in the highest? -> if so add the prize potential to the highest:
		if(tipCountPerCategory[1].signum() == 0 && tipCountPerCategory[0].signum() > 0)
		{
			perCategoryPrizePotential[0] = perCategoryPrizePotential[0].add(perCategoryPrizePotential[1]);
			perCategoryPrizePotential[1] = new BigDecimal(0);
		}

		//calculate the winnings for each SingleTip in each category, and build new jackpot from empty categories:
		BigDecimal[] newJackpot = new BigDecimal[8];
		BigDecimal[] categoryWinnings = new BigDecimal[8];

		for(int i = 0; i < 8; ++i)
			if(tipCountPerCategory[i].signum() > 0)
			{
				categoryWinnings[i] = perCategoryPrizePotential[i].divide(tipCountPerCategory[i]);
				newJackpot[i] = new BigDecimal(0);
			}
			else
			{
				categoryWinnings[i] = new BigDecimal(0);
				newJackpot[i] = perCategoryPrizePotential[i];
			}

		Lottery.getInstance().getFinancialManagement().getJackpots().setWeeklyLottoJackpot(newJackpot);//set new jackpot
		drawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(newJackpot);//create image of new jackpot and the difference to the old jackpot

		//merge prize categories if lower category has higher winnings per SingleTip:
		drawEvaluationResult.copyCategoryWinningsUnMerged(categoryWinnings);

		int m = 0;
		for(; m < 100; ++m)//limit loop count for the case of unpredicted rounding behavior which would lead to infinite looping
		{
			boolean noMerge = true;

			for(int i = 7; i > 0; --i)//start with lowest category
			{
				for(int j = i-1; j > 0; --j)//compare with all higher categories
				{
					if(tipCountPerCategory[i].signum() > 0 && tipCountPerCategory[j].signum() > 0)//only compare if there are actually SingleTips in both categories
						if(categoryWinnings[i].compareTo(categoryWinnings[j]) > 0)//compare if lower category has higher winnings
						{
							noMerge = false;

							BigDecimal newWinnings = (categoryWinnings[i].add(categoryWinnings[j])).divide(dec2);//average winnings
							categoryWinnings[i] = newWinnings;
							categoryWinnings[j] = newWinnings;

							break;
						}
				}

				if(!noMerge) break;//break to start with lowest category again
			}

			if(noMerge) break;//only exit if there hasn't been another merge
		}

		if(m > 15)
			System.out.println("UNEXPECTED HIGH MERGE LOOP COUNT! Loop count was: " + (new Integer(m)).toString() + " !");

		drawEvaluationResult.copyCategoryWinningsMerged(categoryWinnings);

		//create and send winnings, also calculate the actual overall amount of money that has been processed in the end:
		BigDecimal actualOverallAmount = new BigDecimal(0);

		for(int i = 0; i < 8; ++i)
		{
			actualOverallAmount = actualOverallAmount.add(newJackpot[i]);
			
			for(SingleTip tip : (LinkedList<SingleTip>)(category[i]))
			{
				Winnings newWinnings = new Winnings(tip, categoryWinnings[i], i + 1);
				tip.setOverallWinnings(newWinnings);
				
				if(tip.getGroupTip() != null)
				{
					//add group associated winnings to list in group:
					tip.getGroupTip().addWinnings(newWinnings);
				}
				else
				{
					actualOverallAmount = actualOverallAmount.add(categoryWinnings[i]);

					//directly send all other winnings to their respective customers:
					newWinnings.init();

					drawEvaluationResult.addWinnings(newWinnings);
				}				
			}
		}


		//evaluate average winnings for all contributers of a group tip and send them the respective winnings:
		for(GroupTip tip : groupTips)
			actualOverallAmount = actualOverallAmount.add(tip.finalizeWinnings(drawEvaluationResult));

		//normalize overall amount of processed money:
		drawEvaluationResult.getReceiptsDistributionResult().addToTreasuryDue(mustOverallAmount.subtract(actualOverallAmount));

		return true;
	}

	public void setResult(int[] result)
	{ 
		assert result.length == 8 : "Wrong result length (!=8) given to WeeklyLottoDraw.setResult(int[] result)! (6 + extraNumber + superNumber)";
		this.result = result; 
	}

	public boolean addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, WeeklyLottoGroupTip.class); }

	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, WeeklyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, WeeklyLottoGroupTip.class); }

	public int[] getResult(){ return result; }

	/**
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 * 3 - a tipped number is smaller than 1 oder greater than 49
	 * 4 - the same number has been tipped multiple times
	 */
	public int createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof WeeklyLottoTT : "Wrong TipTicket type given to WeeklyLottoDraw.createAndSubmitSingleTip()! Expected WeeklyLottoTT!";

	if(this.isTimeLeftUntilEvaluation())
	{
		WeeklyLottoTip tip = new WeeklyLottoTip((WeeklyLottoTT)ticket, this, tipTip);
		
		for(int i = 0; i < 6; ++i)
		{
			if(tipTip[i] < 1 || tipTip[i] > 49)
				return 3;
			
			for(int j = 0; j < 6; ++j)
			if(i != j && tipTip[i] == tipTip[j])
				return 4;
		}
		
		int result = ticket.addTip(tip);

		if(result == 0)
		{
			singleTips.add(tip);

			return 0;
		}
		else 
			return result;
	}
	else
		return -2;
	}
}
