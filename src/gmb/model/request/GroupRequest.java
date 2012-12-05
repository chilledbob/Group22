package gmb.model.request;

import gmb.model.user.Customer;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import gmb.model.user.Group;

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
