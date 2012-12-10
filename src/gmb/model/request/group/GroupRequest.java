package gmb.model.request.group;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.request.Request;

@Entity
public abstract class GroupRequest extends Request 
{
	@ManyToOne
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
