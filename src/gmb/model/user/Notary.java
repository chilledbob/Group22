package gmb.model.user;

import org.salespointframework.core.user.Capability;

public class Notary extends Member 
{	
	//CONSTRUCTORS
	public Notary(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("notary"));
	}
}
