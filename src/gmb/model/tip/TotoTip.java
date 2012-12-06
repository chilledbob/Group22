package gmb.model.tip;

import javax.persistence.Embeddable;

@Embeddable
public class TotoTip extends SingleTip 
{
	@Deprecated
	protected TotoTip(){}

	public TotoTip(TotoSTT tipTicket, TotoEvaluation eval, int[] tip)
	{
		super(tipTicket, eval, tip);

		assert eval.getResult().length == tip.length : "Wrong number of tips given to TotoTip!";
	}
	
	public TotoTip(TotoSTT tipTicket, GroupTip groupTip, int[] tip)
	{
		super(tipTicket, groupTip, tip);

		assert ((TotoEvaluation)groupTip.getDraw()).getResult().length == tip.length : "Wrong number of tips given to TotoTip!";
	}
	
	public boolean setTip(int[] tip)
	{ 	
		if(!draw.isTimeLeftUntilEvaluation()) return false;
		
		return super.setTip(tip);
	}
}
