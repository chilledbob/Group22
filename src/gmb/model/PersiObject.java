package gmb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Abstract super class for all persistent classes in the system.<br>
 * (Except for the member class, which handles this functionality on her own)
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PersiObject 
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
