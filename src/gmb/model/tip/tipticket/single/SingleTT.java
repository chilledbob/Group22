package gmb.model.tip.tipticket.single;

import gmb.model.CDecimal;

import gmb.model.tip.tip.single.SingleTip;
import gmb.model.tip.tipticket.TipTicket;

import javax.persistence.Entity;

@Entity
public abstract class SingleTT extends TipTicket
{	
	protected SingleTip tip = null;

//	@Deprecated
//	protected SingleTT(){}
	
	public SingleTT()
	{
		super();
	}
	
	public boolean removeTip(SingleTip tip)
	{
		if(this.tip.equals(tip))
		{
			this.tip = null;
			DB_UPDATE(); 
			
			return true;
		}
		else
		return false;
	}
	
	public SingleTip getTip(){ return tip; }
	
	protected int addTip(SingleTip tip, Class<?> tipType)
	{ 
		assert tip.getClass() == tipType : "Wrong type given to SingleTT.setTip(SingleTip tip)! Expected: " + tipType.getSimpleName() + " !";
		
		if(tip != null)
		{
			this.tip = tip;
			DB_UPDATE(); 
			
			return 0;
		}
		else
		return 1;
	}
	
	public CDecimal getPricePerTicket(){ return getPrice(); }
}
