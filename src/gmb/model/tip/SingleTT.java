package gmb.model.tip;

import javax.persistence.Entity;

import gmb.model.user.Customer;

@Entity
public abstract class SingleTT extends TipTicket
{	
	protected SingleTip tip = null;

	@Deprecated
	protected SingleTT(){}
	
	public SingleTT(Customer owner)
	{
		super(owner);
	}
	
	public boolean removeTip(SingleTip tip)
	{
		if(this.tip.equals(tip))
		{
			this.tip = null;
			return true;
		}
		else
		return false;
	}
	
	//GET METHODS
	public SingleTip getTip(){ return tip; }
	
	//SET METHODS
	public void setTip(SingleTip tip){ this.tip = tip; }
	
	protected void setTip(SingleTip tip, Class<?> tipType)
	{ 
		assert tip.getClass() == tipType : "Wrong type given to SingleTT.setTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		setTip(tip);
	}
}
