package gmb.model.request.data;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import gmb.model.member.Member;
import gmb.model.member.MemberManagement;
import gmb.model.member.container.MemberData;
import gmb.model.request.Request;

/**
 * A request type for updating member data.
 */
@Entity
public class MemberDataUpdateRequest extends Request 
{
	@OneToOne 
    @JoinColumn(name="memberDataId")
	protected MemberData updatedData;
	
	@ManyToOne
	@JoinColumn(name="memberManagementID")
	protected MemberManagement memberManagementID;
	
	@Deprecated
	protected MemberDataUpdateRequest(){}
	
	public MemberDataUpdateRequest(MemberData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	/**
	 * [Intended for direct usage by controller]<br>
	 * @return return code:
	 * <ul>
	 * <li> 0 - successful
	 * <li> 1 - failed because state was not "Unhandled"
	 * </ul>
	 */
	public int accept()
	{
		if(super.accept() == 0)
			{
				member.setMemberData(updatedData);
				return 0;
			}
		else
			return 1;
	}
	
	public MemberData getUpdatedData(){ return updatedData; }
}
