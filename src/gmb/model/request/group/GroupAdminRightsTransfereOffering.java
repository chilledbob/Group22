package gmb.model.request.group;

import javax.persistence.Entity;

import gmb.model.group.Group;
import gmb.model.member.Customer;

@Entity
public class GroupAdminRightsTransfereOffering extends GroupRequest 
{
	@Deprecated
	protected GroupAdminRightsTransfereOffering(){}

	public GroupAdminRightsTransfereOffering(Group group, Customer member, String note)
	{
		super(group, member, note);
	}

	/**
	 * [intended for direct usage by controller]
	 * Return codes:
	 * 0 - successful
	 * 1 - failed because state was not "UNHANDLED"
	 * 2 - failed because "member" is not in "group"'s list of members
	 */
	public int accept()
	{
		if(super.accept() == 0)
			if(group.switchGroupAdmin((Customer) member)){ return 0; }
			else
			{
				state = 3;
				DB_UPDATE();
				
				return 2;
			}
		else
			return 1;
	}
}
