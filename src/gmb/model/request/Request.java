package gmb.model.request;
import gmb.model.user.Member;


public abstract class Request extends Notification
{ 
	//ATTRIBUTES
	protected Member member;
	protected int state = 0;//RequestState.UNHANDELED
	
	//CONSTRUCTORS
	@Deprecated
	protected Request(){}
	
	public Request(Member member, String note)
	{
		super(note);
		this.member = member;
	}
	
	//GET METHODS
	public Member getMember(){ return member; }
	public String getNote(){ return note; }

	public RequestState getState()
	{
		if(this.state == 1){ return RequestState.WITHDRAWN; }
		else
			if(this.state == 2){ return RequestState.ACCEPTED; }
			else
				if(this.state == 3){ return RequestState.REFUSED; }
				else
					return RequestState.UNHANDELED;
	}

	/**
	 * returns the value of the integer which internally encodes the state
	 * @return
	 */
	public int getStateAsInt(){ return state; }
	
	//SET METHODS
//	public void setNote(String note){ this.note = note; }

//	public void setState(RequestState state)
//	{
//		if(state == RequestState.WITHDRAWN){ this.state = 1; }
//		else
//			if(state == RequestState.ACCEPTED){ this.state = 2; }
//			else
//				if(state == RequestState.REFUSED){ this.state = 3; }
//				else
//					this.state = 0; 
//	}

	//OTHER METHODS
	public void resetState(){ state = 0; }
	public boolean withdraw(){ if(state == 0) return false; state = 1; return true;}
	public boolean accept(){ if(state == 0) return false; state = 2; return true;}
	public boolean refuse(){ if(state == 0) return false; state = 3; return true;}
}
