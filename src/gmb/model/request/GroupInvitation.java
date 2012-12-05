package gmb.model.request;

import gmb.model.user.Customer;
import gmb.model.user.Group;

public class GroupInvitation extends GroupRequest 
{
	@Deprecated
	protected GroupInvitation(){}
	
	public GroupInvitation(Group group, Customer member, String note)
	{
		super(group, member, note);
	}
	
	/**
	 * Return codes:
	 * 0 - successful
	 * 1 - failed because state was not "UNHANDLED"
	 */
	public int accept()
	{
		if(super.accept() == 0)
			{
				group.addGroupMember((Customer) member);
				return 0;
			}
		else
			return 1;
	}
}
