package gmb.model.user;

import javax.persistence.Entity;

import org.salespointframework.core.user.Capability;

@Entity
public class Admin extends Employee 
{	
	@Deprecated
	protected Admin(){}
	
	public Admin(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("admin"));
	}
}
