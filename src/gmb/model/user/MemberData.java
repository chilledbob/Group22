package gmb.model.user;

import org.joda.time.DateTime;


public class MemberData 
{
	//ATTRIBUTES
	protected String firstName;
	protected String lastName;
	protected DateTime birthDate;
	protected String phoneNumber;
	protected String eMail;
	protected Adress adress;
	
	//CONSTRUCTOR
	
	@Deprecated
	protected MemberData(){}
	
	public MemberData(String memberFirstName, String memberLastName, DateTime memberBirthDate, String memberPhoneNumber, String memberEMail, Adress memberAdress)
	{	
		firstName = memberFirstName;
		lastName = memberLastName;
		birthDate = memberBirthDate;
		phoneNumber = memberPhoneNumber;
		eMail = memberEMail;
		adress = memberAdress;
	}
	
	//GET METHODS
	public String getFirstName(){ return firstName; }
	public String getLastName(){ return lastName; }
	public DateTime getBirthDate(){ return birthDate; }
	public String getPhoneNumber(){ return phoneNumber; }
	public String getEMail(){ return eMail; }
	public Adress getAdress(){ return adress; }
	
	//SET METHODS
//	public void setFirstName(String newFName){ firstName = newFName; }
//	public void setLastName(String newLName){ lastName = newLName; }
//	public void setBirthDate(DateTime newDate){ birthDate = newDate; }
//	public void setPhoneNumber(String newPhoneNumber){ phoneNumber = newPhoneNumber; }
//	public void setEMail(String newEMail){ eMail = newEMail; }
//	public void setAdress(Adress newAdress){ adress = newAdress; }
}
