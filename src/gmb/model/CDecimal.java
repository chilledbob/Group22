package gmb.model;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import javax.persistence.Embeddable;

/**
 * BigDecimal based type for currency calculations with scale of 2 and rounding mode HALF_UP.
 */

@Embeddable
public class CDecimal extends PersiObject
{
    protected static final int scale = 2; 
    protected static final RoundingMode round = RoundingMode.HALF_UP; 
    protected static final MathContext CMC = MathContext.DECIMAL128 ; 
      
    protected BigDecimal myAmount; 
      
    public BigDecimal getAmount(){ return myAmount; } 
      
    public CDecimal(){ myAmount = new BigDecimal("0", CMC).setScale(scale, round); } 
    public CDecimal(int num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); } 
    public CDecimal(long num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); } 
    public CDecimal(String num){ myAmount = new BigDecimal(num, CMC).setScale(scale, round); } 
    public CDecimal(BigDecimal num){  myAmount = new BigDecimal(num.toString(), CMC).setScale(scale, round); } 
      
    public CDecimal add(CDecimal dec){ return new CDecimal( myAmount.add(dec.getAmount(), CMC).setScale(scale, round) ); } 
    public CDecimal subtract(CDecimal dec){ return new CDecimal( myAmount.subtract(dec.getAmount(), CMC).setScale(scale, round) ); } 
    public CDecimal multiply(CDecimal dec){ return new CDecimal( myAmount.multiply(dec.getAmount(), CMC).setScale(scale, round) ); } 
    public CDecimal divide(CDecimal dec){ return new CDecimal( myAmount.divide(dec.getAmount(), CMC).setScale(scale, round) ); } 
  
    public CDecimal negate(){ return new CDecimal( myAmount.negate(CMC).setScale(scale, round) ); } 
    public CDecimal abs(){ return new CDecimal( myAmount.abs(CMC).setScale(scale, round) ); } 
    public int signum(){ return myAmount.signum(); } 
      
    public int compareTo(CDecimal dec){ return myAmount.compareTo(dec.getAmount()); } 
    public boolean equals(Object dec){ return myAmount.equals(((CDecimal)dec).getAmount()); } 
      
    public String toString() 
    {  
        BigDecimal dec = myAmount.add(new BigDecimal("0.00"), CMC);//makes sure to always display two decimal places
        dec.setScale(scale, round); 
        return dec.toString();  
    } 
}
