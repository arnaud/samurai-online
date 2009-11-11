package util.loader;

import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class CasqueLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		loadModel(node, "objets/casques", nom, "obj");
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "objets/casques", nom, "jpg");
	}
}
