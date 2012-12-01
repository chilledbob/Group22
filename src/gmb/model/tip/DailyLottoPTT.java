package gmb.model.tip;

import gmb.model.user.Customer;

public class DailyLottoPTT extends PermaTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected DailyLottoPTT(){}

	public DailyLottoPTT(Customer owner, PTTDuration duration)
	{
		 super(owner, duration);
	}

	@Override
	public boolean addTip(SingleTip tip){ return super.addTip(tip, DailyLottoTip.class); }
}
