package gmb.model.request;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import gmb.model.user.Member;
import gmb.model.user.MemberData;

@Entity
public class MemberDataUpdateRequest extends Request 
{
	@OneToOne 
    @JoinColumn(name="memberDataId")
	protected MemberData updatedData;
	
	@Deprecated
	protected MemberDataUpdateRequest(){}

	public MemberDataUpdateRequest(MemberData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	//GET METHODS
	public MemberData getUpdatedData(){ return updatedData; }
}
