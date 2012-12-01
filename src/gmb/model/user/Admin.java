package gmb.model.user;
import org.salespointframework.core.user.Capability;

public class Admin extends Employee 
{	
	//CONSTRUCTORS
	public Admin(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("admin"));
	}
}
