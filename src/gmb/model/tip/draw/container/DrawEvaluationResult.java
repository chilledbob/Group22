package gmb.model.tip.draw.container;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

import gmb.model.Lottery;
import gmb.model.financial.container.ReceiptsDistributionResult;
import gmb.model.financial.transaction.Winnings;

@Entity
public class DrawEvaluationResult 
{
	ReceiptsDistributionResult receiptsDistributionResult = null;
	List<Winnings> winnings = new LinkedList<Winnings>();
	
	@Temporal(value = TemporalType.TIMESTAMP)
	Date evaluationDate;
	
	BigDecimal[] jackpotImageBefore;
	BigDecimal[] jackpotImageAfter;
	BigDecimal[] undistributedPrizes;
	
	public DrawEvaluationResult()
	{
		evaluationDate = Lottery.getInstance().getTimer().getDateTime().toDate();
	}
	
	protected BigDecimal[] createDeepCopy(BigDecimal[] array)
	{
		BigDecimal[] copy = new BigDecimal[array.length];
		
		for(int i = 0; i < array.length; ++i)
			copy[i] = array[i];
		
		return copy;
	}
	
	public void createJackpotImageBefore(BigDecimal[] jackpot){ jackpotImageBefore = createDeepCopy(jackpot); }
	
	public void createJackpotImageAfterAndUndistributedPrizes(BigDecimal[] jackpot)
	{ 
		assert jackpotImageBefore.length == jackpot.length : "Jackpot image length does not fit in DrawEvaluationResult.createJackpotImageAfterAndUndistributedPrizes(BigDecimal[] jackpot)";
		
		jackpotImageAfter = createDeepCopy(jackpot); 
		
		undistributedPrizes = new BigDecimal[jackpotImageAfter.length]; 
		
		for(int i = 0; i < undistributedPrizes.length; ++i)
			undistributedPrizes[i] = jackpotImageAfter[i].subtract(jackpotImageBefore[i]);
	}
	
	public BigDecimal initReceiptsDistributionResult(BigDecimal drawReceipts)
	{
		receiptsDistributionResult = new ReceiptsDistributionResult(drawReceipts);
		return receiptsDistributionResult.getWinnersDue();
	}
	
	public void addWinnings(Winnings winnings){ this.winnings.add(winnings); }
	public void setReceiptsDistributionResult(ReceiptsDistributionResult receiptsDistributionResult){ this.receiptsDistributionResult = receiptsDistributionResult; }
	
	public ReceiptsDistributionResult getReceiptsDistributionResult(){ return receiptsDistributionResult; }
	public List<Winnings> getWinnings(){ return winnings; }
	public DateTime getEvaluationDate(){ return new DateTime(evaluationDate); }
	
	public BigDecimal[] getJackpotImageBefore(){ return jackpotImageBefore; }
	public BigDecimal[] getJackpotImageAfter(){ return jackpotImageAfter; }
	public BigDecimal[] getUndistributedPrizes(){ return undistributedPrizes; }
}
