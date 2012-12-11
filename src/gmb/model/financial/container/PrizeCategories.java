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
public class PrizeCategories extends PersiObject
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int jackpotsId;
	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	protected CDecimal[] weeklyLottoCategories = new CDecimal[8];
	protected CDecimal[] dailyLottoCategories = new CDecimal[10];
	protected CDecimal[] totoCategories = new CDecimal[4];
	
	public PrizeCategories()
	{
		weeklyLottoCategories[7] = new CDecimal(44);
		weeklyLottoCategories[6] = new CDecimal(8);
		weeklyLottoCategories[5] = new CDecimal(10);
		weeklyLottoCategories[4] = new CDecimal(2);
		weeklyLottoCategories[3] = new CDecimal(13);
		weeklyLottoCategories[2] = new CDecimal(5);
		weeklyLottoCategories[1] = new CDecimal(8);
		weeklyLottoCategories[0] = new CDecimal(10);
		
		dailyLottoCategories[7] = new CDecimal(2);
		dailyLottoCategories[6] = new CDecimal(8);
		dailyLottoCategories[5] = new CDecimal(22);
		dailyLottoCategories[4] = new CDecimal(222);
		dailyLottoCategories[3] = new CDecimal(888);
		dailyLottoCategories[2] = new CDecimal(4444);
		dailyLottoCategories[1] = new CDecimal(6666);
		dailyLottoCategories[0] = new CDecimal(100000);
		
		totoCategories[3] = new CDecimal(0);
		totoCategories[2] = new CDecimal(0);
		totoCategories[1] = new CDecimal(0);
		totoCategories[0] = new CDecimal(0);
	}
	
	public void setWeeklyLottoCategories(CDecimal[] weeklyLottoCategories){ this.weeklyLottoCategories = weeklyLottoCategories; DB_UPDATE(); }
	public void setDailyLottoCategories(CDecimal[] dailyLottoCategories){ this.dailyLottoCategories = dailyLottoCategories; DB_UPDATE(); }
	public void setTotoCategories(CDecimal[] totoCategories){ this.totoCategories = totoCategories; DB_UPDATE(); }	
	
	public CDecimal[] getWeeklyLottoCategories(){ return weeklyLottoCategories; }
	public CDecimal[] getDailyLottoCategories(){ return dailyLottoCategories; }
	public CDecimal[] getTotoCategories(){ return totoCategories; }
}
