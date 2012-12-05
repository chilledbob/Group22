package gmb.model.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Adress {
	
	//ATTRIBUTES
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int adrId;
	
	private String streetName;
	private String houseNumber; //STRING ??
	private String postCode;	// STRING ??
	private String townName;
	
	//CONSTRUCTORS
	public Adress()
	{
		
	}
	public Adress(String memberStreetName, String memberHouseNumber, String memberPostCode, String memberTownName)
	{
		streetName = memberStreetName;
		houseNumber = memberHouseNumber;
		postCode = memberPostCode;
		townName = memberTownName;
		
	}
	
	//GET METHODS
 	public String getStreetName()
	{
		return streetName;
	}
	public String getHouseNumber()
	{
		return houseNumber;
	}
	public String getPostCode()
	{
		return postCode;
	}
	public String getTownName()
	{
		return townName;
	}
	
	//SET METHODS
	public void setStreetName(String newSName)
	{
		streetName = newSName;
	}
	public void setHouseNumber(String newHNumber)
	{
		houseNumber = newHNumber;
	}
	public void setPostCode(String newPCode)
	{
		postCode = newPCode;
	}
	public void setTownName(String newTName)
	{
		townName = newTName;
	}

}
