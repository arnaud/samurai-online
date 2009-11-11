package modele.quete;

public class ObjectifCollecter extends ObjectifQuete{

	int idTypeObjet;
	
	
	public ObjectifCollecter(int numero,String description,int idTypeObjet){
		super(numero,description);
		this.idTypeObjet=idTypeObjet;
	}	


	
}
