package gmb.model.request.data;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import gmb.model.financial.container.RealAccountData;
import gmb.model.member.Customer;
import gmb.model.request.Request;

@Entity
public class RealAccountDataUpdateRequest extends Request 
{	
	@OneToOne
	protected RealAccountData updatedData;

	@Deprecated
	protected RealAccountDataUpdateRequest(){}
	
	public RealAccountDataUpdateRequest(RealAccountData updatedData, Customer member, String note)
	{
		super(member, note);
		this.updatedData = updatedData;
	}
	
	/**
	 * [intended for direct usage by controller]
	 * Return codes:
	 * 0 - successful
	 * 1 - failed because state was not "UNHANDLED"
	 */
	public int accept()
	{
		if(super.accept() == 0)
			{
				((Customer)member).getBankAccount().setRealAccountData(updatedData);
				return 0;
			}
		else
			return 1;
	}
	
	public RealAccountData getUpdatedData(){ return updatedData; }
}
