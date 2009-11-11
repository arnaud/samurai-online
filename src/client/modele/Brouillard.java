package client.modele;


import com.jme.renderer.ColorRGBA;
import com.jme.scene.state.FogState;

public class Brouillard extends FogState {
	
	private static final long serialVersionUID = 1L;
	
	private int end = 800;
	private int start = 500;

	public Brouillard(Monde client) {

        FogState fs = client.getDisplay().getRenderer().createFogState();
        fs.setDensity(0.5f);
        fs.setEnabled(true);
        fs.setColor(new ColorRGBA(0.5f, 0.5f, 0.5f, 0.5f));
        fs.setEnd(end);
        fs.setStart(start);
        fs.setDensityFunction(FogState.DF_LINEAR);
        fs.setApplyFunction(FogState.AF_PER_VERTEX);
        client.getRootNode().setRenderState(fs);
	}

	public void apply() {
		apply();
	}
	
	public void setStart(int start){
		this.start = start;
	}
	
	public void setEnd(int end){
		this.end = end;
	}
	
	public int getStart(){
		return start;
	}
	
	/**
	 * Accesseur utilisé pour récupérer le rayon dfe visibilité du personnage
	 * @return
	 */
	public int getEnd(){
		return end;
	}

}
