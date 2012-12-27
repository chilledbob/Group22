package gmb.model.tip.draw;

import java.util.ArrayList;
import java.util.LinkedList;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.ReturnBox;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.EvaluationResult;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;
import org.joda.time.Duration;

@Entity
public class DailyLottoDraw extends Draw 
{


	@ManyToOne
	protected TipManagement tipManagementId;

	@Deprecated
	protected DailyLottoDraw(){}

	public DailyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
		this.tipManagementId = Lottery.getInstance().getTipManagement();
	}

	public boolean evaluate(int[] result) 
	{
		//generate random result if no result has been set:
		if(this.result == null && result == null)
		{
			result = new int[10];
			
			for(int i = 0; i < 10; ++i)
				result[i] = (int)(Math.random() * 100000) % 10;
		}
		
		assert this.result != null || result.length == 10 : "Wrong result length (!=10) given to DailyLottoDraw.evaluate(int[] result)!";
		
		drawEvaluationResult = GmbFactory.new_EvaluationResult(10);

		super.evaluate(result);//init prizePotential 

		//WinnersDue not used for this evaluation:
		drawEvaluationResult.getReceiptsDistributionResult().addWinnersDueToTreasuryDue();

		ArrayList<CDecimal> prizeCatagories = Lottery.getInstance().getFinancialManagement().getPrizeCategories().getDailyLottoCategories();

		//array which will store the SingleTips for each prize category in lists:
		ArrayList<LinkedList<SingleTip>> category = ArrayListFac.new_SingleTipLinkedListArray(10);

		//put SingleTips in the prize category array:
		for(SingleTip tip : allSingleTips)
		{
			int hitCount = -1;

			for(int i = 0; i < 10; ++i)
			{
				if(tip.getTip()[i] == this.result[i])
					++hitCount;
				else
					break;
			}

			if(hitCount > -1)//win!
			{
				category.get(9 - hitCount).add(tip);
				Winnings newWinnings = GmbFactory.new_Winnings(tip, prizeCatagories.get(9 - hitCount), (9 - hitCount) + 1);
				drawEvaluationResult.addWinnings(newWinnings);

				if(tip.getGroupTip() != null)
				{
					//add group associated winnings to list in group:
					tip.getGroupTip().addWinnings(newWinnings);
				}
				else
				{
					//directly send all other winnings to their respective customers:
					newWinnings.init();		
				}
			}
		}

		drawEvaluationResult.setTipsInCategory(category);

		//evaluate average winnings for all contributers of a group tip and send them the respective winnings:
		for(GroupTip tip : groupTips)
		{
			tip.finalizeWinnings(drawEvaluationResult);
		}

		Lottery.getInstance().getFinancialManagement().getLotteryCredits().update(drawEvaluationResult.getReceiptsDistributionResult());

		DB_UPDATE(); 

		return false;
	}

	/**
	 * [intended for direct usage by controller]
	 * Sets the drawn results for this draw type. 
	 * Has to be done before evaluation.
	 * @param result
	 */
	public void setResult(int[] result)
	{ 
		assert result.length == 10 : "Wrong result length (!=10) given to DailyLottoDraw.setResult(int[] result)!";
		this.result = result; 

		DB_UPDATE(); 
	}

	/**
	 * [intended for direct usage by controller]
	 * Returns true if there is still time to submit tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForSubmission()
	{
		return Lottery.getInstance().getTimer().getDateTime().isBefore((new DateTime(planedEvaluationDate)).minusHours(24));
	}

	public boolean addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, DailyLottoGroupTip.class); }

	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, DailyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, DailyLottoGroupTip.class); }


	/**
	 * Return Code:
	 * 0 - successful
	 *-2 - not enough time left until the planned evaluation of the draw
	 *-1 - the duration of the "PermaTT" has expired
	 * 1 - the "SingleTT" is already associated with another "SingleTip"
	 * [2 - the list of the "PermaTT" already contains the "tip"]
	 */
	public ReturnBox<Integer, SingleTip> createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof DailyLottoTT : "Wrong TipTicket type given to DailyLottoDraw.createAndSubmitSingleTip()! Expected DailyLottoTT!";

	return super.createAndSubmitSingleTip(ticket, tipTip);	
	}

	protected SingleTip createSingleTipSimple(TipTicket ticket)
	{
		return new DailyLottoTip((DailyLottoTT)ticket, this);
	}

	protected SingleTip createSingleTipPersistent(TipTicket ticket)
	{
		return GmbFactory.new_DailyLottoTip((DailyLottoTT)ticket, this);
	}
}
