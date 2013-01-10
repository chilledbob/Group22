package gmb.controller;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.HashMap;
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
import gmb.model.tip.tip.group.DailyLottoGroupTip;
import gmb.model.tip.tip.group.TotoGroupTip;
import gmb.model.tip.tip.group.WeeklyLottoGroupTip;
import gmb.model.tip.tipticket.perma.WeeklyLottoPTT;
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
	
	private static String GROUPID;
	private static UserIdentifier UID;

	
//--------------------------------ALLGEMEIN-----------------------------------------
	
	@RequestMapping(value="/customerGroups",method=RequestMethod.GET)
	public ModelAndView customerGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid) throws UnsupportedEncodingException{
		UID = GmbDecoder.decodeUTF8String(uid);
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		mav.setViewName("customer/groups/groups_start");
		addCurrentUserStuff(mav, currentUser);
		return  mav;
	}
	
	@RequestMapping(value="/returnToCustomerGroups",method=RequestMethod.GET)
	public ModelAndView returnToCustomerGroups(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		mav.setViewName("customer/groups/groups_start");
		addCurrentUserStuff(mav, currentUser);
		return  mav;
	}
	
	@RequestMapping(value="/myGroups",method=RequestMethod.GET)
	public ModelAndView myGroups(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);

		mav.setViewName("customer/groups/groups_myGroups");
		mav.addObject("groupList", getMyGroupList(currentUser));
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	
	@RequestMapping(value="/allGroups",method=RequestMethod.GET)
	public ModelAndView allGroups(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);

		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", getAllGroupList());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/applyGroupMembership",method=RequestMethod.GET)
	public ModelAndView applyGroupMembership(ModelAndView mav,
			@RequestParam("groupName") String groupName){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		GmbPersistenceManager.getGroup(groupName).applyForMembership(currentUser, "");	
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", getAllGroupList());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/showApplications",method=RequestMethod.GET)
	public ModelAndView showApplications(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		mav.setViewName("customer/groups/groups_myApplications");
		mav.addObject("myApplList", currentUser.getGroupMembershipApplications());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/withdrawApplication",method=RequestMethod.GET)
	public ModelAndView withdrawApplication(ModelAndView mav,
			@RequestParam("applId") int applId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		currentUser.getGroupMembershipApplications().get(applId).withdraw();

		mav.setViewName("customer/groups/groups_myApplications");
		mav.addObject("myApplList", currentUser.getGroupMembershipApplications());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
		
	@RequestMapping(value="/showInvitations",method=RequestMethod.GET)
	public ModelAndView showInvitations(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/refuseInvitation",method=RequestMethod.GET)
	public ModelAndView refuseInvitation(ModelAndView mav,
			@RequestParam("invId") int invId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
	
		currentUser.getGroupInvitations().get(invId).refuse();

		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/acceptInvitation",method=RequestMethod.GET)
	public ModelAndView acceptInvitation(ModelAndView mav,
			@RequestParam("invId") int invId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);	
		
		currentUser.getGroupInvitations().get(invId).accept();

		mav.setViewName("customer/groups/groups_myInvitations");
		mav.addObject("myInvList", currentUser.getGroupInvitations());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	
	@RequestMapping(value="/newGroup",method=RequestMethod.GET)
	public ModelAndView newGroup(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
		mav.setViewName("customer/groups/groups_newGroup");
		mav.addObject("comment", "Bitte geben Sie einen Namen und optional einen Infotext an !");
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/createNewGroup",method=RequestMethod.POST)
	public ModelAndView createNewGroup(ModelAndView mav,
			@RequestParam("groupName") String groupName,
			@RequestParam("infoText") String infoText) throws UnsupportedEncodingException{
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
	
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
			mav.addObject("groupList", currentUser.getGroups());
		}
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/closeGroup",method=RequestMethod.GET)
	public ModelAndView closeGroup(ModelAndView mav,
			@RequestParam("groupName") String groupName,
			@RequestParam("groupid") int groupid)
//					throws UnsupportedEncodingException
			{
//		String decGroupName = GmbDecoder.decodeUTF8String(groupName);
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		
//		Group g = (Group) GmbPersistenceManager.get(Group.class, groupid);
//		g.close();
		GmbPersistenceManager.getGroup(groupName).close();
		
		for(Group g : currentUser.getGroups()){
			if(g.getId() == groupid){
				g.close();
			//	System.out.println(g.getName()+" wird geschlossen.");
			}
		}
		
		mav.setViewName("customer/groups/groups_myGroups");
		mav.addObject("groupList", currentUser.getGroups());
		addCurrentUserStuff(mav, currentUser);
		return mav;	
	}
	
	
	
//--------------------------------SPEZIELLE GRUPPE-----------------------------------------	
	@RequestMapping(value="/currentGroupView",method=RequestMethod.GET)
	public ModelAndView currentGroup(ModelAndView mav,
			@RequestParam("groupName") String groupName) throws UnsupportedEncodingException {
		String decGroupName = GmbDecoder.decodeUTF8String(groupName);
		GROUPID = decGroupName;
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		if(currentGroup.getGroupAdmin().equals(currentUser)) {
			//adminView startet mit Mitgliedern
			mav.setViewName("customer/groups/currentGroupView_Admin");
			mav.addObject("memberList", currentGroup.getGroupMembers());
		}
		else {
			//memberView startet mit Tipps
			mav.setViewName("customer/groups/currentGroupView_Member");
			mav.addObject("totoTipList", currentGroup.getTotoGroupTips());
			mav.addObject("dailyLottoTipList", currentGroup.getDailyLottoGroupTips());
			mav.addObject("weeklyLottoTipList", currentGroup.getWeeklyLottoGroupTips());
		}
		
		mav.addObject("active", (currentGroup.getGroupMembers().size() >= 2) ? true : false);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}

//	--------------------------------ADMIN---------------------------------------------------
	
	@RequestMapping(value="/currentGroupViewAdmin",method=RequestMethod.GET)
	public ModelAndView currentGroupViewCustomer(ModelAndView mav){
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);

		mav.setViewName("customer/groups/currentGroupView_Admin");
		mav.addObject("memberList", currentGroup.getGroupMembers());
		
		mav.addObject("active", (currentGroup.getGroupMembers().size() >= 2) ? true : false);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
	@RequestMapping(value="/currentGroupRemoveUser",method=RequestMethod.GET)
	public ModelAndView currentGroupRemoveUser(ModelAndView mav,
			@RequestParam("removeUid") UserIdentifier removeUid){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		Customer customer = (Customer) GmbPersistenceManager.get(removeUid);
		
		currentGroup.resign(customer);
		
		mav.setViewName("customer/groups/currentGroupView_Admin");
		mav.addObject("memberList", currentGroup.getGroupMembers());
		
		mav.addObject("active", (currentGroup.getGroupMembers().size() >= 2) ? true : false);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	

	@RequestMapping(value="/currentGroupViewTips_Admin",method=RequestMethod.GET)
	public ModelAndView currentGroupViewTipps(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		mav.setViewName("customer/groups/currentGroupViewTips_Admin");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("weeklySTTList", (currentGroup.getWeeklyLottoGroupTips().size() > 0) ? currentGroup.getWeeklyLottoGroupTips() : null);
		mav.addObject("dailySTTList", (currentGroup.getDailyLottoGroupTips().size() > 0) ? currentGroup.getDailyLottoGroupTips() : null);
		mav.addObject("totoSTTList", (currentGroup.getTotoGroupTips().size() > 0) ? currentGroup.getTotoGroupTips() : null);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
//	-----------------------------ADMIN Group-APPLICATIONS-----------------------------------------
	@RequestMapping(value="/currentGroupViewApplications",method=RequestMethod.GET)
	public ModelAndView currentGroupView(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("applList", currentGroup.getGroupMembershipApplications());
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;		
	}
	
	@RequestMapping(value="/refuseApplication",method=RequestMethod.GET)
	public ModelAndView refuseApplication(ModelAndView mav,
			@RequestParam("applId") int applId){	
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		GroupMembershipApplication currentAppl = currentGroup.getGroupMembershipApplications().get(applId);
		currentAppl.refuse();
//		currentAppl.DB_UPDATE();

		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("applList", currentGroup.getGroupMembershipApplications());
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
	@RequestMapping(value="/acceptApplication",method=RequestMethod.GET)
	public ModelAndView acceptApplication(ModelAndView mav,
			@RequestParam("applId") int applId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		GroupMembershipApplication currentAppl = currentGroup.getGroupMembershipApplications().get(applId);
		currentAppl.accept();
//		currentAppl.DB_UPDATE();
		
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("applList", currentGroup.getGroupMembershipApplications());
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
//	-------------------------ADMIN INVITATIONS----------------------------------------
	
	@RequestMapping(value="/currentGroupViewInvitations",method=RequestMethod.GET)
	public ModelAndView currentGroupViewInvitations(ModelAndView mav){
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);

		mav.setViewName("customer/groups/currentGroupViewInvitations");
		mav.addObject("invList", currentGroup.getGroupInvitations());
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
	@RequestMapping(value="/invite",method=RequestMethod.POST)
	public ModelAndView invite(ModelAndView mav,
			@RequestParam("invID") UserIdentifier invID,
			@RequestParam("infoText") String infoText) throws UnsupportedEncodingException{
		String decInfoText = GmbDecoder.decodeUTF8String(infoText);
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		Customer customer = (Customer) GmbPersistenceManager.get(invID);
		String failure = "";
		
		if(GmbPersistenceManager.get(invID) != null){
			if(currentGroup.getGroupMembers().contains(customer) == false){
				
				if(currentGroup.testForAppl(customer)){
					failure = customer.getIdentifier().toString()+" hat sich schon beworben. Bitte in Bewerbungen nachsehen.";
				}
				else if(currentGroup.testForInv(customer)){
					failure = customer.getIdentifier().toString()+" wurde schon eingeladen !";
				}
				else{
					currentGroup.sendGroupInvitation(customer, decInfoText);
					failure = customer.getIdentifier().toString()+" wurde eingeladen.";
				}
			}else {
				failure = customer.getIdentifier().toString()+" ist schon Mitglied !";
			}
		}else {
			failure = "Den Nutzer "+invID+" gibt es nicht !";
		}
		mav.setViewName("customer/groups/currentGroupViewInvitations");
		mav.addObject("invList", currentGroup.getGroupInvitations());
		mav.addObject("failure", failure);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
//	---------------------------------ADMIN CLOSE GROUP-------------------------------------
	
//	@RequestMapping(value="/closeGroup",method=RequestMethod.GET)
//	public ModelAndView closeGroup(ModelAndView mav){
//		UID = GmbDecoder.decodeUTF8String(UID);
//		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
//		mav.setViewName("customer/groups/closeGroupConf");
//		
//		addCurrentUserStuff(mav);
//		addCurrentGroupStuff(mav);
//		return mav;	
//	}
//	
//	@RequestMapping(value="/closeGroupConf",method=RequestMethod.GET)
//	public ModelAndView closeGroupConf(ModelAndView mav,
//			@RequestParam("groupName") String groupName){
//		
//		String grpName = GmbDecoder.decodeUTF8String(groupName);
//			@RequestParam("UID") UserIdentifier UID) throws UnsupportedEncodingException{
//		UID = GmbDecoder.decodeUTF8String(UID);
//		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
//		GmbPersistenceManager.getGroup(groupName).close();
//		System.out.println(GmbPersistenceManager.getGroup(groupName).isClosed());
//		currentGroup = null;
//		mav.addObject("groupList", currentUser.getGroups());
//		mav.setViewName("customer/groups/groups_myGroups");
//		addCurrentUserStuff(mav);
//		return mav;	
//	}
	
	
//	----------------Hilfsfunktionen----------------------
	private int currentUserApplCount(Customer currentUser){
		int count = 0;
		for(GroupMembershipApplication g : currentUser.getGroupMembershipApplications()){
			if(g.getState().toString() == "Unhandled") count++;
		}return count;
	}
	
	private int currentUserInvCount(Customer currentUser){
		int count = 0;
		for(GroupMembershipInvitation g : currentUser.getGroupInvitations()){
			if(g.getState().toString() == "Unhandled") count++;
		}return count;
	}
	
	private int groupApplCount(Group currentGroup){
		int count = 0;
		for(GroupMembershipApplication g : currentGroup.getGroupMembershipApplications()){
			if(g.getState().toString() == "Unhandled") count++;
		}return count;
	}
	
	private int groupInvCount(Group currentGroup){
		int count = 0;
		for(GroupMembershipInvitation g : currentGroup.getGroupInvitations()){
			if(g.getState().toString() == "Unhandled") count++;
		}return count;
	}  
	
	private ModelAndView addCurrentUserStuff(ModelAndView mav, Customer currentUser) {
		mav.addObject("currentUser", currentUser);
		mav.addObject("applCount", currentUserApplCount(currentUser));
		mav.addObject("invCount", currentUserInvCount(currentUser));
		return  mav;
	}
	
	private ModelAndView addCurrentGroupStuff(ModelAndView mav, Group currentGroup) {
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("groupApplCount", groupApplCount(currentGroup));
		mav.addObject("groupInvCount", groupInvCount(currentGroup));
		return  mav;
	}
	
	private List<Group> getMyGroupList(Customer currentUser){
		List<Group> returnList = new LinkedList<Group>();
		for(Group gl : currentUser.getGroups()){
			if(!gl.isClosed()) returnList.add(gl);
		}return returnList;
	}
	
	private List<Group> getAllGroupList(){
		List<Group> returnList = new LinkedList<Group>();
		for(Group gl : Lottery.getInstance().getGroupManagement().getGroups()){
			if(!gl.isClosed()) returnList.add(gl);
		}return returnList;
	}	
	
//	------------------------------------------------NUTZER------------------------------------------
	
	@RequestMapping(value="/currentGroupViewTips",method=RequestMethod.GET)
	public ModelAndView currentGroupViewTipsCustomer(ModelAndView mav){
		HashMap<WeeklyLottoGroupTip,String> wlMap = new HashMap<WeeklyLottoGroupTip, String>();
		HashMap<DailyLottoGroupTip ,String> dlMap = new HashMap<DailyLottoGroupTip, String>();
		HashMap<TotoGroupTip, String> ttMap = new HashMap<TotoGroupTip, String>();
		Customer currentUser = (Customer) GmbPersistenceManager.get(UID);
		Group currentGroup = GmbPersistenceManager.getGroup(GROUPID);
		
		mav.setViewName("customer/groups/currentGroupViewTips_Member");
		mav.addObject("currentGroup", currentGroup);
		
		for(WeeklyLottoGroupTip gt : currentGroup.getWeeklyLottoGroupTips()){
			if(gt.getAllTipsOfGroupMember(currentUser).size() > 0)
				wlMap.put(gt,"true");
			else
				wlMap.put(gt,"false");
		}
		for(DailyLottoGroupTip gt : currentGroup.getDailyLottoGroupTips()){
			if(gt.getAllTipsOfGroupMember(currentUser).size() > 0)
				dlMap.put(gt,"true");
			else
				dlMap.put(gt,"false");
		}
		for(TotoGroupTip gt : currentGroup.getTotoGroupTips()){
			if(gt.getAllTipsOfGroupMember(currentUser).size() > 0)
				ttMap.put(gt,"true");
			else
				ttMap.put(gt,"false");
		}
		mav.addObject("weeklySTTList", (wlMap.size() > 0) ? wlMap : null);
		mav.addObject("dailySTTList", (dlMap.size() > 0) ? dlMap : null);
		mav.addObject("totoSTTList", (ttMap.size() > 0) ? ttMap : null);
		addCurrentUserStuff(mav, currentUser);
		addCurrentGroupStuff(mav, currentGroup);
		return mav;	
	}
	
}