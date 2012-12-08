package gmb.model.user;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GroupManagement 
{
	@Id
	protected int groupManagementId = 1;
	
	@OneToMany(mappedBy="groupManagementId")
	protected List<Group> groups;
	
	@Deprecated
	protected GroupManagement(){}
	
	public GroupManagement(int dummy)
	{
 		groups = new LinkedList<Group>();
	}
	
	public boolean removeGroup(Group group){ return groups.remove(group); }
	
	public void addGroup(Group group){ groups.add(group); }
	
	public List<Group> getGroups(){ return groups; }
}
