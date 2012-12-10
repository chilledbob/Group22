package gmb.model.financial.container;

import gmb.model.financial.FinancialManagement;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class PrizeCategories 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int jackpotsId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected BigDecimal[] weeklyLottoCategories = new BigDecimal[8];
	protected BigDecimal[] dailyLottoCategories = new BigDecimal[10];
	protected BigDecimal[] totoCategories = new BigDecimal[4];
	
	public PrizeCategories()
	{
		weeklyLottoCategories[7] = new BigDecimal(44);
		weeklyLottoCategories[6] = new BigDecimal(8);
		weeklyLottoCategories[5] = new BigDecimal(10);
		weeklyLottoCategories[4] = new BigDecimal(2);
		weeklyLottoCategories[3] = new BigDecimal(13);
		weeklyLottoCategories[2] = new BigDecimal(5);
		weeklyLottoCategories[1] = new BigDecimal(8);
		weeklyLottoCategories[0] = new BigDecimal(10);
		
		dailyLottoCategories[7] = new BigDecimal(2);
		dailyLottoCategories[6] = new BigDecimal(8);
		dailyLottoCategories[5] = new BigDecimal(22);
		dailyLottoCategories[4] = new BigDecimal(222);
		dailyLottoCategories[3] = new BigDecimal(888);
		dailyLottoCategories[2] = new BigDecimal(4444);
		dailyLottoCategories[1] = new BigDecimal(6666);
		dailyLottoCategories[0] = new BigDecimal(100000);
		
		totoCategories[3] = new BigDecimal(0);
		totoCategories[2] = new BigDecimal(0);
		totoCategories[1] = new BigDecimal(0);
		totoCategories[0] = new BigDecimal(0);
	}
	
	public void setWeeklyLottoCategories(BigDecimal[] weeklyLottoCategories){ this.weeklyLottoCategories = weeklyLottoCategories; }
	public void setDailyLottoCategories(BigDecimal[] dailyLottoCategories){ this.dailyLottoCategories = dailyLottoCategories; }
	public void setTotoCategories(BigDecimal[] totoCategories){ this.totoCategories = totoCategories; }	
	
	public BigDecimal[] getWeeklyLottoCategories(){ return weeklyLottoCategories; }
	public BigDecimal[] getDailyLottoCategories(){ return dailyLottoCategories; }
	public BigDecimal[] getTotoCategories(){ return totoCategories; }
}
