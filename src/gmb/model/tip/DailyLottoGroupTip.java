package gmb.model.tip;

import gmb.model.user.Group;

public class DailyLottoGroupTip extends GroupTip 
{
	@Deprecated
	protected DailyLottoGroupTip(){}

	public DailyLottoGroupTip(Draw draw, Group group, int minimumStake, int overallMinimumStake)
	{
		super(draw, group, minimumStake, overallMinimumStake);
	}
	
	public int withdraw()
	{
		int result = super.withdraw();	
		if(result != 0) return result;
		
		if(group.getDailyLottoGroupTips().remove(this))
			return 0;
		else
			return 4;
	}
}
