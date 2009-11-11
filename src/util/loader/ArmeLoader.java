package util.loader;

import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class ArmeLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		loadModel(node, "objets/armes", nom, "3ds");
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "objets/armes", nom, "png");
	}
}
