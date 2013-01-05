package gmb.model.request.group;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.member.Customer;

/**
 * A request type for group membership applications and invitations.
 */
@Entity
public class GroupMembershipInvitation extends GroupMembershipApplication 
{
	@Deprecated
	protected GroupMembershipInvitation(){}
	
	public GroupMembershipInvitation(Group group, Customer member, String note)
	{
		super(group, member, note);
	}
	
}
