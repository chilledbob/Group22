package gmb.model.financial.container;
import java.math.BigDecimal;

import gmb.model.financial.FinancialManagement;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class TipTicketPrices 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int tipTicketPricesId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected BigDecimal weeklyLottoSTTPrice = new BigDecimal(0.75);
	protected BigDecimal totoSTTPrice = new BigDecimal(0.50);
	protected BigDecimal dailyLottoSTTPrice = new BigDecimal(0.50);
	
	protected BigDecimal weeklyLottoPTTPrice_Month = new BigDecimal(2.75);
	protected BigDecimal dailyLottoPTTPrice_Month = new BigDecimal(1.75);
	protected BigDecimal weeklyLottoPTTPrice_HalfYear = new BigDecimal(14);
	protected BigDecimal dailyLottoPTTPrice_HalfYear = new BigDecimal(9);
	protected BigDecimal weeklyLottoPTTPrice_Year = new BigDecimal(25);
	protected BigDecimal dailyLottoPTTPrice_Year = new BigDecimal(15);
	
	public TipTicketPrices(){}
	
	public TipTicketPrices(BigDecimal weeklyLottoSTTPrice, BigDecimal totoSTTPrice, BigDecimal dailyLottoSTTPrice, 
			BigDecimal weeklyLottoPTTPrice_Month, BigDecimal dailyLottoPTTPrice_Month,
			BigDecimal weeklyLottoPTTPrice_HalfYear, BigDecimal dailyLottoPTTPrice_HalfYear,
			BigDecimal weeklyLottoPTTPrice_Year, BigDecimal dailyLottoPTTPrice_Year)
	{
		this.weeklyLottoSTTPrice = weeklyLottoSTTPrice;
		this.totoSTTPrice = totoSTTPrice;
		this.dailyLottoSTTPrice = dailyLottoSTTPrice;
		
		this.weeklyLottoPTTPrice_Month = weeklyLottoPTTPrice_Month;
		this.dailyLottoPTTPrice_Month = dailyLottoPTTPrice_Month;
		this.weeklyLottoPTTPrice_HalfYear = weeklyLottoPTTPrice_HalfYear;
		this.dailyLottoPTTPrice_HalfYear = dailyLottoPTTPrice_HalfYear;	
		this.weeklyLottoPTTPrice_Year = weeklyLottoPTTPrice_Year;
		this.dailyLottoPTTPrice_Year = dailyLottoPTTPrice_Year;
	}
	
	public void setWeeklyLottoSTTPrice(BigDecimal price){ this.weeklyLottoSTTPrice = price; }
	public void setTotoSTTPrice(BigDecimal price){ this.totoSTTPrice = price; }
	public void setDailyLottoSTTPrice(BigDecimal price){ this.dailyLottoSTTPrice = price; }
	
	public void setWeeklyLottoPTTPrice_Month(BigDecimal price){ this.weeklyLottoPTTPrice_Month = price; }
	public void setDailyLottoPTTPrice_Month(BigDecimal price){ this.dailyLottoPTTPrice_Month = price; }
	public void setWeeklyLottoPTTPrice_HalfYear(BigDecimal price){ this.weeklyLottoPTTPrice_HalfYear = price; }
	public void setDailyLottoPTTPrice_HalfYear(BigDecimal price){ this.dailyLottoPTTPrice_HalfYear = price; }
	public void setWeeklyLottoPTTPrice_Year(BigDecimal price){ this.weeklyLottoPTTPrice_Year = price; }
	public void setDailyLottoPTTPrice_Year(BigDecimal price){ this.dailyLottoPTTPrice_Year = price; }
	
	public BigDecimal getWeeklyLottoSTTPrice(){ return weeklyLottoSTTPrice; }
	public BigDecimal getTotoSTTPrice(){ return totoSTTPrice; }
	public BigDecimal getDailyLottoSTTPrice(){ return dailyLottoSTTPrice; }
	
	public BigDecimal getWeeklyLottoPTTPrice_Month(){ return weeklyLottoPTTPrice_Month; }
	public BigDecimal getDailyLottoPTTPrice_Month(){ return dailyLottoPTTPrice_Month; }
	public BigDecimal getWeeklyLottoPTTPrice_HalfYear(){ return weeklyLottoPTTPrice_HalfYear; }
	public BigDecimal getDailyLottoPTTPrice_HalfYear(){ return dailyLottoPTTPrice_HalfYear; }
	public BigDecimal getWeeklyLottoPTTPrice_Year(){ return weeklyLottoPTTPrice_Year; }
	public BigDecimal getDailyLottoPTTPrice_Year(){ return dailyLottoPTTPrice_Year; }
}
