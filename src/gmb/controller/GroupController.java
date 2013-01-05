package gmb.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import gmb.model.GmbDecoder;
import gmb.model.GmbFactory;
import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.group.Group;
import gmb.model.group.GroupManagement;
import gmb.model.member.Customer;
import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.request.RequestState;
import gmb.model.request.group.GroupMembershipApplication;
import gmb.model.request.group.GroupMembershipInvitation;
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

import org.apache.axis.utils.ByteArray;
import org.salespointframework.core.user.PersistentUserManager;
import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller	
	public class GroupController {
	
	private static Group currentGroup;

	
//--------------------------------ALLGEMEIN-----------------------------------------
	
	@RequestMapping(value="/customerGroups",method=RequestMethod.GET)
	public ModelAndView customerGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		mav.setViewName("customer/groups/groups_start");
		addStuff(mav, currentUser);
		return  mav;
	}
	
	@RequestMapping(value="/myGroups",method=RequestMethod.GET)
	public ModelAndView myGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		List<Group> groupList = currentUser.getGroups();
		mav.setViewName("customer/groups/groups_myGroups");
		mav.addObject("groupList", groupList);
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
	@RequestMapping(value="/allGroups",method=RequestMethod.GET)
	public ModelAndView allGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer)GmbPersistenceManager.get(uid);

		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", getAllGroupList());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/showInvitations",method=RequestMethod.GET)
	public ModelAndView showInvitations(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer)GmbPersistenceManager.get(uid);
		//List<GroupMembershipApplication> myInvList = currentUser.getGroupInvitations();

		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/refuseInvitation",method=RequestMethod.GET)
	public ModelAndView refuseInvitation(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("invId") int invId) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);	
		currentUser.getGroupInvitations().get(invId).refuse();
	//	List<GroupMembershipApplication> myInvList = currentUser.getGroupInvitations();
		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/acceptInvitation",method=RequestMethod.GET)
	public ModelAndView acceptInvitation(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("invId") int invId) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);	
		currentUser.getGroupInvitations().get(invId).accept();
		//List<GroupMembershipApplication> myInvList = currentUser.getGroupInvitations();
		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	
	@RequestMapping(value="/showApplications",method=RequestMethod.GET)
	public ModelAndView showApplications(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer)GmbPersistenceManager.get(uid);
		//List<GroupMembershipApplication> myApplList = currentUser.getGroupMembershipApplications();
		mav.setViewName("customer/groups/groups_myApplications");
		mav.addObject("myApplList", currentUser.getGroupMembershipApplications());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/withdrawApplication",method=RequestMethod.GET)
	public ModelAndView withdrawApplication(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("applId") int applId) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer)GmbPersistenceManager.get(uid);
		currentUser.getGroupMembershipApplications().get(applId).withdraw();
		//List<GroupMembershipApplication> myApplList = currentUser.getGroupMembershipApplications();
		mav.setViewName("customer/groups/groups_myApplications");
		mav.addObject("myApplList", currentUser.getGroupMembershipApplications());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/applyGroupMembership",method=RequestMethod.GET)
	public ModelAndView applyGroupMembership(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String grpName) throws UnsupportedEncodingException{
		System.out.println(grpName);
		uid = GmbDecoder.decodeUTF8String(uid);
		String groupName = GmbDecoder.decodeUTF8String(grpName);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		GmbPersistenceManager.getGroup(groupName).applyForMembership(currentUser, "");	
		for(Group g : getAllGroupList()){
			System.out.println(g.testForAppl(currentUser));
		}
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", getAllGroupList());
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/newGroup",method=RequestMethod.GET)
	public ModelAndView newGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		mav.setViewName("customer/groups/groups_newGroup");
		mav.addObject("comment", "Bitte geben Sie einen Namen und optional einen Infotext an !");
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/createNewGroup",method=RequestMethod.POST)
	public ModelAndView createNewGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName,
			@RequestParam("infoText") String infoText) throws UnsupportedEncodingException{
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid) ;
		
		if(groupName == ""){
			mav.setViewName("customer/groups/groups_newGroup");
			mav.addObject("comment", "Bitte geben Sie einen Namen an !");
		}
		else if (GmbPersistenceManager.getGroup(groupName) != null){
			mav.setViewName("customer/groups/groups_newGroup");
			mav.addObject("comment", "Der Name wird schon verwendet !");
		}
		else{
			GmbFactory.new_Group(groupName, currentUser, infoText);
			mav.setViewName("customer/groups/groups_myGroups");
			mav.addObject("groupList", getMyGroupList(currentUser));
		}
		
		addStuff(mav, currentUser);
		return mav;	
	}
	
	
	
