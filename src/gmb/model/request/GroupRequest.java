package gmb.model.request;

import gmb.model.user.Group;
import gmb.model.user.Member;

public class GroupRequest extends Request 
{
	protected  Group group;
	
	public GroupRequest(Group group, Member member, String note)
	{
		super(member, note);
		this.group = group;
	}
	
	public Group getGroup(){ return group; }
}
