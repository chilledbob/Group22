package gmb.model;

import gmb.model.financial.FinancialManagement;
import gmb.model.group.GroupManagement;
import gmb.model.member.MemberManagement;

import gmb.model.tip.TipManagement;

/**
 * The lottery singleton.<br>
 * Serves as access interface to the whole system.
 */
public class Lottery 
{
	protected static Lottery INSTANCE = null;
	 
	protected FinancialManagement financialManagement;
	protected MemberManagement memberManagement;
	protected GroupManagement groupManagement;
	protected TipManagement tipManagement;
	
	protected Timer timer;
	
	@Deprecated
	protected Lottery(){}
	
	protected Lottery(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		this.financialManagement = financialManagement;
		this.memberManagement = memberManagement;
		this.groupManagement = groupManagement;
		this.tipManagement = tipManagement;
		
		this.timer = new Timer();
	}
	
	public static void Instanciate(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		if(INSTANCE != null) return;
		
		INSTANCE = new Lottery( financialManagement,  memberManagement,  groupManagement,  tipManagement);
	}

	public static Lottery getInstance()
	{
		assert INSTANCE != null : "LOTTERY INSTANCE DOES NOT EXIST!";
		return INSTANCE;
	}

	public FinancialManagement getFinancialManagement()
	{
		FinancialManagement obj = (FinancialManagement) GmbPersistenceManager.get(FinancialManagement.class, financialManagement.getId()); 
		
		if(obj != null) return obj;
		else
			return this.financialManagement;
	}
	
	public MemberManagement getMemberManagement()
	{ 
		MemberManagement obj =  (MemberManagement) GmbPersistenceManager.get(MemberManagement.class, memberManagement.getId()); 
		
		if(obj != null) return obj;
		else
			return this.memberManagement;
	}
	
	public GroupManagement getGroupManagement()
	{ 
		GroupManagement obj =  (GroupManagement) GmbPersistenceManager.get(GroupManagement.class, groupManagement.getId()); 
		
		if(obj != null) return obj;
		else
			return this.groupManagement;
	}
	
	public TipManagement getTipManagement()
	{ 
		TipManagement obj =  (TipManagement) GmbPersistenceManager.get(TipManagement.class, tipManagement.getId()); 
		
		if(obj != null) return obj;
		else
			return this.tipManagement;
	}
	
	public Timer getTimer(){
		Timer obj = (Timer) GmbPersistenceManager.get(Timer.class, this.timer.getId());
		
		if(obj != null) return obj;
		else
			return this.timer;
	}
	
	public void setTimer(Timer timer){ this.timer = timer; }
}
