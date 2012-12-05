package gmb.model.tip;

import javax.persistence.Entity;

import gmb.model.user.Customer;

@Entity
public class TotoSTT extends SingleTT 
{
	//CONSTRUCTORS
	@Deprecated
	protected TotoSTT(){}

	public TotoSTT(Customer owner)
	{
		super(owner);
	}

	@Override
	public void setTip(SingleTip tip){ super.setTip(tip, TotoTip.class);}
}