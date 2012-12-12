package gmb.model.tip.tip.single;

import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.single.TotoSTT;

import javax.persistence.Embeddable;

@Embeddable
public class TotoTip extends SingleTip 
{
	@Deprecated
	protected TotoTip(){}

	public TotoTip(TotoSTT tipTicket, TotoEvaluation eval)
	{
		super(tipTicket, eval);
	}
	
	public TotoTip(TotoSTT tipTicket, GroupTip groupTip)
	{
		super(tipTicket, groupTip);

		assert ((TotoEvaluation)groupTip.getDraw()).getResult().length == tip.length : "Wrong number of tips given to TotoTip!";
	}
	
//	public int setTip(int[] tip)
//	{ 			
//		return super.setTip(tip);
//	}
}
