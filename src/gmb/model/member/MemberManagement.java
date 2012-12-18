package gmb.model.member;
import gmb.model.GmbPersistenceManager;
import gmb.model.PersiObject;
import gmb.model.request.data.MemberDataUpdateRequest;

import java.util.List;
import java.util.LinkedList;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;


@Entity
public class MemberManagement extends PersiObject
{
	@OneToMany(mappedBy="memberManagement",fetch=FetchType.EAGER)
	@JoinColumn(name="MEMBERMANAGEMENT_PERSISTENCEID")
	protected List<Member> members;
	@OneToMany(mappedBy="memberManagementID",fetch=FetchType.EAGER)
	@JoinColumn(name="MEMBERMANAGEMENTID")
	protected List<MemberDataUpdateRequest> requests;
			
	@Deprecated
	protected MemberManagement(){}
	
	public MemberManagement(Object dummy)
	{
		members = new LinkedList<Member>();
		requests = new LinkedList<MemberDataUpdateRequest>();
	}
	
	public void addMember(Member member){ members.add(member); DB_UPDATE(); }
	
	public boolean removeMember(Member member){	boolean result = members.remove(member); DB_UPDATE(); return result;}
	
	public void addMemberDataUpdateRequest(MemberDataUpdateRequest newRequest){ requests.add(newRequest); DB_UPDATE(); }
	
	public List<MemberDataUpdateRequest> getMemberDataUpdateRequests(){ return requests; }
	public List<Member> getMembers(){ return members; }
}
