package gmb.model.tip;

import gmb.model.user.Customer;


public class TotoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected TotoSTT(){}

	public TotoSTT(Customer owner)
	{
		super(owner);
	}

	public void setTip(SingleTip tip){ super.setTip(tip, TotoTip.class); }
	public boolean addTip(SingleTip tip){ super.setTip(tip, TotoTip.class); return true; }
}