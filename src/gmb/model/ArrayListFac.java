package gmb.model;

import gmb.model.tip.tip.single.SingleTip;

import java.util.ArrayList;
import java.util.LinkedList;

public class ArrayListFac 
{
	public static ArrayList<CDecimal> new_CDecimalArray(int element_count)
	{
		ArrayList<CDecimal> array = new ArrayList<CDecimal>(element_count);
		
		for(int i = 0; i < element_count; ++i)
			array.add(new CDecimal(0));
		
		return array;
	}
	
	public static ArrayList<LinkedList<SingleTip>> new_SingleTipLinkedListArray(int element_count)
	{
		ArrayList<LinkedList<SingleTip>> array = new ArrayList<LinkedList<SingleTip>>(element_count);
		
		for(int i = 0; i < element_count; ++i)
			array.add(new LinkedList<SingleTip>());
		
		return array;
	}
}
