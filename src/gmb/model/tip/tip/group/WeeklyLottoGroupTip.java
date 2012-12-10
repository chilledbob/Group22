package gmb.model.tip.tip.group;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;

@Entity
public class WeeklyLottoGroupTip extends GroupTip 
{
	@Deprecated
	protected WeeklyLottoGroupTip(){}

	public WeeklyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
		group.addWeeklyLottoGroupTip(this);
	}
	
	/**
	 * tries to withdraw the "GroupTip" and removes all existing references in the system.
	 * returns 0 if successful.
	 */
	public int withdraw()
	{
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.getWeeklyLottoGroupTips().remove(this))
			return 0;
		else
			return 4;
	}	
}
