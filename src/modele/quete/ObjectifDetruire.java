package modele.quete;
import modele.objet.*;

public class ObjectifDetruire extends ObjectifQuete{

	private Objet objet;
	
	public ObjectifDetruire (int numero,String description,Objet objet){
		super(numero,description);
		this.objet=objet;
	}
	
}
