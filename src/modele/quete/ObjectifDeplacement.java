package modele.quete;
import modele.monde.*;

public class ObjectifDeplacement extends ObjectifQuete{

	private Zone zone;
	
	public ObjectifDeplacement (int numero,String description,Zone zone){
		super (numero,description); 
		this.zone = zone;
	}
	
	
	public Zone getZone(){
		return zone;
	}
	
	public void setZone(Zone zone){
		this.zone=zone;
	}
}
