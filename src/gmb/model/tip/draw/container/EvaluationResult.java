package gmb.model.tip.draw.container;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import gmb.model.ArrayListFac;
import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.Lottery;
import gmb.model.PersiObject;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.transaction.Winnings;
import gmb.model.tip.tip.single.SingleTip;

@Entity
public class EvaluationResult extends PersiObject
{
	@OneToOne
	protected ReceiptsDistributionResult receiptsDistributionResult;
	
	@OneToMany
	protected List<Winnings> winnings;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date evaluationDate;
	
	@Transient
	protected ArrayList<LinkedList<SingleTip>> tipsInCategory;
	
	@Deprecated
	protected EvaluationResult(){}

	public EvaluationResult(int categoryCount)
	{
		receiptsDistributionResult = null;
		winnings = new LinkedList<Winnings>();
		tipsInCategory = ArrayListFac.new_SingleTipLinkedListArray(categoryCount);
		
		evaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	protected ArrayList<CDecimal> createDeepCopyForCDecimalArray(ArrayList<CDecimal> array)
	{
		List<CDecimal> copy = ArrayListFac.new_CDecimalArray(array.size());

		for(int i = 0; i < array.size(); ++i)
			copy.set(i, array.get(i));

		return (ArrayList<CDecimal>)copy;
	}

	protected ArrayList<LinkedList<SingleTip>> createDeepCopyForSingleTipLLArray(ArrayList<LinkedList<SingleTip>> array)
	{
		ArrayList<LinkedList<SingleTip>> copy = ArrayListFac.new_SingleTipLinkedListArray(array.size());

		for(int i = 0; i < array.size(); ++i)
			copy.set(i, array.get(i));

		return copy;
	}
	
	public CDecimal initReceiptsDistributionResult(CDecimal drawReceipts)
	{
		receiptsDistributionResult = GmbFactory.new_ReceiptsDistributionResult(drawReceipts);
		
		DB_UPDATE(); 

		return receiptsDistributionResult.getWinnersDue();
	}
	
	public void setTipsInCategory(ArrayList<LinkedList<SingleTip>> tipsInCategory)
	{ 
		this.tipsInCategory = tipsInCategory; 
		DB_UPDATE(); 
	}
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); DB_UPDATE(); }
	public void addWinnings(LinkedList<Winnings> winnings){ this.winnings.addAll(winnings); DB_UPDATE(); }

	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; DB_UPDATE(); }
	
	public ArrayList<LinkedList<SingleTip>> getTipsInCategory(){ return tipsInCategory; }

	public LinkedList<SingleTip> getTipsInCategory(int categoryID){ return tipsInCategory.get(categoryID); }

	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }
}
