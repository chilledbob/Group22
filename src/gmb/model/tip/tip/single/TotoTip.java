package gmb.model.tip.tip.single;

import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.single.TotoSTT;

import javax.persistence.Entity;

/** 
 * A SingleTip for the daily football-toto evaluation.
 */
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
	
	/**
	 * [Intended for direct usage by controller][check-method]<br>
	 * Checks whether "tip" would be a valid result to be tipped.
	 * @return return code:<br>
	 * <ul>
	 * <li> 0 - successful
	 * <li>-2 - not enough time left until the planned evaluation of the draw
	 * <li> 3 - a tipped number is smaller than 0 oder greater than 2
	 * </ul>
	 */
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
