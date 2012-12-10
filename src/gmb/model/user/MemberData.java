package gmb.model.user;

import java.util.Date;

import org.joda.time.DateTime;

import javax.persistence.CascadeType;
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
public class MemberData 
{
	@Id @GeneratedValue (strategy=GenerationType.IDENTITY)
	protected int memberDataId;

	protected String firstName;
	protected String lastName;

	@Temporal(value = TemporalType.TIMESTAMP)
	protected Date birthDate;
	protected String phoneNumber;
	protected String eMail;
	@OneToOne(cascade=CascadeType.ALL)
    @JoinColumn(name="adrId") 
	protected Adress adress;
	
	
	@Deprecated
	protected MemberData(){}
	
	public MemberData(String firstName, String lastName, DateTime birthDate, String phoneNumber, String eMail, Adress adress)
	{	
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate.toDate();
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.adress = adress;
	}
	
	public String getFirstName(){ return firstName; }
	public String getLastName(){ return lastName; }
	public DateTime getBirthDate(){ return new DateTime(birthDate); }
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
