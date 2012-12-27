package gmb.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * BigDecimal type for currency calculations with scale of 2 an rounding mode HALF_UP
 * @author some pony
 */
@Embeddable
public class CDecimal
{
	protected static final int scale = 2;
	protected static final RoundingMode round = RoundingMode.HALF_UP;
	protected static final MathContext CMC = MathContext.DECIMAL128 ;
	
	protected static final BigDecimal decN1 = new BigDecimal(-1);
	
	@Column(precision = 10, scale = 2)
	protected BigDecimal myAmount;
	
	public BigDecimal getAmount(){ return myAmount; }
	
	public CDecimal(){ myAmount = new BigDecimal("0", CMC).setScale(scale, round); }
	public CDecimal(int num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); }
	public CDecimal(long num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); }
	public CDecimal(String num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); }
	public CDecimal(BigDecimal num){  myAmount = new BigDecimal(num.toString(), CMC).setScale(scale, round); }
	
	public CDecimal add(CDecimal dec){ return new CDecimal( myAmount = myAmount.add(dec.getAmount(), CMC).setScale(scale, round) ); }
	public CDecimal subtract(CDecimal dec){ return new CDecimal( myAmount = myAmount.subtract(dec.getAmount(), CMC).setScale(scale, round) ); }
	public CDecimal multiply(CDecimal dec){ return new CDecimal( myAmount = myAmount.multiply(dec.getAmount(), CMC).setScale(scale, round) ); }
	public CDecimal divide(CDecimal dec){ return new CDecimal( myAmount = myAmount.divide(dec.getAmount(), CMC).setScale(scale, round) ); }

	public CDecimal negate(){ return new CDecimal( myAmount = myAmount.plus(CMC).multiply(decN1, CMC).setScale(scale, round) ); }
	public CDecimal abs(){ return new CDecimal( myAmount = myAmount.plus(CMC).setScale(scale, round) ); }
	public int signum(){ return myAmount.signum(); }
	
	public int compareTo(CDecimal dec){ return myAmount.compareTo(dec.getAmount()); }
	
	public String toString()
	{ 
		BigDecimal dec = myAmount.add(new BigDecimal("0.00"), CMC);
		dec.setScale(scale, round);
		return dec.toString(); 
	}
}
