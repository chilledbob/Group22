package gmb.model.user;

public class Adress 
{	
	//ATTRIBUTES
	protected String streetName;
	protected String houseNumber; 
	protected String postCode;	
	protected String townName;
	
	//CONSTRUCTORS
	@Deprecated
	public Adress(){}
	
	public Adress(String streetName, String houseNumber, String postCode, String townName)
	{
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.townName = townName;
	}
	
	//GET METHODS
 	public String getStreetName(){ return streetName; }
	public String getHouseNumber(){ return houseNumber; }
	public String getPostCode(){ return postCode; }
	public String getTownName(){ return townName; }
	
	//SET METHODS
//	public void setStreetName(String newSName){ streetName = newSName; }
//	public void setHouseNumber(String newHNumber){ houseNumber = newHNumber; }
//	public void setPostCode(String newPCode){ postCode = newPCode; }
//	public void setTownName(String newTName){ townName = newTName; }
}
