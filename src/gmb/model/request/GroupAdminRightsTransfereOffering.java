package gmb.model.request;

import javax.persistence.Entity;

import gmb.model.user.Group;
import gmb.model.user.Member;

@Entity
public class GroupAdminRightsTransfereOffering extends GroupRequest 
{
	@Deprecated
	protected GroupAdminRightsTransfereOffering(){}
	
	public GroupAdminRightsTransfereOffering(Group group, Member member, String note)
	{
		super(group, member, note);
	}
}
