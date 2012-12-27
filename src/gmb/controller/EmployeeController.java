package gmb.controller;
import java.util.List;

import gmb.model.GmbPersistenceManager;
import gmb.model.Lottery;
import gmb.model.group.Group;
import gmb.model.member.Member;
import gmb.model.request.data.MemberDataUpdateRequest;

import org.salespointframework.core.user.UserIdentifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

	

@Controller	
	public class EmployeeController {
		

//--------------------------MitgliederVerwaltung---------------------------------------
	
	@RequestMapping(value="/employeeMitglVerw",method=RequestMethod.GET)
	public ModelAndView employeeMitglVerw(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/mitgliederVerwaltung/mitgliederVerw_navi");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/mitglVerw_mitglieder",method=RequestMethod.GET)
	public ModelAndView mitglVerw_mitglieder(
			//@RequestParam("sort") Capability sort,
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView mav = new ModelAndView();
		System.out.println("mitglVerw_mitglieder");
		List<Member> memberList = Lottery.getInstance().getMemberManagement().getMembers();
		mav.setViewName("employee/mitgliederVerwaltung/mitglieder/mitglVerwMitglieder");
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		mav.addObject("memberList", memberList);
//		mav.addObject("sort", sort);
		return mav;
		
	}
	
	//---------------Groups------------------------------------
	
	@RequestMapping(value="/mitglVerw_groups",method=RequestMethod.GET)
	public ModelAndView mitglVerw_groups(
			@RequestParam("uid") UserIdentifier uid){
		System.out.println("mitglVerw_groups");
		ModelAndView mav = new ModelAndView();
		List<Group> groupList = Lottery.getInstance().getGroupManagement().getGroups();
//		System.out.println(groupList.size());
//		System.out.println(groupList.get(0).getInfoText().toString());
		mav.setViewName("employee/mitgliederVerwaltung/groups/mitglVerwGroups");
		mav.addObject("groupList", groupList);
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;
	}
	
	@RequestMapping(value="/mitglVerw_groupsInfo",method=RequestMethod.GET)
	public ModelAndView mitglVerw_groupsInfo(
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam("gid")int gid){
		ModelAndView mav = new ModelAndView();
		List<Group> groupList = Lottery.getInstance().getGroupManagement().getGroups();
//		System.out.println("mitglVerw_groupsInfo");
		System.out.println(gid);
//		System.out.println(GmbPersistenceManager.getGroup(name));
//		System.out.println(groupList.get(0).getInfoText().toString());
		//geht nicht...Gruppe durch name bekommen
//		System.out.println(GmbPersistenceManager.getGroup("abc").getInfoText());
//		GmbPersistenceManager.get(Group.class, gid);
		mav.setViewName("employee/mitgliederVerwaltung/groups/mitglVerwGroups");
		mav.addObject("groupList", groupList);
		mav.addObject("currentGroup", groupList.get(gid));
		mav.addObject("currentUser",GmbPersistenceManager.get(uid));
		return mav;
	}
	
	//---------------ChangeRequests------------------------------------
	
	@RequestMapping(value="/mitglVerw_changeReq",method=RequestMethod.GET)
	public ModelAndView mitglVerw_changeReq(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/mitgliederVerwaltung/changeReq/mitglVerwChangeReq");
		List<MemberDataUpdateRequest> requestList = Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests();
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		modelAndView.addObject("requestList",requestList);
		System.out.println(GmbPersistenceManager.get(new UserIdentifier("UserTroll")).getMemberDataUpdateRequests().size());
		System.out.println(requestList.size());
		for(MemberDataUpdateRequest myRequest : requestList){
			myRequest.getDate().year().toString();
			myRequest.getMember();
			myRequest.getNote();
			myRequest.getId();
			myRequest.getState().toString();
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/acceptDataReq",method=RequestMethod.GET)
	public ModelAndView acceptDataReq(
			@RequestParam("uid") UserIdentifier uid,
			@RequestParam ("reqID" ) int reqID){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/mitgliederVerwaltung/changeReq/mitglVerwChangeReq");
		
		MemberDataUpdateRequest currentRequest =(MemberDataUpdateRequest) (GmbPersistenceManager.get(MemberDataUpdateRequest.class, reqID));
		System.out.println(currentRequest.getNote());
		List<MemberDataUpdateRequest> requestList = Lottery.getInstance().getMemberManagement().getMemberDataUpdateRequests();
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		modelAndView.addObject("requestList",requestList);
		for(MemberDataUpdateRequest myRequest : requestList){
			GmbPersistenceManager.get(MemberDataUpdateRequest.class, reqID);
			myRequest.getDate();
			myRequest.getMember();
			myRequest.getNote();
			myRequest.getId();
		}
		return modelAndView;
	}
	
	
	
//--------------------------FinanzVerwaltung---------------------------------------
	
	@RequestMapping(value="/employeeFinanzVerw",method=RequestMethod.GET)
	public ModelAndView employeeFinanzVerw(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerw_navi");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/finanzVerw_Statistiken",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Statistiken(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwStatistiken");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/finanzVerw_Lotto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Lotto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwLotto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/finanzVerw_nrLotto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_nrLotto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwNrLotto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	@RequestMapping(value="/finanzVerw_Toto",method=RequestMethod.GET)
	public ModelAndView finanzVerw_Toto(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/finanzVerwaltung/finanzVerwToto");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	
	
//--------------------------SpieleVerwaltung---------------------------------------
	
	@RequestMapping(value="/employeeSpieleVerw",method=RequestMethod.GET)
	public ModelAndView employeeSpieleVerw(
			@RequestParam("uid") UserIdentifier uid){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("employee/spieleVerwaltung/spieleVerw_navi");
		modelAndView.addObject("currentUser",GmbPersistenceManager.get(uid));
		return modelAndView;
	}
	
	
}