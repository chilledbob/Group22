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
public class PrizeCategories extends PersiObject
{	
	@OneToOne
	protected FinancialManagement financialManagementId;
	
	@ElementCollection
	protected List<CDecimal> weeklyLottoCategories;
	@ElementCollection
	protected List<CDecimal> dailyLottoCategories;
	@ElementCollection
	protected List<CDecimal> totoCategories;
	
	@Deprecated
	protected PrizeCategories(){}
	
	public PrizeCategories(Object dummy)
	{
		weeklyLottoCategories = ArrayListFac.new_CDecimalArray(8);
		dailyLottoCategories = ArrayListFac.new_CDecimalArray(10);
		totoCategories = ArrayListFac.new_CDecimalArray(4);
		
		weeklyLottoCategories.set(7, new CDecimal(44));
		weeklyLottoCategories.set(6, new CDecimal(8));
		weeklyLottoCategories.set(5, new CDecimal(10));
		weeklyLottoCategories.set(4, new CDecimal(2));
		weeklyLottoCategories.set(3, new CDecimal(13));
		weeklyLottoCategories.set(2, new CDecimal(5));
		weeklyLottoCategories.set(1, new CDecimal(8));
		weeklyLottoCategories.set(0, new CDecimal(10));
		
		dailyLottoCategories.set(9, new CDecimal(4));
		dailyLottoCategories.set(8, new CDecimal(22));
		dailyLottoCategories.set(7, new CDecimal(44));
		dailyLottoCategories.set(6, new CDecimal(222));
		dailyLottoCategories.set(5, new CDecimal(444));
		dailyLottoCategories.set(4, new CDecimal(888));
		dailyLottoCategories.set(3, new CDecimal(2222));
		dailyLottoCategories.set(2, new CDecimal(4444));
		dailyLottoCategories.set(1, new CDecimal(8888));
		dailyLottoCategories.set(0, new CDecimal(100000));
		
		totoCategories.set(3, new CDecimal(0));
		totoCategories.set(2, new CDecimal(0));
		totoCategories.set(1, new CDecimal(0));
		totoCategories.set(0, new CDecimal(0));
	}
	
	public void setWeeklyLottoCategories(List<CDecimal> weeklyLottoCategories){ this.weeklyLottoCategories = weeklyLottoCategories; DB_UPDATE(); }
	public void setDailyLottoCategories(List<CDecimal> dailyLottoCategories){ this.dailyLottoCategories = dailyLottoCategories; DB_UPDATE(); }
	public void setTotoCategories(List<CDecimal> totoCategories){ this.totoCategories = totoCategories; DB_UPDATE(); }	
	
	public ArrayList<CDecimal> getWeeklyLottoCategories(){ return (ArrayList<CDecimal>)weeklyLottoCategories; }
	public ArrayList<CDecimal> getDailyLottoCategories(){ return (ArrayList<CDecimal>)dailyLottoCategories; }
	public ArrayList<CDecimal> getTotoCategories(){ return (ArrayList<CDecimal>)totoCategories; }
}
