package gmb.model.request;

import gmb.model.user.Group;
import gmb.model.user.Member;

public class GroupInvitation extends GroupRequest 
{
	@Deprecated
	protected GroupInvitation(){}
	
	public GroupInvitation(Group group, Member member, String note)
	{
		super(group, member, note);
	}
}
