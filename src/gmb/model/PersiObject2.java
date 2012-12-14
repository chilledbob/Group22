package gmb.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PersiObject2 
{
	@Id
	@GeneratedValue (strategy=GenerationType.AUTO)
	protected int pId;
	
	public int getId(){ return pId;}
	
	protected PersiObject2(){}
	
	public void DB_ADD(){ GmbPersistenceManager.add(this); }
	public void DB_UPDATE(){ GmbPersistenceManager.update(this); }
	public void DB_REMOVE(){ GmbPersistenceManager.remove(this); }
}
