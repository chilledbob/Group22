package gmb.model.request;

import gmb.model.user.Group;
import gmb.model.user.Member;

public abstract class GroupRequest extends Request 
{
	protected  Group group;
	
	@Deprecated
	protected GroupRequest(){}
	
	public GroupRequest(Group group, Member member, String note)
	{
		super(member, note);
		this.group = group;
	}
	
	public Group getGroup(){ return group; }
}
