package gmb.model.user;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.salespointframework.core.database.Database;

@Entity
public class GroupManagement 
{
	@Id
	protected int groupManagementId;
	
	@OneToMany(mappedBy="groupManagementId")
	protected List<Group> groups;
	
	@SuppressWarnings("unchecked")
	public GroupManagement()
	{

	}
		
	public boolean removeGroup(Group group){ return groups.remove(group); }
	
	public void addGroup(Group group){ 
		groups.add(group);
	}
	
	public List<Group> getGroups(){ return groups; }
}
