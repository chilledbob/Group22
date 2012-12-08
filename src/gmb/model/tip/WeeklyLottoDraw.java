package gmb.model.tip;

import gmb.model.Lottery;

import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
public class WeeklyLottoDraw extends Draw
{
	protected int[] result = null;
	
	@ManyToOne
	protected TipManagement tipManagementId;
	
	@Deprecated
	protected WeeklyLottoDraw(){}
	
	public WeeklyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
	}
	
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate and init prizePotential 

		prizePotential = prizePotential.add(Lottery.getInstance().getFinancialManagement().getWeeklyLottoPrize());
		prizePotential = Lottery.getInstance().getFinancialManagement().distributeDrawReceipts(prizePotential);

		//////////////////////////CALCULATE THE WINNINGS HERE THEN REMOVE THE FOLLOWING CODE
		for(SingleTip tip : singleTips)
			tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");

		for(GroupTip groupTip : groupTips)
			for(SingleTip tip :  groupTip.getTips())
				tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");
		
		Lottery.getInstance().getFinancialManagement().setWeeklyLottoPrize(prizePotential);//everything for the lottery!
		//////////////////////////

		return false;
	}
	
	public void setResult(int[] result)
	{ 
		assert result.length == 7 : "Wrong result length (!=7) given to WeeklyLottoDraw.setResult(int[] result)!";
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
	 */
	public int createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof WeeklyLottoTT : "Wrong TipTicket type given to WeeklyLottoDraw.createAndSubmitSingleTip()! Expected WeeklyLottoTT!";
		
		if(this.isTimeLeftUntilEvaluation())
		{
			WeeklyLottoTip tip = new WeeklyLottoTip((WeeklyLottoTT)ticket, this, tipTip);
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
