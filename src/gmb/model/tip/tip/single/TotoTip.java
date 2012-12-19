package gmb.model.tip.tip.single;

import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.single.TotoSTT;

import javax.persistence.Embeddable;
import javax.persistence.Entity;

@Entity
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
	}
	
	public int validateTip(int[] tip)
	{ 
		assert tip.length == 9 : "Wrong tip length (!=9) given to TotoTip.setTip(int[] tip)!";
		
		for(int i = 0; i < 9; ++i)
		{
			if(tip[i] < 0 || tip[i] > 2)
				return 3;
		}
		
		return super.validateTip(tip);
	}
}
