package gmb.model.tip;

import javax.persistence.Entity;

import gmb.model.user.Customer;

@Entity
public class DailyLottoSTT extends SingleTT 
{
	
	@Deprecated
	protected DailyLottoSTT(){}

	public DailyLottoSTT(Customer owner)
	{
		super(owner);
	}

	@Override
	public void setTip(SingleTip tip){ super.setTip(tip, DailyLottoTip.class);}
}
