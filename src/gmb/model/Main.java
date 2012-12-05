package gmb.model;

import java.text.DateFormat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.salespointframework.core.database.*;
import org.springframework.stereotype.Component;
import org.salespointframework.core.shop.Shop;
import org.salespointframework.core.user.Capability;
import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;

import java.util.Date;

import gmb.model.user.MemberManagement;
import gmb.model.Lottery;
import gmb.model.user.Adress;
import gmb.model.user.Admin;
import gmb.model.user.MemberData;



@Component
public class Main {

	/**
	 * alles initialisieren
	 */
	public Main(){
		Shop.INSTANCE.initializePersistent();
		initData();
		
	}

	

	private void initData() {
		
		EntityManagerFactory emf = Database.INSTANCE.getEntityManagerFactory();
		EntityManager em = emf.createEntityManager();
		
		MemberManagement mm = new MemberManagement();
		
		Adress a = new Adress("a","b","c","d");
		Date d = new Date();
		MemberData md = new MemberData("a","b",d,"c","d",a);
		em.getTransaction().begin();
		em.persist(a);
		em.getTransaction().commit();
		em.getTransaction().begin();
		em.persist(md);
		em.getTransaction().commit();
		
		Admin user = new Admin("bob","pw",md);
		
		Lottery.Instanciate(null,mm,null,null);
		
		Lottery.getInstance().getMemberManagement().add(user);
		Lottery.getInstance().getMemberManagement().update(user);
		

		}
	

	}

