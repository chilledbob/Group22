package gmb.model.user;

import java.util.LinkedList;


public class GroupManagement {

	private LinkedList<Group> groups;
	
	public LinkedList<Group> getGroups(){
		return groups;
	}
	
	public void addGroup(Group group){
		groups.add(group);
	}
	
	public void removeGroup(Group group){
		groups.remove(group);
	}
	
	
}
