package gmb.model.request;

import javax.persistence.Embeddable;

import gmb.model.financial.RealAccountData;
import gmb.model.user.Member;

@Embeddable
public class RealAccountDataUpdateRequest extends Request 
{	
	protected RealAccountData updatedData;

	@Deprecated
	protected RealAccountDataUpdateRequest(){}

	public RealAccountDataUpdateRequest(RealAccountData updatedData, Member member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	//GET METHODS
	public RealAccountData getUpdatedData(){ return updatedData; }
}
