package gmb.model.member.container;

import gmb.model.PersiObject;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Adress extends PersiObject
{	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int adrId;
	
	protected String streetName;
	protected String houseNumber; 
	protected String postCode;	
	protected String townName;
	
	@Deprecated
	public Adress(){}
	
	public Adress(String streetName, String houseNumber, String postCode, String townName)
	{
		this.streetName = streetName;
		this.houseNumber = houseNumber;
		this.postCode = postCode;
		this.townName = townName;
	}
	
 	public String getStreetName(){ return streetName; }
	public String getHouseNumber(){ return houseNumber; }
	public String getPostCode(){ return postCode; }
	public String getTownName(){ return townName; }
	
//	public void setStreetName(String newSName){ streetName = newSName; DB_UPDATE(); }
//	public void setHouseNumber(String newHNumber){ houseNumber = newHNumber; DB_UPDATE(); }
//	public void setPostCode(String newPCode){ postCode = newPCode; DB_UPDATE(); }
//	public void setTownName(String newTName){ townName = newTName; DB_UPDATE(); }
}
