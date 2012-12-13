package gmb.model.tip.draw;

import gmb.model.Lottery;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.FootballGameResult;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import org.joda.time.DateTime;


@Entity
public class TotoEvaluation extends Draw 
{
	protected FootballGameResult[] results;
	
	@ManyToOne
	protected TipManagement tipManagementId;
	
	@Deprecated
	protected TotoEvaluation(){}

	public TotoEvaluation(DateTime planedEvaluationDate, FootballGameResult[] results)
	{
		super(planedEvaluationDate);
		this.results = results;
		Lottery.getInstance().getTipManagement().addDraw(this);
	}
	
	public boolean evaluate() 
	{
		super.evaluate();//set actualEvaluationDate and init prizePotential 

//		prizePotential = prizePotential.add(Lottery.getInstance().getFinancialManagement().getTotoPrize());
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
//		Lottery.getInstance().getFinancialManagement().setTotoPrize(prizePotential);//everything for the lottery!
//		//////////////////////////

		DB_UPDATE(); 
		
		return false;
	}
	
	public void setResult(FootballGameResult[] results){ this.results = results; DB_UPDATE(); }
	
	/**
	 * [intended for direct usage by controller]
	 * Returns true if there is still time to submit tips, otherwise false.
	 * @return
	 */
	public boolean isTimeLeftUntilEvaluationForSubmission()
	{
		return isTimeLeftUntilEvaluationForChanges();
	}
	
	public boolean addTip(SingleTip tip){ return super.addTip(tip, TotoTip.class); }
	public boolean addTip(GroupTip tip){ return super.addTip(tip, TotoGroupTip.class); }
	
	public boolean removeTip(SingleTip tip){ return super.removeTip(tip, TotoTip.class); }
	public boolean removeTip(GroupTip tip){ return super.removeTip(tip, TotoGroupTip.class); }
	
	public FootballGameResult[] getFootballGameResults(){ return results; }
	
	public int[] getResult()
	{ 
		int[] goals = new int[results.length * 2];
		
		for(int i = 0; i < results.length; ++i)
		{
			goals[i*2]   = results[i].getHomeResult().getScore();
			goals[i*2+1] = results[i].getVisitorResult().getScore();
		}
		
		return goals; 
	}
	
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
		assert ticket instanceof WeeklyLottoSTT : "Wrong TipTicket type given to TotoEvaluation.createAndSubmitSingleTip()! Expected TotoSTT!";
		
		TotoTip tip = new TotoTip((TotoSTT)ticket, this);

		if(!this.addTip(tip)) return -2;
		
		int result1 = tip.setTip(tipTip);
		if(result1 != 0) return result1;	

		int result2 = ticket.addTip(tip);
		if(result2 != 0) return result2;

		return 0;	
	}
}
