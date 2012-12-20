package gmb.model.financial.container;

import java.util.ArrayList;
import java.util.List;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class Jackpots extends PersiObject
{
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	@ElementCollection
	protected List<CDecimal> weeklyLottoJackpot;
	@ElementCollection
	protected List<CDecimal> totoJackpot;
	
	@Deprecated
	protected Jackpots(){}
	
	public Jackpots(Object dummy)
	{
		weeklyLottoJackpot = ArrayListFac.new_CDecimalArray(8);
		totoJackpot = ArrayListFac.new_CDecimalArray(5);
		
		for(int i = 0; i < weeklyLottoJackpot.size(); ++i){ weeklyLottoJackpot.set(i, new CDecimal(0)); }
		for(int i = 0; i < totoJackpot.size(); ++i){ totoJackpot.set(i, new CDecimal(0)); }
	}
	
	public void setWeeklyLottoJackpot(List<CDecimal> weeklyLottoJackpot){ this.weeklyLottoJackpot = weeklyLottoJackpot; DB_UPDATE(); }
	public void setTotoJackpot(List<CDecimal> totoJackpot){ this.totoJackpot = totoJackpot; DB_UPDATE(); }	
	
	public ArrayList<CDecimal> getWeeklyLottoJackpot(){ return (ArrayList<CDecimal>)weeklyLottoJackpot; }
	public ArrayList<CDecimal> getTotoJackpot(){ return (ArrayList<CDecimal>)totoJackpot; }
}
