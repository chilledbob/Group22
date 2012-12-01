package gmb.model.request;

import gmb.model.user.Member;
import gmb.model.user.MemberData;

public class MemberDataUpdateRequest extends Request 
{
	//ATTRIBUTE
	protected MemberData updatedData;

	//CONSTRUCTORS
	public MemberDataUpdateRequest(MemberData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	//GET METHODS
	public MemberData getUpdatedData(){ return updatedData; }
}
