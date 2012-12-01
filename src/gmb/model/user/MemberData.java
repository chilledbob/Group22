package gmb.model.user;
import java.util.Date;


public class MemberData {
	
	

	//ATTRIBUTES
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String phoneNumber;
	private String eMail;
	private Adress adress;
	
	//CONSTRUCTOR
	public MemberData()
	{		
	}
	public MemberData(String memberFirstName, String memberLastName, Date memberBirthDate, String memberPhoneNumber, String memberEMail, Adress memberAdress)
	{	
		firstName = memberFirstName;
		lastName = memberLastName;
		birthDate = memberBirthDate;
		phoneNumber = memberPhoneNumber;
		eMail = memberEMail;
		adress = memberAdress;
	}
	
	//GET METHODS
	public String getFirstName()
	{
		return firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public Date getBirthDate()
	{
		return birthDate;
	}
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public String getEMail()
	{
		return eMail;
	}
	public Adress getAdress()
	{
		return adress;
	}
	
	//SET METHODS
	public void setFirstName(String newFName)
	{
		firstName = newFName;
	}
	public void setLastName(String newLName)
	{
		lastName = newLName;
	}
	public void setBirthDate(Date newDate)
	{
		birthDate = newDate;
	}
	public void setPhoneNumber(String newPhoneNumber)
	{
		phoneNumber = newPhoneNumber;
	}
	public void setEMail(String newEMail)
	{
		eMail = newEMail;
	}
	public void setAdress(Adress newAdress)
	{
		adress = newAdress;
	}
	
	

}
