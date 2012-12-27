package gmb.model.group;

import gmb.model.GmbPersistenceManager;
import gmb.model.PersiObject;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import gmb.model.group.Group;

@Entity
public class GroupManagement extends PersiObject
{	
	@OneToMany(mappedBy="groupManagementId",fetch=FetchType.EAGER)
	protected List<Group> groups;
	
	@Deprecated
	protected GroupManagement(){}
	
	public GroupManagement(Object dummy)
	{
		groups = new LinkedList<Group>();
	}
	
	public boolean removeGroup(Group group){ boolean result = groups.remove(group); DB_UPDATE();  return result; }
	
	public void addGroup(Group group){ groups.add(group); DB_UPDATE(); }
	
	public List<Group> getGroups(){ return groups; }
}
