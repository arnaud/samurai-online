package test;

import java.util.logging.Level;

import util.loader.ModelLoader;

import com.jme.app.SimpleGame;
import com.jme.scene.Node;
import com.jme.util.LoggingSystem;

/**
 * Charge un objet depuis un modèle 3D
 * @author Arnaud
 *
 */
public class TestModelLoader extends SimpleGame {


	//private static final String MODELE = "deco/plante.3ds";
	//private static final String TEXTURE = "deco/plntlfs.tif";
	private static final String MODELE = "persos/nain2.ms3d";
	private static final String TEXTURE = "persos/nain2.jpg";
	
	
    public static void main(String[] args) {
        TestModelLoader app = new TestModelLoader();
        app.setDialogBehaviour(SimpleGame.FIRSTRUN_OR_NOCONFIGFILE_SHOW_PROPS_DIALOG);
        // Turn the logger off so we can see the XML later on
        LoggingSystem.getLogger().setLevel(Level.OFF);
        app.start();
    }

    protected void simpleInitGame() {
        // Point to a URL of my model
    	Node node = new Node("node");
    	ModelLoader.loadModelPath(node, MODELE);
    	ModelLoader.loadTexture(node, display, TEXTURE);
    	rootNode.attachChild(node);
    }
}
