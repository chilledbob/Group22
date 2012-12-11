package gmb.model.tip.tip.group;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.tip.draw.Draw;

@Entity
public class TotoGroupTip extends GroupTip 
{
	@Deprecated
	protected TotoGroupTip(){}

	public TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
		group.addGroupTip(this);
	}
	
	/**
	 * tries to withdraw the "GroupTip" and removes all existing references in the system.
	 * returns 0 if successful.
	 */
	public int withdraw()
	{
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.removeGroupTip(this))
			return 0;
		else
			return 4;
	}
}
