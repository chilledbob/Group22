package gmb.model.financial.container;

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
	
	protected CDecimal[] weeklyLottoJackpot = new CDecimal[8];
	protected CDecimal[] dailyLottoJackpot = new CDecimal[10];
	protected CDecimal[] totoJackpot = new CDecimal[4];
	
	public Jackpots()
	{
		for(int i = 0; i < weeklyLottoJackpot.length; ++i){ weeklyLottoJackpot[i] = new CDecimal(0); }
		for(int i = 0; i < dailyLottoJackpot.length; ++i){ dailyLottoJackpot[i] = new CDecimal(0); }
		for(int i = 0; i < totoJackpot.length; ++i){ totoJackpot[i] = new CDecimal(0); }
	}
	
	public void setWeeklyLottoJackpot(CDecimal[] weeklyLottoJackpot){ this.weeklyLottoJackpot = weeklyLottoJackpot; DB_UPDATE(); }
	public void setDailyLottoJackpot(CDecimal[] dailyLottoJackpot){ this.dailyLottoJackpot = dailyLottoJackpot; DB_UPDATE(); }
	public void setTotoJackpot(CDecimal[] totoJackpot){ this.totoJackpot = totoJackpot; DB_UPDATE(); }	
	
	public CDecimal[] getWeeklyLottoJackpot(){ return weeklyLottoJackpot; }
	public CDecimal[] getDailyLottoJackpot(){ return dailyLottoJackpot; }
	public CDecimal[] getTotoJackpot(){ return totoJackpot; }
}
