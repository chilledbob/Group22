package gmb.model.user;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.salespointframework.core.database.Database;

public class GroupManagement 
{
	private EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	protected List<Group> groups;
	
	@SuppressWarnings("unchecked")
	public GroupManagement()
	{
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("Select g FROM Group g", Group.class);
		groups = (LinkedList<Group>) query.getResultList();
	}
	
//	@Deprecated
//	protected GroupManagement(){}
	
	public boolean removeGroup(Group group){ return groups.remove(group); }
	
	public void addGroup(Group group){ groups.add(group); }
	
	public List<Group> getGroups(){ return groups; }
}
