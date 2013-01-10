package gmb.controller;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import gmb.model.CDecimal;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.financial.LotteryBankAccount;
import gmb.model.financial.container.RealAccountData;
import gmb.model.group.Group;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberType;
import gmb.model.member.container.Adress;
import gmb.model.member.container.MemberData;
import gmb.model.request.data.MemberDataUpdateRequest;

import org.joda.time.DateTime;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

	

@Controller	
	public class Admin_MemberController {
	private static UserIdentifier UID;
	private static UserIdentifier TEMPUID;

//--------------------------MitgliederVerwaltung---------------------------------------
//--------------------------------------------------------------------------------------
	
	@RequestMapping(value="/adminMitglVerwStart",method=RequestMethod.GET)
	public ModelAndView employeeMitglVerw(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		UID = uid;
		Member currentAdmin = GmbPersistenceManager.get(UID);

		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwMitglieder");
		mav.addObject("currentUser",currentAdmin);

		mav.addObject("memberList", getMemberList_depOnUser());
		return mav;
	}
	
	@RequestMapping(value="/adminMember",method=RequestMethod.GET)
	public ModelAndView adminMember(ModelAndView mav,
			@RequestParam("filter") String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);

		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwMitglieder");
		mav.addObject("memberList", filterMemberList(filter, getMemberList_depOnUser()));
		mav.addObject("currentUser",currentAdmin);
		return mav;
	}
	
	@RequestMapping(value="/adminEditMember",method=RequestMethod.GET)
	public ModelAndView adminEditMember(ModelAndView mav,
			@RequestParam("editUid") UserIdentifier editUid){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		TEMPUID = editUid;
		
		if(GmbPersistenceManager.get(TEMPUID).getType().toString().equals("Customer")){
			Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);
			mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomer");
			mav.addObject("editMember",editCustomer);
		}
		else{
			Member editMember = GmbPersistenceManager.get(TEMPUID);
			mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editMember");
			mav.addObject("editMember",editMember);
		}
				
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("comment","Bitte führen Sie ihre Änderungen durch .");
		return mav;
	}
	
