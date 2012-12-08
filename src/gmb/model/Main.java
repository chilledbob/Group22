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
		initData();
		GmbPersistenceManager.initLottery();
	}

	
	//Testdata
	private void initData() {
						
		MemberManagement mm = new MemberManagement(1);
		
		Adress a = new Adress("a","b","c","d");
		DateTime d = new DateTime();
		MemberData md = new MemberData("a","b",d,"c","d",a);
		Admin user = new Admin("bob","pw",md);		
		mm.addMember(user);
		
		GmbPersistenceManager.add(a);
		GmbPersistenceManager.add(md);
		GmbPersistenceManager.add(user);

		GmbPersistenceManager.add(mm);
		
		}
	

	}

