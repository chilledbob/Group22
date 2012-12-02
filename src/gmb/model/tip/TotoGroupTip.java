package gmb.model.tip;

import gmb.model.user.Group;

public class TotoGroupTip extends GroupTip 
{
	@Deprecated
	protected TotoGroupTip(){}

	public TotoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
	}
	
	/**
	 * tries to withdraw the "GroupTip" and removes all existing references in the system.
	 * returns 0 if successful.
	 */
	public int withdraw()
	{
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.getTotoGroupTips().remove(this))
			return 0;
		else
			return 4;
	}	
}
