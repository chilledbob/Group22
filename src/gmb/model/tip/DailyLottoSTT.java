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

	public void setTip(SingleTip tip){ super.setTip(tip, DailyLottoTip.class);}
	public boolean addTip(SingleTip tip){ super.setTip(tip, DailyLottoTip.class); return true; }
}
