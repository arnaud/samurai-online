package modele.quete;

public class ObjectifRencontrer extends ObjectifQuete{

	private int idPerso;
	
	public ObjectifRencontrer (int numero,String description,int idPerso){
		super(numero,description);
		this.idPerso = idPerso;
	}	
	
	public void getInfos(){
		super.getInfos();
		System.out.println("   ID du perso : "+idPerso);	
	}
	
	public int getIdPerso(){
		return idPerso;
	}
	
	public void setIdPerso(int id){
		this.idPerso=id;
	}
}
