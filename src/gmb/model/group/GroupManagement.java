package gmb.model.group;



import gmb.model.PersiObject;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class GroupManagement extends PersiObject
{
	@Id
	protected int groupManagementId = 1;
	
	@OneToMany(mappedBy="groupManagementId")
	protected List<Group> groups = new LinkedList<Group>();
	
	public GroupManagement(){}
	
	public boolean removeGroup(Group group){ boolean result = groups.remove(group); DB_UPDATE();  return result; }
	
	public void addGroup(Group group){ groups.add(group); DB_UPDATE(); }
	
	public List<Group> getGroups(){ return groups; }
}
