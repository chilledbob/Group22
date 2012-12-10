package gmb.model.financial.container;

import gmb.model.financial.FinancialManagement;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Jackpots 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int jackpotsId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected BigDecimal[] weeklyLottoJackpot = new BigDecimal[8];
	protected BigDecimal[] dailyLottoJackpot = new BigDecimal[10];
	protected BigDecimal[] totoJackpot = new BigDecimal[4];
	
	public Jackpots()
	{
		for(int i = 0; i < weeklyLottoJackpot.length; ++i){ weeklyLottoJackpot[i] = new BigDecimal(0); }
		for(int i = 0; i < dailyLottoJackpot.length; ++i){ dailyLottoJackpot[i] = new BigDecimal(0); }
		for(int i = 0; i < totoJackpot.length; ++i){ totoJackpot[i] = new BigDecimal(0); }
	}
	
	public void setWeeklyLottoJackpot(BigDecimal[] weeklyLottoJackpot){ this.weeklyLottoJackpot = weeklyLottoJackpot; }
	public void setDailyLottoJackpot(BigDecimal[] dailyLottoJackpot){ this.dailyLottoJackpot = dailyLottoJackpot; }
	public void setTotoJackpot(BigDecimal[] totoJackpot){ this.totoJackpot = totoJackpot; }	
	
	public BigDecimal[] getWeeklyLottoJackpot(){ return weeklyLottoJackpot; }
	public BigDecimal[] getDailyLottoJackpot(){ return dailyLottoJackpot; }
	public BigDecimal[] getTotoJackpot(){ return totoJackpot; }
}
