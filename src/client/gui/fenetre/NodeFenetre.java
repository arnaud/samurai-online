package client.gui.fenetre;

import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.Spatial;
import com.jme.scene.state.LightState;

public class NodeFenetre extends Node {
	
	private static final long serialVersionUID = 1L;
	
	private Fenetre fenetre;

	public NodeFenetre(String nomNode, Fenetre fenetre) {
		super(nomNode);
		this.fenetre = fenetre;
        this.setRenderQueueMode(Renderer.QUEUE_ORTHO);
        this.setCullMode(Spatial.CULL_NEVER );
        this.setLightCombineMode(LightState.OFF);
        this.attachChild(fenetre);
	}
	
	public void refresh(){
		fenetre.refresh();
	}

}
