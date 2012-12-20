package gmb.model;

/**
 * Generic container class intended for returning multiple results.
 *
 * @param <T1> Result 1 to be returned.
 * @param <T2> Result 2 to be returned.
 */
public class ReturnBox <T1, T2>
{
	public final T1 var1;
	public final T2 var2;
	
	public ReturnBox(T1 var1, T2 var2)
	{
		this.var1 = var1;
		this.var2 = var2;
	}
}
