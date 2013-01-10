package gmb.model;

import java.util.List;

import gmb.model.financial.FinancialManagement;
import gmb.model.group.Group;
import gmb.model.group.GroupManagement;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.request.data.MemberDataUpdateRequest;
import gmb.model.tip.TipManagement;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.Draw;
import gmb.model.tip.draw.TotoEvaluation;
import gmb.model.tip.draw.WeeklyLottoDraw;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.RollbackException;
import javax.servlet.http.HttpSession;

import org.eclipse.persistence.exceptions.DatabaseException;
import org.salespointframework.core.database.Database;
import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;

public class GmbPersistenceManager 
{	
	private static final EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
	
	private static final PersistentUserManager pum = new PersistentUserManager();
	
	public static Member add(Member member){ pum.add(member); return pum.get(Member.class, member.getIdentifier()); }
	
	public static void remove(Member member){ pum.remove(member.getIdentifier()); }
	
	public static void update(Member member){ pum.update(member); }
	
	public static Member get(UserIdentifier uid){ return pum.get(Member.class, uid); }
	
	public static Object get(Class<?> classType, int id){EntityManager em = emf.createEntityManager(); return em.find(classType, id); }
	
	public static Object get(Class<?> classType){
		return initContainer(classType,0);
	}
	
	public static Group getGroup(String name){
		EntityManager em = emf.createEntityManager();
		Group g = null;
		String q = "SELECT g FROM Group g WHERE g.name='"+name+"'";
		Query query = em.createQuery(q);
		try{
			g = (Group) query.getSingleResult();
		}
		catch (NoResultException e){
			System.out.println("Die Gruppe "+name+" konnte nicht gefunden werden "+e.getMessage());
		}
		return g;
	}

	public static void login(Member user, HttpSession session) {
		pum.login(user, session);
		
	}
	

	public static void logout(HttpSession session) {
		pum.logout(session);
		
	}
	
	public static Timer add(Timer obj){
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.persist(obj);
		em.getTransaction().commit();
		
		return em.find(obj.getClass(), obj.getId());
	}
	
	public static PersiObject add(PersiObject obj)
	{
	EntityManager em = emf.createEntityManager();

	em.getTransaction().begin();
	em.persist(obj);
	em.getTransaction().commit();

	return em.find(obj.getClass(), obj.getId());
	}
	
	public static PersiObjectSingleTable add(PersiObjectSingleTable obj)
	{
	EntityManager em = emf.createEntityManager();

	em.getTransaction().begin();
	em.persist(obj);
	em.getTransaction().commit();

	return em.find(obj.getClass(), obj.getId());
	}
	
	public static void remove(Object obj)
	{
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.remove(em.merge(obj));
		em.getTransaction().commit();
	}
	
	public static void update(Timer obj){
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
	
	public static void update(PersiObject obj)
	{
		EntityManager em = emf.createEntityManager();
				
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
	
	public static void update(PersiObjectSingleTable obj)
	{
		EntityManager em = emf.createEntityManager();
				
		em.getTransaction().begin();
		em.merge(obj);
		em.getTransaction().commit();
	}
	
	public static void initLottery()
	{
		FinancialManagement fm = (FinancialManagement) initContainer(FinancialManagement.class, 0);
		MemberManagement mm = (MemberManagement) initContainer(MemberManagement.class, 0);
		GroupManagement gm = (GroupManagement) initContainer(GroupManagement.class,0);
		TipManagement tm = (TipManagement) initContainer(TipManagement.class, 0);
		
		Lottery.Instanciate(fm, mm, gm, tm);
		
		Lottery.getInstance().setTimer((Timer) initContainer(Timer.class, 0));
	}
	
	private static Object initContainer(Class<?> classType, int id)
	{
		EntityManager em = emf.createEntityManager();
		
		String q = "SELECT m FROM "+classType.getSimpleName()+" m";
		Query query= em.createQuery(q);
		if(query.getResultList().isEmpty()){ return null; }
		else { return query.getResultList().get(id); }
		
	}
	
	public static List<?> getList(Class<?> classType, int id){
		EntityManager em = emf.createEntityManager();
		String q = "SELECT w FROM "+classType.getSimpleName()+"_SingleTip d, WeeklyLottoTip w "+
				"WHERE d.DRAW_PERSISTENCEID = w.DRAW_PERSISTENCEID AND " +
				"d.DRAW_PERSISTENCEID = "+id;
		Query query = em.createQuery(q);
		return query.getResultList();
	}
	
	public static Object refresh(Object obj){
		EntityManager em = emf.createEntityManager();
		
		try {
			em.getTransaction().begin();
			obj = em.merge(obj);
			try {
				em.refresh(obj);
			} catch (EntityNotFoundException e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
			em.getTransaction().commit();
		} catch (RollbackException e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return obj;
	}
	
}

//dummy required for unit tests of model code

//public class GmbPersistenceManager 
//{		
//	public static Member add(Member obj){ return obj; }
//	public static void remove(Member obj){}
//	public static void update(Member obj){}
//	
//	public static PersiObject add(PersiObject obj){ return obj; }	
//	public static void remove(PersiObject obj){}	
//	public static void update(PersiObject obj){}
//	
//	public static void initLottery(){}	
//	
//	public static Group getGroup(String name){ return null; }
//
//	public static void login(Member user, HttpSession session) {}
//	
//	public static Object get(Class<?> classType, int id){ return null; }
//	public static Object get(Class<?> classType){ return null; }
//	public static Member get(UserIdentifier uid){ return null; }
//	
//	public static void logout(HttpSession session) {}
//	
//	public static void remove(Object obj){}
//	
//	public static void update(Object obj){}
//	
//	public static PersiObjectSingleTable add(PersiObjectSingleTable obj){return obj;}
//}
