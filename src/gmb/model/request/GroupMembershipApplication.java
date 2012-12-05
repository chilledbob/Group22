package gmb.model.request;

import javax.persistence.Entity;

import gmb.model.user.Group;
import gmb.model.user.Member;

@Entity
public class GroupMembershipApplication extends GroupRequest 
{
	@Deprecated
	protected GroupMembershipApplication(){}
	
	public GroupMembershipApplication(Group group, Member member, String note)
	{
		super(group, member, note);
	}
}