//--------------------------------SPEZIELLE GRUPPE-----------------------------------------	
	@RequestMapping(value="/currentGroupView",method=RequestMethod.GET)
	public ModelAndView currentGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
//		groupName = GmbDecoder.decodeUTF8String(groupName);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		currentGroup = GmbPersistenceManager.getGroup(groupName);
		if(currentGroup.getGroupAdmin().equals(currentUser)) {
			//adminView startet mit Mitgliedern
			
			System.out.println(currentGroup.getGroupMembers().size());
			
			mav.setViewName("customer/groups/currentGroupView_Admin");
			mav.addObject("memberList", currentGroup.getGroupMembers());
			mav.addObject("groupApplCount", filterGroupApplications().size());
			mav.addObject("groupInvCount", filterGroupInvitations().size());
		}
		else {//memberView startet mit Tipps
			mav.setViewName("customer/groups/currentGroupView_Member");
			mav.addObject("totoTipList", currentGroup.getTotoGroupTips());
			mav.addObject("dailyLottoTipList", currentGroup.getDailyLottoGroupTips());
			mav.addObject("weeklyLottoTipList", currentGroup.getWeeklyLottoGroupTips());
		}
		
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("active", (currentGroup.getGroupMembers().size() >= 2) ? true : false);
		addStuff(mav, currentUser);
		return mav;	
	}
	
//	--------------------------------ADMIN---------------------------------------------------
	
	@RequestMapping(value="/currentGroupViewTips_Admin",method=RequestMethod.GET)
	public ModelAndView currentGroupViewTipps(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
//		groupName = GmbDecoder.decodeUTF8String(groupName);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		currentGroup = GmbPersistenceManager.getGroup(groupName);
		mav.setViewName("customer/groups/currentGroupViewTips_Admin");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("weeklySTTList", (currentGroup.getWeeklyLottoGroupTips().size() > 0) ? currentGroup.getWeeklyLottoGroupTips() : null);
		mav.addObject("dailySTTList", (currentGroup.getDailyLottoGroupTips().size() > 0) ? currentGroup.getDailyLottoGroupTips() : null);
		mav.addObject("totoSTTList", (currentGroup.getTotoGroupTips().size() > 0) ? currentGroup.getTotoGroupTips() : null);
		addStuff(mav, currentUser);
		return mav;	
	}
	
//	-----------------------------ADMIN APPLICATIONS-----------------------------------------
	@RequestMapping(value="/currentGroupViewApplications",method=RequestMethod.GET)
	public ModelAndView currentGroupView(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
//		List<GroupMembershipApplication> applicationsList = currentGroup.getGroupMembershipApplications();
//		for(GroupMembershipApplication g : currentGroup.getGroupMembershipApplications()){
//			Date time = g.getDate().toDate();
//			DateFormat df;
//			df = DateFormat.getDateInstance();
//			DateFormat formatter = new SimpleDateFormat();
//			System.out.println(df.format(time));
//		}
		
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("applList", currentGroup.getGroupMembershipApplications());
		addStuff(mav, currentUser);
		return mav;		
	}
	
	@RequestMapping(value="/refuseApplication",method=RequestMethod.GET)
	public ModelAndView refuseApplication(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("applId") int applId) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		GroupMembershipApplication currentAppl = filterGroupApplications().get(applId);
		currentAppl.refuse();
		currentAppl.DB_UPDATE();
		//List<GroupMembershipApplication> applicationsList = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications();

		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("applList", filterGroupApplications());
		mav.addObject("currentGroup", currentGroup);
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/acceptApplication",method=RequestMethod.GET)
	public ModelAndView acceptApplication(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("applId") int applId) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		GroupMembershipApplication currentAppl = filterGroupApplications().get(applId);
		currentAppl.accept();
		currentAppl.DB_UPDATE();
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("applList", filterGroupApplications());
		mav.addObject("currentGroup", currentGroup);
		addStuff(mav, currentUser);
		return mav;	
	}
	
