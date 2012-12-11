package gmb.model.member;

import gmb.model.member.container.MemberData;

import org.salespointframework.core.user.Capability;

import javax.persistence.Entity;

@Entity
public class Notary extends Member 
{	
	@Deprecated
	protected Notary(){}
	
	public Notary(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("notary"));
	}
}
