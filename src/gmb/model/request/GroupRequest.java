package gmb.model.request;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import gmb.model.user.Group;
import gmb.model.user.Member;

@Entity
public class GroupRequest extends Request 
{
	@ManyToOne
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
