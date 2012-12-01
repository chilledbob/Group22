package gmb.model.tip;

import gmb.model.user.Customer;

public class WeeklyLottoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected WeeklyLottoSTT(){}

	public WeeklyLottoSTT(Customer owner)
	{
		super(owner);
	}

	@Override
	public void setTip(SingleTip tip){ super.setTip(tip, WeeklyLottoTip.class);}
}