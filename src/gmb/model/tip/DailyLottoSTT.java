package gmb.model.tip;

import gmb.model.user.Customer;

public class DailyLottoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected DailyLottoSTT(){}

	public DailyLottoSTT(Customer owner)
	{
		super(owner);
	}

	@Override
	public void setTip(SingleTip tip){ super.setTip(tip, DailyLottoTip.class);}
}
