package gmb.model.member;

import gmb.model.member.container.MemberData;

import org.salespointframework.core.user.Capability;

import javax.persistence.Entity;

@Entity
public class Employee extends Member 
{
	@Deprecated
	protected Employee(){}
	
	public Employee(String nickName, String password, MemberData memberData)
	{
		super(nickName, password, memberData);
		this.addCapability(new Capability("employee"));
	}
}
