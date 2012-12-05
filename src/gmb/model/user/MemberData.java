package gmb.model.user;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

//import org.eclipse.persistence.internal.jpa.parsing.TemporalLiteralNode.TemporalType;

@Entity
public class MemberData {
	
	
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int memberDataId;
	
	private String firstName;
	private String lastName;
	@Temporal(value = TemporalType.DATE)
	private Date birthDate;
	private String phoneNumber;
	private String eMail;
	
	@OneToOne 
    @JoinColumn(name="adrId") 
	private Adress adress;
	
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
