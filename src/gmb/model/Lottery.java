package gmb.model;

import gmb.model.financial.FinancialManagement;

import gmb.model.tip.TipManagement;
import gmb.model.user.GroupManagement;
import gmb.model.user.MemberManagement;

import org.salespointframework.core.time.DefaultTime;

public class Lottery 
{
	protected static Lottery INSTANCE = null;
	 
	protected FinancialManagement financialManagement;
	protected MemberManagement memberManagement;
	protected GroupManagement groupManagement;
	protected TipManagement tipManagement;
	
	protected DefaultTime timer;
	
	@Deprecated
	protected Lottery(){}
	
	protected Lottery(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		this.financialManagement = financialManagement;
		this.memberManagement = memberManagement;
		this.groupManagement = groupManagement;
		this.tipManagement = tipManagement;
		
		this.timer = new DefaultTime();
	}
	
	public void Instanciate(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
	{
		if(INSTANCE != null) return;
		
		INSTANCE = new Lottery( financialManagement,  memberManagement,  groupManagement,  tipManagement);
	}

	public static Lottery getInstance()
	{
		assert INSTANCE != null : "LOTTERY INSTANCE DOES NOT EXIST!";
		return INSTANCE;
	}

	public FinancialManagement getFinancialManagement(){ return financialManagement; }
	public MemberManagement getMemberManagement(){ return memberManagement; }
	public GroupManagement getGroupManagement(){ return groupManagement; }
	public TipManagement getTipManagement(){ return tipManagement; }
	
	public DefaultTime getTimer(){ return timer; }
}
