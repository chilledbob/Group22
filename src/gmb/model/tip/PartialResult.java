package gmb.model.tip;

public class PartialResult 
{
	protected String clubname;
	protected int score;
	
	@Deprecated
	protected PartialResult(){}
	
	public PartialResult(String clubname, int score)
	{
		this.clubname = clubname;
		this.score = score;
	}
	
	public String getClubName(){ return clubname; }
	public int getScore(){ return score; }
}
