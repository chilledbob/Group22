package gmb.model.tip;

import gmb.model.user.Customer;

public class WeeklyLottoPTT extends PermaTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected WeeklyLottoPTT(){}

	public WeeklyLottoPTT(Customer owner, PTTDuration duration)
	{
		super(owner, duration);
	}

	@Override
	public boolean addTip(SingleTip tip){ return super.addTip(tip, WeeklyLottoTip.class); }
}
