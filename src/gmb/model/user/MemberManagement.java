package gmb.model.user;
import gmb.model.request.MemberDataUpdateRequest;

import java.util.LinkedList;
import java.util.List;

import org.salespointframework.core.user.PersistentUserManager;

public class MemberManagement// extends PersistentUserManager
{
	protected List<Member> members;	
	protected List<MemberDataUpdateRequest> requests;
			
//	@Deprecated
//	protected MemberManagement(){}
	
	public MemberManagement()
	{
		members = new LinkedList<Member>();
		requests = new LinkedList<MemberDataUpdateRequest>();
	}
	
	public void addMember(Member member)
	{
		members.add(member); // the member is added to the list
//		this.add(member);	//the member is added as a persistent user by the persistentusermanager
	}
	
	public boolean removeMember(Member member)
	{	
		//if the member is in the list and the removal from the database was successful
//		if ( members.contains(member) && remove(member.getIdentifier()) ) 
		if ( members.contains(member) ) 
		{
			members.remove(member);	
			return true;
		}
		else
			return false;
	}
	
	public void addMemberDataUpdateRequest(MemberDataUpdateRequest newRequest){ requests.add(newRequest); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return requests; }
	public List<Member> getMembers(){ return members; }
}
