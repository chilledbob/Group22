package gmb.model.member;
import gmb.model.request.data.MemberDataUpdateRequest;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	public MemberManagement(int dummy)
	{
		members = new LinkedList<Member>();
		requests = new LinkedList<MemberDataUpdateRequest>();
	}
	
	public void addMember(Member member){ members.add(member); }
	
	public boolean removeMember(Member member){	return members.remove(member);}
	
	public void addMemberDataUpdateRequest(MemberDataUpdateRequest newRequest){ requests.add(newRequest); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return requests; }
	public List<Member> getMembers(){ return members; }
}
