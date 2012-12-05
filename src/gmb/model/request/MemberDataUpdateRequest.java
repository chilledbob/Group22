package gmb.model.request;

import gmb.model.user.Member;
import gmb.model.user.MemberData;

public class MemberDataUpdateRequest extends Request 
{
	protected MemberData updatedData;

	@Deprecated
	protected MemberDataUpdateRequest(){}
	
	public MemberDataUpdateRequest(MemberData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	/**
	 * Return codes:
	 * 0 - successful
	 * 1 - failed because state was not "UNHANDLED"
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
