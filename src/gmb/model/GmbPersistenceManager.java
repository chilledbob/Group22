package gmb.model;

import gmb.model.financial.FinancialManagement;
import gmb.model.group.Group;
import gmb.model.group.GroupManagement;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.tip.TipManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.salespointframework.core.database.Database;
import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;

public class GmbPersistenceManager 
{	
	private static final EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	
	private static final PersistentUserManager pum = new PersistentUserManager();
	
	public static void add(Member member){ pum.add(member); }
	
	public static void remove(Member member){ pum.remove(member.getIdentifier()); }
	
	public static void update(Member member){ pum.update(member); }
	
	public static Member get(UserIdentifier uid){ return pum.get(Member.class, uid); }
	
	public static Object get(Class<?> classType, int id){ return initContainer(classType, id); }
	
	public static PersiObject2 add(PersiObject2 obj)
	{
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		
		return em.find(obj.getClass(), obj.getId());
	}
	
	public static void add(Object obj)
	{
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
	}
	
	public static void remove(Object obj)
	{
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.remove(obj);
		em.getTransaction().commit();
	}
	
	public static void update(Object obj)
	{
		EntityManager em = emf.createEntityManager();
				
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
	
	public static void initLottery()
	{
		FinancialManagement fm = (FinancialManagement) initContainer(FinancialManagement.class, 1);
		MemberManagement mm = (MemberManagement) initContainer(MemberManagement.class, 1);
		GroupManagement gm = (GroupManagement) initContainer(GroupManagement.class, 1);
		TipManagement tm = (TipManagement) initContainer(TipManagement.class, 1);
		
		Lottery.Instanciate(fm, mm, gm, tm);
	}
	
	private static Object initContainer(Class<?> classType, int id)
	{
		EntityManager em = emf.createEntityManager();
		
		return em.find(classType, id);
	}
	
	public static Group createGroup(String name, Customer groupAdmin, String infoText){
		
		Group g = new Group(name, groupAdmin, infoText);
		
		return (Group) GmbPersistenceManager.add(g);
	}
		
}

////dummy required for unit tests of model code
/*
public class GmbPersistenceManager 
{		
	public static void get(Class<?> classType, int id){}
	
	public static void add(Object obj){}
	
	public static void remove(Object obj){}
	
	public static void update(Object obj){}
	
	public static void initLottery(){}	
}*/
