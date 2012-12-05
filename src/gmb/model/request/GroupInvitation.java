package gmb.model.request;

import javax.persistence.Entity;

import gmb.model.user.Group;
import gmb.model.user.Member;

@Entity
public class GroupInvitation extends GroupRequest 
{
	@Deprecated
	protected GroupInvitation(){}
	
	public GroupInvitation(Group group, Member member, String note)
	{
		super(group, member, note);
	}
}
