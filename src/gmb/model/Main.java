package gmb.model;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.joda.time.DateTime;
import org.salespointframework.core.database.*;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Component;
import org.salespointframework.core.shop.Shop;

import gmb.model.user.MemberManagement;
import gmb.model.Lottery;
import gmb.model.user.Adress;
import gmb.model.user.Admin;
import gmb.model.user.MemberData;
import gmb.model.user.Group;
import gmb.model.user.GroupManagement;
import gmb.model.user.Member;



@Component
public class Main {

	/**
	 * alles initialisieren
	 */
	public Main(){
		Shop.INSTANCE.initializePersistent();
		GmbPersistenceManager.initLottery();	
	}

	
	//Testdata
	private void initData() {
		
		EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		MemberManagement mm = new MemberManagement(0);
		GroupManagement gm = new GroupManagement(0);
		
		Lottery.Instanciate(null,mm,gm,null);
		
		Adress a = new Adress("a","b","c","d");
		DateTime d = new DateTime();
		MemberData md = new MemberData("a","b",d,"c","d",a);
		Admin user = new Admin("bob","pw",md);
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(md);
		em.getTransaction().commit();
		
		Lottery.getInstance().getMemberManagement().addMember(user);
		
		for(Member m : Lottery.getInstance().getMemberManagement().getMembers()){
			if(m.getIdentifier() == user.getIdentifier()){ System.out.printf("läuft", m); break;}
			else{ System.out.printf("nixläuft :(", user);}
		}		
		Class<?> classtest= user.getClass();
		System.out.printf(classtest.getSimpleName(), user);
		//Group group = new Group("trolle",null,"ne Gruppe voller Trolle");
		//Lottery.getInstance().getGroupManagement().addGroup(group);
		}
	

	}

