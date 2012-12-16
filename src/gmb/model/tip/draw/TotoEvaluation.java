package gmb.model.tip.draw;

import java.util.ArrayList;
import java.util.List;

import gmb.model.GmbFactory;
import gmb.model.ReturnBox;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tip.single.TotoTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;


@Entity
public class TotoEvaluation extends Draw 
{
	@OneToMany(mappedBy="totoEvaluation")
	protected List<FootballGameData> games;
	
	@ManyToOne
	protected TipManagement tipManagementId;
	
	@Deprecated
	protected TotoEvaluation(){}

	public TotoEvaluation(DateTime planedEvaluationDate, ArrayList<FootballGameData> games)
	{
		super(planedEvaluationDate);
		this.games = games;
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
	
	public void setGames(ArrayList<FootballGameData> games){ this.games = games; DB_UPDATE(); }
	
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
	
	public List<FootballGameData> getGames(){ return games; }
	
	public int[] getResult()
	{ 
		int[] goals = new int[games.size() * 2];
		
//		for(int i = 0; i < results.size(); ++i)
//		{
//			goals[i*2]   = results.get(i).getHomeResult().getScore();
//			goals[i*2+1] = results.get(i).getVisitorResult().getScore();
//		}
		
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
	public ReturnBox<Integer, SingleTip> createAndSubmitSingleTip(TipTicket ticket, int[] tipTip) 
	{
		assert ticket instanceof WeeklyLottoSTT : "Wrong TipTicket type given to TotoEvaluation.createAndSubmitSingleTip()! Expected TotoSTT!";
		
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
