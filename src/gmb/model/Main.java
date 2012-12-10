package gmb.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.salespointframework.core.shop.Shop;

import gmb.model.Lottery;
import gmb.model.user.*;
import gmb.model.tip.*;
import gmb.model.financial.*;
import gmb.model.request.*;

@Component
public class Main {

	/**
	 * alles initialisieren
	 */
	public Main(){
		Shop.INSTANCE.initializePersistent();
		initMm();
		GmbPersistenceManager.initLottery();
		initData();
	}

	
	//Testdata
	
	private void initMm(){
		MemberManagement mm = new MemberManagement(1);
		//FinancialManagement fm = new FinancialManagement();
		GmbPersistenceManager.add(new TipManagement());
		GmbPersistenceManager.add(new GroupManagement(1));
		GmbPersistenceManager.add(mm);
	}
	
	private void initData() {
		
		Adress a = new Adress("a","b","c","d");
		DateTime d = new DateTime();
		MemberData md = new MemberData("a","b",d,"c","d",a);
		Admin user = new Admin("bob","bob",md);		
		Lottery.getInstance().getMemberManagement().addMember(user);
		
		GmbPersistenceManager.add(a);
		GmbPersistenceManager.add(md);
		GmbPersistenceManager.add(user);
		GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
		
		RealAccountData rad = new RealAccountData("0010","0815");
		LotteryBankAccount lba = new LotteryBankAccount(rad);
		Adress aa = new Adress("e","f","g","h");
		DateTime da = new DateTime();
		MemberData mda = new MemberData("i","j",da,"k","l",aa);
		
		Customer c = new Customer("UserTroll","UserTroll",mda,lba);
		Lottery.getInstance().getMemberManagement().addMember(c);
		lba.setOwner(c);
		
		Adress ab = new Adress("ee","f","g","h");
		DateTime db = new DateTime();
		MemberData mdb = new MemberData("i","j",db,"k","l",ab);
		
		GmbPersistenceManager.add(c);
		
		c.sendDataUpdateRequest(mdb, "");

		//Group g = new Group("The Savages",c,"Don't hunt what you can't kill!");
		//GmbPersistenceManager.add(g);
		//GmbPersistenceManager.update(Lottery.getInstance().getGroupManagement());
		GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
		
		
		}
	

	}