//-----------------------------------EDIT CUSTOMER---------------------------------------------
	
	@RequestMapping(value="/adminChangeCustomerUserData",method=RequestMethod.POST)
	public ModelAndView changeCustomer(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("email") String email,
			@RequestParam("street") String street,
			@RequestParam("hNumber") String hNumber,
			@RequestParam("plz") String plz,
			@RequestParam("city") String city) {
		
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);
		
		boolean changeTest = false;
		if(!vname.equals(editCustomer.getMemberData().getFirstName())) {changeTest = true;} 
		if(!nname.equals(editCustomer.getMemberData().getLastName())) {changeTest = true;}  
		if(!email.equals(editCustomer.getMemberData().getEMail())) {changeTest = true;} 
		if(!street.equals(editCustomer.getMemberData().getAdress().getStreetName())) {changeTest = true;} 
		if(!hNumber.equals(editCustomer.getMemberData().getAdress().getHouseNumber())) {changeTest = true;}  
		if(!plz.equals(editCustomer.getMemberData().getAdress().getPostCode())) {changeTest = true;} 
		if(!city.equals(editCustomer.getMemberData().getAdress().getTownName())) {changeTest = true;}  
		
		if(changeTest){
			Adress newAdress = GmbFactory.new_Adress(street,hNumber,plz,city);
			MemberData newMemberData = GmbFactory.new_MemberData(vname,nname, editCustomer.getMemberData().getBirthDate(),editCustomer.getMemberData().getPhoneNumber(), email, newAdress);
			editCustomer.sendDataUpdateRequest(newMemberData, "Geändert vom "+currentAdmin.getTypeAsGerman()+".").accept();
			mav.addObject("comment","Ihr Änderungswunsch wurde gewährt.");
		}else{
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomer");
		mav.addObject("editMember",editCustomer);
		mav.addObject("currentUser", currentAdmin);
		return mav;
	}
	
	@RequestMapping(value="/adminEditCustomerPassword",method=RequestMethod.GET)
	public ModelAndView editCustomerPassword(ModelAndView mav){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomerPassword");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("comment", "Bitte geben Sie das alte und das neue Passwort ein !");  
		mav.addObject("editMember",editCustomer);
		return mav;
	}

	@RequestMapping(value="/adminChangeCustomerPassword",method=RequestMethod.POST)
	public ModelAndView changeCustomerPassword(ModelAndView mav,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);
		
		if((oldPassword == "" || newPassword == "" )){
			mav.addObject("comment", "Sie müssen alle Felder Füllen !");
		}
		else if(editCustomer.verifyPassword(oldPassword)){
			editCustomer.changePassword(newPassword);
			GmbPersistenceManager.update(editCustomer);
			mav.addObject("comment", "Das Passwort wurde geändert.");
		}
		else{
			mav.addObject("comment", "Sie haben ein falsches Passwort eingegeben !");
		}
			
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomerPassword");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("editMember",editCustomer);
		return mav;
	}
	
	@RequestMapping(value="/adminEditCustomerRealAccount",method=RequestMethod.GET)
	public ModelAndView editCustomerRealAccount(ModelAndView mav) {
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);	
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomerBankAccount");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("editMember",editCustomer);
		mav.addObject("comment", "Führen Sie Ihre Änderungswünsche im entsprechenden Feld aus !");
		return mav;
	}
	
	@RequestMapping(value="/adminChangeCustomerRealAccount",method=RequestMethod.POST)
	public ModelAndView changeCustomerRealAccount(ModelAndView mav,
			@RequestParam("newBankCode") String newBankCode,
			@RequestParam("newAccountNumber") String newAccountNumber) {
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Customer editCustomer = (Customer) GmbPersistenceManager.get(TEMPUID);
		
		boolean changeTest = false;
		if(!newBankCode.equals(editCustomer.getBankAccount().getRealAccountData().getBankCode())){changeTest=true;}
		if(!newAccountNumber.equals(editCustomer.getBankAccount().getRealAccountData().getAccountNumber())){changeTest=true;}
		
		if((newBankCode == "" || newAccountNumber == "" )){
			mav.addObject("comment", "Sie müssen alle Felder ausfüllen !");
		}
		else if(changeTest){
			editCustomer.getBankAccount().sendDataUpdateRequest(GmbFactory.new_RealAccountData(newBankCode, newAccountNumber), "Geändert vom "+currentAdmin.getTypeAsGerman()+".").accept();
			mav.addObject("comment","Die Daten wurden geändert.");
		} 
		else if(!changeTest){
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomerBankAccount");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("editMember",editCustomer);
		return mav;
	}
	
//-----------------------------------EDIT MEMBER---------------------------------------------

	@RequestMapping(value="/adminChangeMemberData",method=RequestMethod.POST)
	public ModelAndView adminChangeMemberData(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname) {
		
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Member editMember = GmbPersistenceManager.get(TEMPUID);
		
		boolean changeTest = false;
		if(!vname.equals(editMember.getMemberData().getFirstName())) {changeTest = true;} 
		if(!nname.equals(editMember.getMemberData().getLastName())) {changeTest = true;}  
		
		if(changeTest){
			MemberData newMemberData = GmbFactory.new_MemberData(vname,nname, editMember.getMemberData().getBirthDate(),
					editMember.getMemberData().getPhoneNumber(),editMember.getMemberData().getEMail(), 
					editMember.getMemberData().getAdress());
			
			editMember.sendDataUpdateRequest(newMemberData, "Geändert von "+currentAdmin.getTypeAsGerman()+".").accept();
			mav.addObject("comment","Ihr Änderungswunsch wurde gewährt.");
		}else{
			mav.addObject("comment","Sie haben keine Änderungen vorgenommen.");
		}
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editMember");
		mav.addObject("editMember",editMember);
		mav.addObject("currentUser", currentAdmin);
		return mav;
	}
	
	@RequestMapping(value="/adminEditMemberPassword",method=RequestMethod.GET)
	public ModelAndView adminEditMemberPassword(ModelAndView mav){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Member editMember = GmbPersistenceManager.get(TEMPUID);
		
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editMemberPassword");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("comment", "Bitte geben Sie das alte und das neue Passwort ein !");  
		mav.addObject("editMember",editMember);
		return mav;
	}

	@RequestMapping(value="/adminChangeMemberPassword",method=RequestMethod.POST)
	public ModelAndView changeMemberPassword(ModelAndView mav,
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword) {
		Member currentAdmin = GmbPersistenceManager.get(UID);
		Member editMember = GmbPersistenceManager.get(TEMPUID);
		
		if((oldPassword == "" || newPassword == "" )){
			mav.addObject("comment", "Sie müssen alle Felder Füllen !");
		}
		else if(editMember.verifyPassword(oldPassword)){
			editMember.changePassword(newPassword);
			GmbPersistenceManager.update(editMember);
			mav.addObject("comment", "Das Passwort wurde geändert.");
		}
		else{
			mav.addObject("comment", "Sie haben ein falsches Passwort eingegeben !");
		}
			
		mav.setViewName("admin/mitgliederVerwaltung/editMember/admin_editCustomerPassword");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("editMember",editMember);
		return mav;
	}
	
//--------------------------------CANCEL STUFF-------------------------------------------------
	
	@RequestMapping(value="/adminCancelEditing",method=RequestMethod.GET)
	public ModelAndView cancelEditingCustomer(ModelAndView mav){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		TEMPUID = null;
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwMitglieder");
		mav.addObject("currentUser", currentAdmin);
		mav.addObject("memberList", getMemberList_depOnUser());
		return mav;	
	}
	
	@RequestMapping(value="/adminCancel",method=RequestMethod.GET)
	public ModelAndView adminCancel(ModelAndView mav){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		mav.setViewName("admin/admin");
		mav.addObject("currentUser",currentAdmin);
		return mav;
	}
	
//-------------------------------DELETE MEMBER----------------------------------------
	
	@RequestMapping(value="/adminDeleteMember",method=RequestMethod.GET)
	public ModelAndView adminDeleteMember(ModelAndView mav,
			@RequestParam ("deleteUid") UserIdentifier deleteUid){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		if(GmbPersistenceManager.get(deleteUid).getType() == MemberType.Customer){
			
			Customer fuckingCustomer = (Customer)GmbPersistenceManager.get(deleteUid);
			for(Group g : fuckingCustomer.getGroups()){
				g.resign(fuckingCustomer);
			}
		
//			Lottery.getInstance().getMemberManagement().getMember(deleteUid).deactivateAccount();
			GmbPersistenceManager.get(deleteUid).deactivateAccount();					
		}else{
			Member member = GmbPersistenceManager.get(deleteUid);		
			GmbPersistenceManager.remove(member);
			Lottery.getInstance().getMemberManagement().removeMember(member);
		}
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwMitglieder");
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("memberList", Lottery.getInstance().getMemberManagement().getMembers());
		return mav;		
	}
	
//-----------------------------------NEW MEMBER----------------------------------------
	
	@RequestMapping(value="/adminNewMember",method=RequestMethod.GET)
	public ModelAndView adminNewMember(ModelAndView mav){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwNewMember");
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("comment", "Bitte geben Sie die Daten ein .");
		mav.addObject("vorname", "");
		mav.addObject("nachname", "");
		mav.addObject("password", "");
		mav.addObject("uid", "");
		mav.addObject("currentUser",currentAdmin);
		return mav;		
	}
	
	@RequestMapping(value="/adminCreateMember",method=RequestMethod.POST)
	public ModelAndView adminNewMember(ModelAndView mav,
			@RequestParam("vname") String vname,
			@RequestParam("nname") String nname,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("password") String password,
			@RequestParam("typ") String userType){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		if(GmbPersistenceManager.get(uid)!= null){
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("password", password);
			mav.addObject("uid", "");
			mav.addObject("comment", new String("Der Nutzername wird schon verwendet !"));
		}

		else if(this.testeEingabeFehler(vname, nname, uid.getIdentifier(), password)){
			mav.addObject("vorname", vname);
			mav.addObject("nachname", nname);
			mav.addObject("password", "");
			mav.addObject("uid", uid.toString());
			mav.addObject("comment", new String("Alle Felder fuellen !!!"));
		}
		else{
			MemberType memberType = null;
			if(userType.equals("admin"))memberType = MemberType.Admin;
			if(userType.equals("employee"))memberType = MemberType.Employee;
			if(userType.equals("notary"))memberType = MemberType.Notary;
			
				Adress adress = GmbFactory.new_Adress("a","b","c","d");
				MemberData memberData = GmbFactory.new_MemberData(vname, nname, new DateTime(1970,10,16,0,0), "0735643", "member@mail.lotto", adress);
				Member admin = new Member(uid.toString(), password, memberData, memberType);
				Lottery.getInstance().getMemberManagement().addMember(admin);
				admin.activateAccount();
				mav.addObject("comment", uid.toString()+" wurde als "+memberType.toString()+" erstellt.");
			}

		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwNewMember");
		mav.addObject("currentUser",currentAdmin);
		return mav;		
	}
	
//-----------------------------------REQUESTS----------------------------------------
	
	@RequestMapping(value="/adminChangeReq_forAdmin",method=RequestMethod.GET)
	public ModelAndView adminMitglVerw_ChangeReq(ModelAndView mav,
			@RequestParam("filter") String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		List<MemberDataUpdateRequest> requestList = filterRequestList(filter, Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests());
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forAdmin");
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("requestList",requestList);
		mav.addObject("filter",filter);
		return mav;
	}
	
	@RequestMapping(value="/adminChangeReq_forEmployee",method=RequestMethod.GET)
	public ModelAndView adminChangeReq_forEmployee(ModelAndView mav,
			@RequestParam("filter") String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		List<MemberDataUpdateRequest> requestList = filterRequestList(filter, Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests());
		
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forEmployee");
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("requestList",requestList);
		mav.addObject("filter",filter);

		return mav;
	}
	
	@RequestMapping(value="/adminAcceptDataReq",method=RequestMethod.GET)
	public ModelAndView acceptDataReq(ModelAndView mav,
			@RequestParam ("reqID" ) int reqID,
			@RequestParam ("filter" ) String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		MemberDataUpdateRequest currentRequest = getRequest(reqID);
		currentRequest.accept();
		if(currentAdmin.getType().toString().equals("Admin")){
			mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forAdmin");
			mav.addObject("requestList",filterRequestList("All", Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests()));
		}else{
			mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forEmployee");
			mav.addObject("requestList",filterRequestList("Customer", Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests()));
		}
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("filter",filter);
		return mav;
	}
	
	@RequestMapping(value="/adminRefuseDataReq",method=RequestMethod.GET)
	public ModelAndView refuseDataReq(ModelAndView mav,
			@RequestParam ("reqID" ) int reqID,
			@RequestParam ("filter" ) String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		MemberDataUpdateRequest currentRequest = getRequest(reqID);
		currentRequest.refuse();
		if(currentAdmin.getType().toString().equals("Admin")){
			mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forAdmin");
			mav.addObject("requestList",filterRequestList("All", Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests()));
		}else{
			mav.setViewName("admin/mitgliederVerwaltung/mitglVerwChangeReq_forEmployee");
			mav.addObject("requestList",filterRequestList("Customer", Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests()));
		}
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("filter",filter);
		return mav;
	}
	
	@RequestMapping(value="/adminShowDataReq",method=RequestMethod.GET)
	public ModelAndView adminShowDataReq(ModelAndView mav,
			@RequestParam ("reqID" ) int reqID,
			@RequestParam ("filter" ) String filter){
		Member currentAdmin = GmbPersistenceManager.get(UID);
		
		MemberDataUpdateRequest currentRequest = getRequest(reqID);
		
		mav.setViewName("admin/mitgliederVerwaltung/mitglVerwShowRequest");
		mav.addObject("currentUser",currentAdmin);
		mav.addObject("currentRequest",currentRequest);
		mav.addObject("filter",filter);
		return mav;
	}
	
	
	
//--------------------------FinanzVerwaltung---------------------------------------
	
	@RequestMapping(value="/adminFinanzVerw",method=RequestMethod.GET)
	public ModelAndView employeeFinanzVerw(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerw_navi");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/adminFinanzVerw_Statistiken",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Statistiken(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwStatistiken");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/adminFinanzVerw_Lotto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Lotto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwLotto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/adminFinanzVerw_nrLotto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_nrLotto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwNrLotto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/adminFinanzVerw_Toto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Toto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwToto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	
	
//--------------------------SpieleVerwaltung---------------------------------------
	
	@RequestMapping(value="/adminSpieleVerw",method=RequestMethod.GET)
	public ModelAndView employeeSpieleVerw(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/spieleVerwaltung/spieleVerw_navi");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
//	private String typeToGerman(String input){
//		if(input.equals("Customer")return "Mitarbeiter";
//		if(input.equals("Customer")return "Mitarbeiter";
//		
//		
//		return null;
//		
//	}
	
//-------------------------------------------ZUSATZMETHODEN--------------------------------------
	private boolean testeEingabeFehler(
			String vname,String nname,String uid,String password){
		Boolean erg = false;
		if(vname==""){	erg = true;}
		if(nname==""){	erg = true;}
		if(uid==""){	erg = true;}
		if(password==""){		erg = true;}
		return erg;
		}
	
	private List<Member> filterMemberList(String filter, List<Member> list){
		
		List<Member> returnList = new LinkedList<Member>();
		if(filter.equals("All")){
			returnList = list;
		}
		else{
			for(Member member : list){
				if(member.getType().toString().equals(filter)) returnList.add(member);
			}
		}
		return returnList;
	}
	
	
	private List<Member> getMemberList_depOnUser(){
		
		Member currentAdmin = GmbPersistenceManager.get(UID);
		List<Member> memberList = new LinkedList<Member>();
	
		if(currentAdmin.getType().toString().equals("Admin")){
		memberList = Lottery.getInstance().getMemberManagement().getMembers();
		}
		if(currentAdmin.getType().toString().equals("Employee")){
		memberList = filterMemberList("Customer", Lottery.getInstance().getMemberManagement().getMembers());
		}
	
		return memberList;
	}
	
	private MemberDataUpdateRequest getRequest(int id){
		MemberDataUpdateRequest returnReq = null;
		List<MemberDataUpdateRequest> requestList = Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests();
		for(MemberDataUpdateRequest ar : requestList){
			if(ar.getId() == id){
				returnReq = ar;
			}
		}
		return returnReq;
	}
	
private List<MemberDataUpdateRequest> filterRequestList(String filter, List<MemberDataUpdateRequest> list){
		
		List<MemberDataUpdateRequest> returnList = new LinkedList<MemberDataUpdateRequest>();
		if(filter.equals("All")){
			returnList = list;
		}
		else{
			for(MemberDataUpdateRequest request : list){
				if(request.getMember().getType().toString().equals(filter)) returnList.add(request);
			}
		}
		return returnList;
	}

private String requestTime (MemberDataUpdateRequest req){
	Date time = req.getDate().toDate();
	DateFormat df;
	df = DateFormat.getDateInstance();
	DateFormat formatter = new SimpleDateFormat();
	System.out.println(df.format(time));
	System.out.println(formatter.format(time));
	return df.format(time);
}
	
	
	
}