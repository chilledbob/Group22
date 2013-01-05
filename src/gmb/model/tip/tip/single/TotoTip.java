package gmb.model.tip.tip.single;

import java.util.ArrayList;

import gmb.model.member.Customer;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.TipTicket;
import gmb.model.tip.tipticket.single.TotoSTT;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.eclipse.persistence.annotations.JoinFetch;
import org.eclipse.persistence.annotations.JoinFetchType;

/** 
 * A SingleTip for the daily football-toto evaluation.
 */
@Entity
public class TotoTip extends SingleTip 
{
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="PERSISTENCEID")
	protected TotoSTT tipTicket;
	
	
	@Deprecated
	protected TotoTip(){}

	public TotoTip(TotoSTT tipTicket, TotoEvaluation eval)
	{
		super(tipTicket, eval);
		this.tipTicket = tipTicket;
	}
	
	public TotoTip(TotoSTT tipTicket, GroupTip groupTip)
	{
		super(tipTicket, groupTip);
		this.tipTicket = tipTicket;
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
	
	public TipTicket getTipTicket(){ return tipTicket; }
	public Customer getOwner(){return tipTicket.getOwner(); }
}
