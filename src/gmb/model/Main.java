package gmb.model;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.salespointframework.core.shop.Shop;

import gmb.model.Lottery;
import gmb.model.tip.*;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;
import gmb.model.financial.*;
import gmb.model.financial.container.RealAccountData;
import gmb.model.group.GroupManagement;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.member.MemberType;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;
import gmb.model.group.Group;

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
		MemberManagement mm = new MemberManagement(null);
		FinancialManagement fm = new FinancialManagement(null,null);
		GmbPersistenceManager.add(new TipManagement(null));
		GmbPersistenceManager.add(new GroupManagement(null));
		GmbPersistenceManager.add(fm);
		GmbPersistenceManager.add(mm);
	}

	private void initData() {

		Adress a = new Adress("a","b","c","d");
		DateTime d = new DateTime();
		MemberData md = new MemberData("a","b",d,"c","d",a);
		Member admin = new Member("bob","bob",md, MemberType.Admin);		
		Lottery.getInstance().getMemberManagement().addMember(admin);

		//GmbPersistenceManager.add(a);
		//GmbPersistenceManager.add(md);
		GmbPersistenceManager.add(admin.getMemberData());
		GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());

		
		RealAccountData rad = GmbFactory.new_RealAccountData("0010","0815");
		LotteryBankAccount lba = GmbFactory.new_LotteryBankAccount(rad);
		Adress aa = new Adress("e","f","g","h");
		DateTime da = new DateTime();
		MemberData mda = new MemberData("i","j",da,"k","l",aa);

		Customer c = new Customer("UserTroll","UserTroll",mda,lba);
		Lottery.getInstance().getMemberManagement().addMember(c);
		lba.setOwner(c);

		Adress ab = new Adress("ee","f","g","h");
		DateTime db = new DateTime();
		MemberData mdb = new MemberData("i","j",db,"k","l",ab);

//		GmbPersistenceManager.add(aa);
//		GmbPersistenceManager.add(mda);
//		GmbPersistenceManager.add(rad);
//		GmbPersistenceManager.add(lba);

		
		c.sendDataUpdateRequest(mdb, "");
		GmbPersistenceManager.update(c);
		Group g = GmbFactory.new_Group("The Savages",c,"Don't hunt what you can't kill!");
		//GmbPersistenceManager.add(g);
		
		GmbPersistenceManager.update(Lottery.getInstance().getGroupManagement());
		GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
		//GmbPersistenceManager.update(Lottery.getInstance().getFinancialManagement());

		}
	

	}

