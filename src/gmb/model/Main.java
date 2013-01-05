package gmb.model;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.stereotype.Component;
import org.salespointframework.core.shop.Shop;
import org.salespointframework.core.user.UserIdentifier;

import de.msiggi.Sportsdata.Webservices.Matchdata;
import de.msiggi.Sportsdata.Webservices.SportsdataSoap;
import de.msiggi.Sportsdata.Webservices.SportsdataSoapProxy;

import gmb.model.Lottery;
import gmb.model.request.ExternalTransactionRequest;
import gmb.model.tip.*;
import gmb.model.tip.draw.DailyLottoDraw;
import gmb.model.tip.draw.WeeklyLottoDraw;
import gmb.model.tip.draw.container.FootballGameData;
import gmb.model.tip.tip.single.WeeklyLottoTip;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;
import gmb.model.financial.*;
import gmb.model.financial.container.RealAccountData;
import gmb.model.financial.container.TipTicketPrices;
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
		if(GmbPersistenceManager.get(MemberManagement.class) == null){
			GmbFactory.new_FinancialManagement(GmbFactory.new_TipTicketPrices(), GmbFactory.new_ReceiptsDistribution());
			GmbFactory.new_MemberManagement();
			GmbFactory.new_TipManagement();
			GmbFactory.new_GroupManagement();
		}
	}

	private void initData() {
		if(GmbPersistenceManager.get(new UserIdentifier("admin")) == null){
		
		Lottery.getInstance().getTimer().setReferenceDate(new DateTime(2012,7,31,12,0,0,0));
		Adress a = GmbFactory.new_Adress("a","b","c","d");
		DateTime d = new DateTime();
		MemberData md = GmbFactory.new_MemberData("a","b",d,"c","d",a);
		Member admin = new Member("admin","admin",md, MemberType.Admin);		
		Lottery.getInstance().getMemberManagement().addMember(admin);

		//GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
	
		RealAccountData rad = GmbFactory.new_RealAccountData("0010","0815");
		LotteryBankAccount lba = GmbFactory.new_LotteryBankAccount(rad);
		Adress aa = new Adress("e","f","g","h");
		DateTime da = new DateTime();
		MemberData mda = new MemberData("Vorname","Nachname",da,"k","l",aa);

		RealAccountData raa = GmbFactory.new_RealAccountData("0010","0815");
		LotteryBankAccount lbab = GmbFactory.new_LotteryBankAccount(raa);
		Adress abb = GmbFactory.new_Adress("e","f","g","h");
		DateTime daa = new DateTime();
		MemberData mdaa = GmbFactory.new_MemberData("Vorname","Nachname",daa,"k","l",abb);
		
		Customer c = new Customer("nutzer","nutzer",mda,lba);
		Lottery.getInstance().getMemberManagement().addMember(c);
		lba.setOwner(c);
		
		Customer c1 = new Customer("UserTroll","UserTroll",mdaa,lbab);
		lbab.setOwner(c1);

		Adress ab = new Adress("ee","f","g","h");
		DateTime db = new DateTime();
		MemberData mdb = new MemberData("i","j",db,"k","l",ab);
		
		//c.sendDataUpdateRequest(mdb, "");
		
		//GmbPersistenceManager.update(c);
		Group g = GmbFactory.new_Group("The Savages",c,"Don't hunt what you can't kill!");
		//GmbPersistenceManager.add(g);
		
		//GmbPersistenceManager.update(Lottery.getInstance().getGroupManagement());
		//GmbPersistenceManager.update(Lottery.getInstance().getMemberManagement());
		//GmbPersistenceManager.update(Lottery.getInstance().getFinancialManagement());
		
		Adress aaa = GmbFactory.new_Adress("aa","bb","cc","dd");
		DateTime dd = new DateTime();
		MemberData mdd = GmbFactory.new_MemberData("a","b",dd,"c","d",aaa);
		Member notary = new Member("arschkrampe","123",mdd, MemberType.Notary);		
		Lottery.getInstance().getMemberManagement().addMember(notary);
		
		DateTime currentTime = Lottery.getInstance().getTimer().getDateTime();
		WeeklyLottoDraw draw1 = GmbFactory.new_WeeklyLottoDraw(currentTime.plusDays(7));
		DailyLottoDraw draw2 = GmbFactory.new_DailyLottoDraw(currentTime.plusDays(1));
		c.getBankAccount().sendExternalTransactionRequest(new CDecimal(100), "gimme money!");
		
		assert Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests().size()==1 : "AAAAAAAAAAAA!";
		
		for(ExternalTransactionRequest request : Lottery.getInstance().getFinancialManagement().getExternalTransactionRequests())
		{
			if(request.getTransaction().getAmount().compareTo(new CDecimal(5000)) < 1)
			{
				System.out.println(request.accept());
			}
			else
			{
				request.getMember().addNotification("No so much money! You evil!");
			}
		}
		
		
		
//		------------------------------------------------------------------------------------
//		SportsdataSoap sportDataSoup = new SportsdataSoapProxy().getSportsdataSoap();
//		Matchdata[] data = null;
//		Matchdata[] matchdaydata = new Matchdata[9];
//		ArrayList<FootballGameData> footballData = new ArrayList<FootballGameData>();
//		
//		try {
//			data=sportDataSoup.getMatchdataByLeagueSaison("bl1", "2012");
//		} catch (RemoteException e) {
//			e.printStackTrace();
//		}
//		
//		for(int i = 0; i < 34; i++){
//			footballData.clear();
//			for(int j = 0; j < 9; j++){
//				matchdaydata[j] = data[i*9+j];
//			}
//			for(int k = 0; k < 9; k++){
//				FootballGameData fgd = GmbFactory.new_FootballGameData(new DateTime(matchdaydata[k].getMatchDateTime().getTime()), matchdaydata[k].getNameTeam1(), matchdaydata[k].getNameTeam2());
//				footballData.add(fgd);
//			}
//			
//			GmbFactory.new_TotoEvaluation(new DateTime(footballData.get(0).getMatchDay()), footballData);
//		}
		
		}
		
	}
}

