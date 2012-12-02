package gmb.model.user;

import java.util.LinkedList;


public class GroupManagement 
{
	protected LinkedList<Group> groups;
	
//	@Deprecated
//	protected GroupManagement(){}
	
	public GroupManagement()
	{
		groups = new LinkedList<Group>();
	}
	
	public LinkedList<Group> getGroups(){ return groups; }
	public void addGroup(Group group){ groups.add(group); }
	public boolean removeGroup(Group group){ return groups.remove(group); }
}
