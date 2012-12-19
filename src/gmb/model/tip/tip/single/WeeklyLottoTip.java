package gmb.model.tip.tip.single;

import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.tip.group.GroupTip;
import gmb.model.tip.tipticket.type.WeeklyLottoTT;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class WeeklyLottoTip extends SingleTip 
{
	protected int superNumber;
	
	
	@Deprecated
	protected WeeklyLottoTip(){}

	public WeeklyLottoTip(WeeklyLottoTT tipTicket, WeeklyLottoDraw draw)
	{
		super(tipTicket, draw);
		superNumber = -1;
	}
	
	public WeeklyLottoTip(WeeklyLottoTT tipTicket, GroupTip groupTip)
	{
		super(tipTicket, groupTip);
		superNumber = -1;
	}
	
	/**
	 * return code:
	 * 3 - a tipped number is smaller than 1 oder greater than 49
	 * 4 - the same number has been tipped multiple times
	 */
	public int validateTip(int[] tip)
	{
		assert tip.length == 6 : "Wrong tip length (!=6) given to DailyLottoTip.setTip(int[] tip)!";
		
		for(int i = 0; i < 6; ++i)
		{
			if(tip[i] < 1 || tip[i] > 49)
				return 3;
			
			for(int j = 0; j < 6; ++j)
			if(i != j && tip[i] == tip[j])
				return 4;
		}
		
		return super.validateTip(tip);
	}
	
	/**
	 * This method is supposed to be used by WeeklyLottoDraw while evaluating the draw only!
	 * (or in test cases)
	 * @param superNumber
	 */
	public void setSuperNumber(int superNumber){ this.superNumber = superNumber; DB_UPDATE(); }	
	public int getSuperNumber(){ return superNumber; }
}
