package gmb.model;

public class PersiObject 
{
	protected PersiObject(){}
	
	public void DB_ADD(){ GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
}
