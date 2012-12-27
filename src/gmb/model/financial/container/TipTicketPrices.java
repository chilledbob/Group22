package gmb.model.financial.container;

import gmb.model.CDecimal;
import gmb.model.PersiObject;
import gmb.model.financial.FinancialManagement;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
public class TipTicketPrices extends PersiObject
{	
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="weeklyLottoSTTPrice",precision = 10, scale = 2))
	protected CDecimal weeklyLottoSTTPrice;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="totoSTTPRice",precision = 10, scale = 2))
	protected CDecimal totoSTTPrice;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="dailyLottoSTTPrice",precision = 10, scale = 2))
	protected CDecimal dailyLottoSTTPrice;
	
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="weeklyLottoPTTPrice_Month",precision = 10, scale = 2))
	protected CDecimal weeklyLottoPTTPrice_Month;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="dailyLottoPTTPrice_Month",precision = 10, scale = 2))
	protected CDecimal dailyLottoPTTPrice_Month;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="weeklyLottoPTTPrice_HalfYear",precision = 10, scale = 2))
	protected CDecimal weeklyLottoPTTPrice_HalfYear;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="dailyLottoPTTPrice_HalfYear",precision = 10, scale = 2))
	protected CDecimal dailyLottoPTTPrice_HalfYear;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="weeklyLottoPTTPrice_Year",precision = 10, scale = 2))
	protected CDecimal weeklyLottoPTTPrice_Year;
	@Embedded
	@AttributeOverride(name="myAmount", column= @Column(name="dailyLottoPTTPrice_Year",precision = 10, scale = 2))
	protected CDecimal dailyLottoPTTPrice_Year;
	
	@Deprecated
	protected TipTicketPrices(){}
	
	public TipTicketPrices(Object dummy)
	{
		  weeklyLottoSTTPrice = new CDecimal("0.75");
		  totoSTTPrice = new CDecimal("0.50");
		  dailyLottoSTTPrice = new CDecimal("0.50");
		
		  weeklyLottoPTTPrice_Month = new CDecimal("2.75");
		  dailyLottoPTTPrice_Month = new CDecimal("12");
		  weeklyLottoPTTPrice_HalfYear = new CDecimal("14");
		  dailyLottoPTTPrice_HalfYear = new CDecimal("65");
		  weeklyLottoPTTPrice_Year = new CDecimal("25");
		  dailyLottoPTTPrice_Year = new CDecimal("110");
	}
	
	public TipTicketPrices(CDecimal weeklyLottoSTTPrice, CDecimal totoSTTPrice, CDecimal dailyLottoSTTPrice, 
			CDecimal weeklyLottoPTTPrice_Month, CDecimal dailyLottoPTTPrice_Month,
			CDecimal weeklyLottoPTTPrice_HalfYear, CDecimal dailyLottoPTTPrice_HalfYear,
			CDecimal weeklyLottoPTTPrice_Year, CDecimal dailyLottoPTTPrice_Year)
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
	
	public void setWeeklyLottoSTTPrice(CDecimal price){ this.weeklyLottoSTTPrice = price; DB_UPDATE(); }
	public void setTotoSTTPrice(CDecimal price){ this.totoSTTPrice = price; DB_UPDATE(); }
	public void setDailyLottoSTTPrice(CDecimal price){ this.dailyLottoSTTPrice = price; DB_UPDATE(); }
	
	public void setWeeklyLottoPTTPrice_Month(CDecimal price){ this.weeklyLottoPTTPrice_Month = price; DB_UPDATE(); }
	public void setDailyLottoPTTPrice_Month(CDecimal price){ this.dailyLottoPTTPrice_Month = price; DB_UPDATE(); }
	public void setWeeklyLottoPTTPrice_HalfYear(CDecimal price){ this.weeklyLottoPTTPrice_HalfYear = price; DB_UPDATE(); }
	public void setDailyLottoPTTPrice_HalfYear(CDecimal price){ this.dailyLottoPTTPrice_HalfYear = price; DB_UPDATE(); }
	public void setWeeklyLottoPTTPrice_Year(CDecimal price){ this.weeklyLottoPTTPrice_Year = price; DB_UPDATE(); }
	public void setDailyLottoPTTPrice_Year(CDecimal price){ this.dailyLottoPTTPrice_Year = price; DB_UPDATE(); }
	
	public CDecimal getWeeklyLottoSTTPrice(){ return weeklyLottoSTTPrice; }
	public CDecimal getTotoSTTPrice(){ return totoSTTPrice; }
	public CDecimal getDailyLottoSTTPrice(){ return dailyLottoSTTPrice; }
	
	public CDecimal getWeeklyLottoPTTPrice_Month(){ return weeklyLottoPTTPrice_Month; }
	public CDecimal getDailyLottoPTTPrice_Month(){ return dailyLottoPTTPrice_Month; }
	public CDecimal getWeeklyLottoPTTPrice_HalfYear(){ return weeklyLottoPTTPrice_HalfYear; }
	public CDecimal getDailyLottoPTTPrice_HalfYear(){ return dailyLottoPTTPrice_HalfYear; }
	public CDecimal getWeeklyLottoPTTPrice_Year(){ return weeklyLottoPTTPrice_Year; }
	public CDecimal getDailyLottoPTTPrice_Year(){ return dailyLottoPTTPrice_Year; }
}