//	-------------------------ADMIN INVITATIONS----------------------------------------
	
	@RequestMapping(value="/currentGroupViewInvitations",method=RequestMethod.GET)
	public ModelAndView currentGroupViewInvitations(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		List<GroupMembershipInvitation> invitationList = currentGroup.getgroupInvitations();

		mav.setViewName("customer/groups/currentGroupViewInvitations");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("invList", invitationList);
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/invite",method=RequestMethod.POST)
	public ModelAndView invite(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("invID") UserIdentifier invID,
			@RequestParam("infoText") String infoText){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		Customer customer = (Customer) GmbPersistenceManager.get(invID);
		String failure = "";
		
		if(GmbPersistenceManager.get(invID) != null){
			if(currentGroup.getGroupMembers().contains(customer) == false){
				GmbPersistenceManager.getGroup(currentGroup.getName()).sendGroupInvitation(customer, infoText);
			}else {
				failure = customer.getIdentifier().toString()+" ist schon Mitglied !";
			}
		
		}else {
			failure = "Den Nutzer "+invID+" gibt es nicht !";
		}
		
		List<GroupMembershipInvitation> invitationList = currentGroup.getgroupInvitations();

		mav.setViewName("customer/groups/currentGroupViewInvitations");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("invList", invitationList);
		mav.addObject("failure", failure);
		addStuff(mav, currentUser);
		return mav;	
	}
	
//	---------------------------------ADMIN CLOSE GROUP-------------------------------------
	
	@RequestMapping(value="/closeGroup",method=RequestMethod.GET)
	public ModelAndView closeGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		mav.setViewName("customer/groups/closeGroupConf");
		mav.addObject("currentGroup", currentGroup);
		addStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/closeGroupConf",method=RequestMethod.GET)
	public ModelAndView closeGroupConf(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		uid = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		currentGroup.close();
//		currentGroup.DB_REMOVE();
		mav.addObject("groupList", getMyGroupList(currentUser));
		mav.setViewName("customer/groups/groups_myGroups");
		addStuff(mav, currentUser);
		return mav;	
	}
	
	
//	----------------Hilfsfunktionen----------------------
	private int applCount(Customer cus){
		int count = 0;
		for(GroupMembershipApplication g : cus.getGroupMembershipApplications()){
			if(g.getState().toString() == "Unhandled") count++;
		}
		return count;
	}
	
	private int invCount(Customer cus){
		int count = 0;
		for(GroupMembershipApplication g : cus.getGroupInvitations()){
			if(g.getState().toString() == "Unhandled") count++;
		}
		return count;
	}
	
	private ModelAndView addStuff(ModelAndView mav, Customer currentUser) {
		mav.addObject("currentUser", currentUser);
		mav.addObject("applCount", applCount(currentUser));
		mav.addObject("invCount", invCount(currentUser));
		return  mav;
	}
	
	private List<Group> getMyGroupList(Customer cus){
		List<Group> returnList = new LinkedList<Group>();
		for(Group gl : cus.getGroups()){
			if(!gl.isClosed()) returnList.add(gl);
		}
		return returnList;
	}
	
	private List<Group> getAllGroupList(){
		List<Group> returnList = new LinkedList<Group>();
		for(Group gl : Lottery.getInstance().getGroupManagement().getGroups()){
			if(!gl.isClosed()) returnList.add(gl);
		}
		return returnList;
	}
	
	
	//----nur zum anzeigen, nicht um daten zu Ändern wegen anderen indizes
	private List<GroupMembershipApplication> filterGroupApplications(){
		List<GroupMembershipApplication> returnList = new LinkedList<GroupMembershipApplication>();
		for(GroupMembershipApplication al : currentGroup.getGroupMembershipApplications()){
			if(al.getState().toString() == "Unhandled") returnList.add(al);
		}
		return returnList;
	}
	
	//----nur zum anzeigen, nicht um daten zu Ändern wegen anderen indizes
	private List<GroupMembershipApplication> filterGroupInvitations(){
		List<GroupMembershipApplication> returnList = new LinkedList<GroupMembershipApplication>();
		for(GroupMembershipApplication al : GmbPersistenceManager.getGroup(currentGroup.getName()).getgroupInvitations()){
			if(al.getState().toString() == "Unhandled") returnList.add(al);
		}
		return returnList;
	}
	
}