package gmb.model.request;

import gmb.model.user.Group;
import gmb.model.user.Member;

public class GroupMembershipApplication extends GroupRequest 
{
	public GroupMembershipApplication(Group group, Member member, String note)
	{
		super(group, member, note);
	}
}
