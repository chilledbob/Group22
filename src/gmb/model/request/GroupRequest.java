package gmb.model.request;

import gmb.model.user.Customer;
import gmb.model.user.Group;

public abstract class GroupRequest extends Request 
{
	protected  Group group;
	
	@Deprecated
	protected GroupRequest(){}
	
	public GroupRequest(Group group, Customer member, String note)
	{
		super(member, note);
		this.group = group;
	}
	
	public Group getGroup(){ return group; }
}
