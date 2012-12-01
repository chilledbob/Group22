package gmb.model.request;

import gmb.model.financial.RealAccountData;
import gmb.model.user.Member;

public class RealAccountDataUpdateRequest extends Request 
{	
	//ATTRIBUTE
	protected RealAccountData updatedData;

	//CONSTRUCTORS
	public RealAccountDataUpdateRequest(RealAccountData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	//GET METHODS
	public RealAccountData getUpdatedData(){ return updatedData; }
}
