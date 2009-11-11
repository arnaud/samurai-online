package client.modele;

import javax.swing.ImageIcon;

import modele.monde.DataRawHeightMap;

import com.jme.image.Texture;
import com.jme.light.DirectionalLight;
import com.jme.math.Vector3f;
import com.jme.renderer.ColorRGBA;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.CullState;
import com.jme.scene.state.LightState;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.terrain.util.ProceduralTextureGenerator;

public class Carte extends modele.monde.Carte {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructeur appelé par le client lors de l'initialisation du Terrain.
	 * @param client
	 */
	public Carte(Monde client) {
        this(client.getRootNode(), client.getLightState(), client.getDisplay());
	}
	
	private Carte(Node rootNode, LightState lightState, DisplaySystem display){
        super(new DataRawHeightMap());
        // Attache
        rootNode.attachChild(this);
        // Applique des effets visuels
        applyRenderingAndCullStateAndLightState(rootNode, lightState, display);
        // Applique les textures
        applyTextures(rootNode, display);
	}
	
	private void applyRenderingAndCullStateAndLightState(Node rootNode, LightState lightState, DisplaySystem display){

        rootNode.setRenderQueueMode(Renderer.QUEUE_OPAQUE);

        display.getRenderer().setBackgroundColor(
                new ColorRGBA(0.5f, 0.5f, 0.5f, 1));

        DirectionalLight dr = new DirectionalLight();
        dr.setEnabled(true);
        dr.setDiffuse(new ColorRGBA(1.0f, 1.0f, 1.0f, 1.0f));
        dr.setAmbient(new ColorRGBA(0.5f, 0.5f, 0.5f, 1.0f));
        dr.setDirection(new Vector3f(0.5f, -0.5f, 0));

        CullState cs = display.getRenderer().createCullState();
        cs.setCullMode(CullState.CS_BACK);
        cs.setEnabled(true);
        rootNode.setRenderState(cs);

        lightState.detachAll();
        lightState.attach(dr);
	}

	private void applyTextures(Node rootNode, DisplaySystem display){
		
        this.setDetailTexture(1, 32);
        
		ProceduralTextureGenerator pt = new ProceduralTextureGenerator(heightMap);
        pt.addTexture(new ImageIcon(Carte.class.getClassLoader()
                .getResource("data/textures/grassb.png")), -128, 0, 128);
        pt.addTexture(new ImageIcon(Carte.class.getClassLoader()
                .getResource("data/textures/dirt.jpg")), 0, 128, 255);
        pt.addTexture(new ImageIcon(Carte.class.getClassLoader()
                .getResource("data/textures/highest.jpg")), 128, 255,
                384);
        
        pt.createTexture(512);

        TextureState ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);
        Texture t1 = TextureManager.loadTexture(pt.getImageIcon().getImage(),
                Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR, true);
        ts.setTexture(t1, 0);

        Texture t2 = TextureManager.loadTexture(Monde.class
                .getClassLoader()
                .getResource("data/textures/Detail.jpg"),
                Texture.MM_LINEAR_LINEAR, Texture.FM_LINEAR);
        ts.setTexture(t2, 1);
        t2.setWrap(Texture.WM_WRAP_S_WRAP_T);

        t1.setApply(Texture.AM_COMBINE);
        t1.setCombineFuncRGB(Texture.ACF_MODULATE);
        t1.setCombineSrc0RGB(Texture.ACS_TEXTURE);
        t1.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
        t1.setCombineSrc1RGB(Texture.ACS_PRIMARY_COLOR);
        t1.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
        t1.setCombineScaleRGB(1.0f);

        t2.setApply(Texture.AM_COMBINE);
        t2.setCombineFuncRGB(Texture.ACF_ADD_SIGNED);
        t2.setCombineSrc0RGB(Texture.ACS_TEXTURE);
        t2.setCombineOp0RGB(Texture.ACO_SRC_COLOR);
        t2.setCombineSrc1RGB(Texture.ACS_PREVIOUS);
        t2.setCombineOp1RGB(Texture.ACO_SRC_COLOR);
        t2.setCombineScaleRGB(1.0f);
        rootNode.setRenderState(ts);
	}

}
