package gmb.model;

import gmb.model.user.Member;
import gmb.model.user.MemberManagement;
import gmb.model.user.GroupManagement;
import gmb.model.financial.FinancialManagement;
import gmb.model.tip.TipManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.salespointframework.core.database.Database;
import org.salespointframework.core.user.PersistentUserManager;

public class GmbPersistenceManager 
{	
	private static final EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	
	private static final PersistentUserManager pum = new PersistentUserManager();
	
	public static void add(Member member){ pum.add(member); }
	
	public static void remove(Member member){ pum.remove(member.getIdentifier()); }
	
	public static void update(Member member){ pum.update(member); }
	
	public static Object get(Class<?> classType, int id){ return initContainer(classType, id); }
	
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
		
}
