package gmb.model;

import gmb.model.financial.FinancialManagement;

import gmb.model.tip.TipManagement;
import gmb.model.user.GroupManagement;
import gmb.model.user.MemberManagement;

import org.salespointframework.core.time.DefaultTime;

public class Lottery 
{
	private static Lottery INSTANCE = null;
	 
	private FinancialManagement financialManagement;
	private MemberManagement memberManagement;
	private GroupManagement groupManagement;
	private TipManagement tipManagement;
	
	private DefaultTime timer;
	
	@Deprecated
	private Lottery(){}
	
	private Lottery(FinancialManagement financialManagement, MemberManagement memberManagement, GroupManagement groupManagement, TipManagement tipManagement)
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
