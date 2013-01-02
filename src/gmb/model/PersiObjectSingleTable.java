package gmb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

/**
 * Abstract super class for all persistent classes in the system.
 * (Except for the member class, which handles this functionality on her own)
 */
@MappedSuperclass
public abstract class PersiObjectSingleTable 
{
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	protected int persistenceID;

	public int getId(){ return persistenceID;}

	public PersiObjectSingleTable DB_ADD(){ return GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
}
