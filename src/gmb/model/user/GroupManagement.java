package gmb.model.user;

import java.util.LinkedList;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.salespointframework.core.database.Database;



public class GroupManagement {
	
	private EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	private LinkedList<Group> groups;
	
	public GroupManagement(){
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select g FROM Group g", Group.class);
		groups = (LinkedList<Group>) query.getResultList();
	}
	
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
