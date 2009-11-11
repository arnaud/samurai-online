package client.modele;

import modele.objet.ExtendedNode;

import com.jme.image.Texture;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

public class Mer extends ExtendedNode {
	
	private static final long serialVersionUID = 1L;
	
	private WaterSurface surface;
	
	public Mer(Monde monde) {

		surface = new WaterSurface(this, "water", 64, 64, .1f);
		surface.setRenderQueueMode(Renderer.QUEUE_TRANSPARENT);
		surface.copyTextureCoords(0,1);
	    System.out.println(surface.getColorBuffer());
	    this.attachChild(surface);
	    monde.getRootNode().attachChild(this);

	    TextureState ts = monde.getDisplay().getRenderer().createTextureState();
	    ts.setEnabled(true);
	    Texture t1 = TextureManager.loadTexture(
	        Mer.class.getClassLoader().getResource( "data/textures/water.png"),
	        Texture.MM_LINEAR_LINEAR,
	        Texture.FM_LINEAR);
	    //Environmental Map (reflection of clouds)
	    Texture t = TextureManager.loadTexture(
	            Mer.class.getClassLoader().getResource( "data/textures/clouds.png"),
	        Texture.MM_LINEAR_LINEAR,
	        Texture.FM_LINEAR);
	    t.setApply(Texture.AM_DECAL);
	    t.setBlendColor(new ColorRGBA(50,50,250,10));
	    t1.setBlendColor(new ColorRGBA(50,50,250,10));
	    t.setEnvironmentalMapMode(Texture.EM_SPHERE);
	    ts.setTexture(t1);
	    ts.setTexture(t, 1);
	    setRenderState(ts);
	}
	
	public void makeVagues(){
		surface.makeVagues();
	}
	
	public void makeGoutte(float posx, float posy){
		surface.makeGoutte(posx, posy);
	}
	
	public void setScale(float scale){
		surface.setLocalScale(scale);
		surface.setLocalTranslation(new Vector3f(-2*1984, -10f, -2*1984));
	}
	
	public void setHauteur(float hauteur){
	    this.setLocalTranslation(new Vector3f(0,hauteur,0));
	}
	
	public float getHauteur(){
	    return this.getLocalTranslation().y;
	}
}
