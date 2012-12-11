package gmb.model.member;
import gmb.model.PersiObject;
import gmb.model.request.data.MemberDataUpdateRequest;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class MemberManagement extends PersiObject
{
	@Id
	protected int groupId = 1;
	
	@OneToMany(mappedBy="memberManagementID")
	protected List<Member> members = new LinkedList<Member>();
	@OneToMany(mappedBy="memberManagementID")
	protected List<MemberDataUpdateRequest> requests = new LinkedList<MemberDataUpdateRequest>();
			
	public MemberManagement(){}
	
	public void addMember(Member member){ members.add(member); DB_UPDATE(); }
	
	public boolean removeMember(Member member){	boolean result = members.remove(member); DB_UPDATE(); return result;}
	
	public void addMemberDataUpdateRequest(MemberDataUpdateRequest newRequest){ requests.add(newRequest); DB_UPDATE(); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return requests; }
	public List<Member> getMembers(){ return members; }
}
