package gmb.model.financial.container;

import java.util.ArrayList;
import java.util.List;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.PersiObject;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;

/**
 * Container class storing the jackpots of the WeeklyLotto and Toto.
 *
 */
@Entity
public class Jackpots extends PersiObject
{
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="weeklyLottoJackpot",precision = 10, scale = 2))
	protected List<CDecimal> weeklyLottoJackpot;
	@ElementCollection
	@AttributeOverride(name="myAmount", column= @Column(name="totoJackpot",precision = 10, scale = 2))
	protected List<CDecimal> totoJackpot;
	
	@Deprecated
	protected Jackpots(){}
	
	public Jackpots(Object dummy)
	{
		weeklyLottoJackpot = ArrayListFac.new_CDecimalArray(8);
		totoJackpot = ArrayListFac.new_CDecimalArray(5);
	}
	
	public void setWeeklyLottoJackpot(List<CDecimal> weeklyLottoJackpot){ this.weeklyLottoJackpot = weeklyLottoJackpot; DB_UPDATE(); }
	public void setTotoJackpot(List<CDecimal> totoJackpot){ this.totoJackpot = totoJackpot; DB_UPDATE(); }	
	
	public ArrayList<CDecimal> getWeeklyLottoJackpot(){ return new ArrayList<CDecimal>(weeklyLottoJackpot); }
	public ArrayList<CDecimal> getTotoJackpot(){ return new ArrayList<CDecimal>(totoJackpot); }
}
