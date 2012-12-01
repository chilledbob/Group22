package gmb.model.user;

import org.salespointframework.core.user.Capability;

public class Employee extends Member 
{
	//CONSTRUCTORS
	public Employee(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("employee"));
	}
}
