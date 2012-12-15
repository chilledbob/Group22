package gmb.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

//@Entity
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersiObject 
{
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	protected int persistenceID;

	public int getId(){ return persistenceID;}

	protected PersiObject(){}

	public PersiObject DB_ADD(){ return GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
}
