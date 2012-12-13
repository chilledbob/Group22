package gmb.model.tip.draw;

import gmb.model.Lottery;
import gmb.model.tip.TipManagement;
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.single.DailyLottoTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.type.DailyLottoTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;

@Entity
public class DailyLottoDraw extends Draw 
{
	protected int[] result;

	@ManyToOne
	protected TipManagement tipManagementId;

	@Deprecated
	protected DailyLottoDraw(){}

	public DailyLottoDraw(DateTime planedEvaluationDate)
	{
		super(planedEvaluationDate);
		Lottery.getInstance().getTipManagement().addDraw(this);
	}

	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate and init prizePotential 

		//		prizePotential = prizePotential.add(Lottery.getInstance().getFinancialManagement().getDailyLottoPrize());
		//		prizePotential = Lottery.getInstance().getFinancialManagement().distributeDrawReceipts(prizePotential);
		//
		//		//////////////////////////CALCULATE THE WINNINGS HERE THEN REMOVE THE FOLLOWING CODE
		//		for(SingleTip tip : singleTips)
		//			tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");
		//
		//		for(GroupTip groupTip : groupTips)
		//			for(SingleTip tip :  groupTip.getTips())
		//				tip.getTipTicket().getOwner().addNotification("Sadly there is no evaluation code for the drawings so you never really had a chance to win something.");
		//
		//		Lottery.getInstance().getFinancialManagement().setDailyLottoPrize(prizePotential);//everything for the lottery!
		//		//////////////////////////

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
		return isTimeLeftUntilEvaluationForChanges();
	}
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, DailyLottoGroupTip.class); }

	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, DailyLottoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, DailyLottoGroupTip.class); }

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
		assert ticket instanceof DailyLottoTT : "Wrong TipTicket type given to DailyLottoDraw.createAndSubmitSingleTip()! Expected DailyLottoTT!";

		DailyLottoTip tip = new DailyLottoTip((DailyLottoTT)ticket, this);

		if(!this.addTip(tip)) return -2;
		
		int result1 = tip.setTip(tipTip);
		if(result1 != 0) return result1;	

		int result2 = ticket.addTip(tip);
		if(result2 != 0) return result2;

		return 0;	
	}
}
