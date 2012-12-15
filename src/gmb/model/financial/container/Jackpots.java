package gmb.model.financial.container;

import java.util.ArrayList;
import java.util.List;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Jackpots extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int jackpotsId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected List<CDecimal> weeklyLottoJackpot;
	protected List<CDecimal> dailyLottoJackpot;
	protected List<CDecimal> totoJackpot;
	
	@Deprecated
	protected Jackpots(){}
	
	public Jackpots(Object dummy)
	{
		weeklyLottoJackpot = ArrayListFac.new_CDecimalArray(8);
		dailyLottoJackpot = ArrayListFac.new_CDecimalArray(10);
		totoJackpot = ArrayListFac.new_CDecimalArray(4);
		
		for(int i = 0; i < weeklyLottoJackpot.size(); ++i){ weeklyLottoJackpot.set(i, new CDecimal(0)); }
		for(int i = 0; i < dailyLottoJackpot.size(); ++i){ dailyLottoJackpot.set(i, new CDecimal(0)); }
		for(int i = 0; i < totoJackpot.size(); ++i){ totoJackpot.set(i, new CDecimal(0)); }
	}
	
	public void setWeeklyLottoJackpot(List<CDecimal> weeklyLottoJackpot){ this.weeklyLottoJackpot = weeklyLottoJackpot; DB_UPDATE(); }
	public void setDailyLottoJackpot(List<CDecimal> dailyLottoJackpot){ this.dailyLottoJackpot = dailyLottoJackpot; DB_UPDATE(); }
	public void setTotoJackpot(List<CDecimal> totoJackpot){ this.totoJackpot = totoJackpot; DB_UPDATE(); }	
	
	public ArrayList<CDecimal> getWeeklyLottoJackpot(){ return (ArrayList<CDecimal>)weeklyLottoJackpot; }
	public ArrayList<CDecimal> getDailyLottoJackpot(){ return (ArrayList<CDecimal>)dailyLottoJackpot; }
	public ArrayList<CDecimal> getTotoJackpot(){ return (ArrayList<CDecimal>)totoJackpot; }
}
