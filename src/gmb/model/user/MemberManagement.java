package gmb.model.user;
import gmb.model.GmbPersistenceManager;
import gmb.model.request.MemberDataUpdateRequest;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.salespointframework.core.user.PersistentUser;

@Entity
public class MemberManagement
{
	@Id
	protected int groupId = 1;
	
	@OneToMany(mappedBy="memberManagementID")
	protected List<Member> members;
	@OneToMany(mappedBy="memberManagementID")
	protected List<MemberDataUpdateRequest> requests;
			
	@Deprecated
	protected MemberManagement(){}
	
	public MemberManagement(String Troll)
	{
		
	}
	
	
	public void addMember(Member member)
	{
		//this.add(member);	//the member is added as a persistent user by the persistentusermanager
		//members.add(member); // the member is added to the list
		
	}
	
	public boolean removeMember(Member member)
	{	
		//if the member is in the list and the removal from the database was successful
		if ( members.contains(member)) 
		{
			GmbPersistenceManager.remove(member.getIdentifier());
			members.remove(member);
			return true;
		}
		else
			return false;
	}
	
	public void addMemberDataUpdateRequest(MemberDataUpdateRequest newRequest){ 		
		requests.add(newRequest);
	}
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return requests; }
	public List<Member> getMember(){ return members; }
}
