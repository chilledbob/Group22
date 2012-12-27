package gmb.controller;
import java.util.LinkedList;
import java.util.List;

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
import gmb.model.tip.tipticket.single.WeeklyLottoSTT;

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
	@RequestMapping(value="/myGroups",method=RequestMethod.GET)
	public ModelAndView myGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		List<Group> groupList = currentUser.getGroups();
		mav.setViewName("customer/groups/groups_myGroups");
		mav.addObject("groupList", groupList);
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	
	@RequestMapping(value="/allGroups",method=RequestMethod.GET)
	public ModelAndView allGroups(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		Customer c = (Customer)GmbPersistenceManager.get(uid);
		List<Group> groupList = Lottery.getInstance().getGroupManagement().getGroups();
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", groupList);
		mav.addObject("currentUser", c);
		return mav;	
	}
	
	@RequestMapping(value="/newGroup",method=RequestMethod.GET)
	public ModelAndView newGroup(
			@RequestParam("uid") UserIdentifier uid){
		System.out.println("----Customer newGroup----");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("customer/groups/groups_newGroup");
		mav.addObject("currentUser", GmbPersistenceManager.get(uid));
		return mav;	
	}
	
	@RequestMapping(value="/createNewGroup",method=RequestMethod.POST)
	public ModelAndView createNewGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName,
			@RequestParam("infoText") String infoText){
		Member currentUser = GmbPersistenceManager.get(uid) ;
		GmbFactory.new_Group(groupName, (Customer)currentUser, infoText);
		List<Group> groupList = Lottery.getInstance().getGroupManagement().getGroups();	
		mav.addObject("groupList", groupList);
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("currentUser",currentUser);
		return mav;	
	}
	
	
	
//--------------------------------SPEZIELLE GRUPPE-----------------------------------------	
	@RequestMapping(value="/currentGroupView",method=RequestMethod.GET)
	public ModelAndView currentGroup(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		currentGroup = GmbPersistenceManager.getGroup(groupName);
		if(currentGroup.getGroupAdmin().equals(currentUser)) {
			//adminView startet mit Gruppen
			mav.setViewName("customer/groups/currentGroupView_Admin");
			mav.addObject("memberList", currentGroup.getGroupMembers());
		}
		else {//memberView startet mit Tipps
			mav.setViewName("customer/groups/currentGroupView_Member");
			
			mav.addObject("totoTipList", currentGroup.getTotoGroupTips());
			mav.addObject("dailyLottoTipList", currentGroup.getDailyLottoGroupTips());
			mav.addObject("weeklyLottoTipList", currentGroup.getWeeklyLottoGroupTips());
		}
		
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("active", (currentGroup.getGroupMembers().size() >= 2) ? true : false);
//		mav.addObject("openApplList", openApplList);
//		mav.addObject("invList", invList);
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	//--------------------------------ADMIN---------------------------------------------------
	
	@RequestMapping(value="/currentGroupViewTips_Admin",method=RequestMethod.GET)
	public ModelAndView currentGroupViewTipps(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		currentGroup = GmbPersistenceManager.getGroup(groupName);
		mav.setViewName("customer/groups/currentGroupViewTips_Admin");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("weeklySTTList", (currentGroup.getWeeklyLottoGroupTips().size() > 0) ? currentGroup.getWeeklyLottoGroupTips() : null);
//		mav.addObject("totoTipList", );
//		mav.addObject("lottoTiList", );
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	
	@RequestMapping(value="/currentGroupViewApplications",method=RequestMethod.GET)
	public ModelAndView currentGroupView(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid){
		
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		
		List<GroupMembershipApplication> applicationsList = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications();
		List<GroupMembershipApplication> openApplList = new LinkedList<GroupMembershipApplication>();
		int i = 0;
		for(GroupMembershipApplication applic : applicationsList){
			if(applic.getState() == RequestState.Unhandled) openApplList.add(applic);
			System.out.println(applic.getState().toString());
			System.out.println(i);
			i++;
		}

		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("openApplList", openApplList);
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/refuseApplication",method=RequestMethod.GET)
	public ModelAndView refuseApplication(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("applId") int applId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		//GroupMembershipApplication currentAppl = currentUser.getGroupMembershipApplications().get(applId);
		GroupMembershipApplication currentAppl = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications().get(applId);
		currentAppl.refuse();
		currentAppl.DB_UPDATE();
		currentAppl.getMember().addNotification("Du wurdest für die Gruppe "+currentGroup.getName()+" abgelehnt.");
		List<GroupMembershipApplication> applicationsList = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications();
		List<GroupMembershipApplication> openApplList = new LinkedList<GroupMembershipApplication>();
		for(GroupMembershipApplication applic : applicationsList){
			if(applic.getState() == RequestState.Unhandled) openApplList.add(applic);
		}
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("openApplList", openApplList);
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	@RequestMapping(value="/acceptApplication",method=RequestMethod.GET)
	public ModelAndView acceptApplication(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("applId") int applId){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		GroupMembershipApplication currentAppl = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications().get(applId);
		//GroupMembershipApplication currentAppl = currentUser.getGroupMembershipApplications().get(applId);
		System.out.println(currentAppl.getMember().getIdentifier().toString());
		currentAppl.accept();
		currentAppl.DB_UPDATE();
		currentAppl.getMember().addNotification("Du wurdest in die Gruppe "+currentGroup.getName()+" aufgenommen.");
		System.out.println("accepted : "+GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications().get(applId).getState().toString());
		List<GroupMembershipApplication> applicationsList = GmbPersistenceManager.getGroup(currentGroup.getName()).getGroupMembershipApplications();
		List<GroupMembershipApplication> openApplList = new LinkedList<GroupMembershipApplication>();
		for(GroupMembershipApplication applic : applicationsList){
			if(applic.getState() == RequestState.Unhandled) openApplList.add(applic);
		}
		mav.setViewName("customer/groups/currentGroupViewApplications");
		mav.addObject("openApplList", openApplList);
		mav.addObject("currentGroup", currentGroup);
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	
	@RequestMapping(value="/applyGroupMembership",method=RequestMethod.GET)
	public ModelAndView applyGroupMembership(ModelAndView mav,
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("groupName") String groupName){
		Customer currentUser = (Customer) GmbPersistenceManager.get(uid);
		GmbPersistenceManager.getGroup(groupName).applyForMembership(currentUser, "");
		mav.setViewName("customer/groups/groups_allGroups");
		mav.addObject("groupList", Lottery.getInstance().getGroupManagement().getGroups());
		mav.addObject("currentUser", currentUser);
		return mav;	
	}
	
	
	
	
}