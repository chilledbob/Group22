package gmb.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * BigDecimal type for currency calculations with scale of 2 an rounding mode HALF_UP
 * @author some pony
 */
@SuppressWarnings("serial")
public class CDecimal extends BigDecimal
{
	protected static final int scale = 2;
	protected static final RoundingMode round = RoundingMode.HALF_UP;
	protected static final MathContext CMC = MathContext.DECIMAL128 ;
	
	protected static final BigDecimal decN1 = new BigDecimal(-1);
	
	public CDecimal(int num){ super(num, CMC); setScale(scale, round); }
	public CDecimal(long num){ super(num, CMC); setScale(scale, round);}
	public CDecimal(String num){ super(num, CMC); setScale(scale, round);}
	public CDecimal(BigDecimal num){ super(num.toString(), CMC); setScale(scale, round);}
	
	public CDecimal add(CDecimal dec){ return new CDecimal( super.add(dec, CMC).setScale(scale, round) ); }
	public CDecimal subtract(CDecimal dec){ return new CDecimal( super.subtract(dec, CMC).setScale(scale, round) ); }
	public CDecimal multiply(CDecimal dec){ return new CDecimal( super.multiply(dec, CMC).setScale(scale, round) ); }
	public CDecimal divide(CDecimal dec){ return new CDecimal( super.divide(dec, CMC).setScale(scale, round) ); }

	public CDecimal negate(){ return new CDecimal( super.plus(CMC).multiply(decN1, CMC).setScale(scale, round) ); }
	public CDecimal abs(){ return new CDecimal( super.plus(CMC).setScale(scale, round) ); }
	
//	@Override
//	public int compareTo(CDecimal dec){ return ((BigDecimal)this).compareTo((BigDecimal)dec); }
}
