package util.loader;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class PersoLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		if(nom.equals("chevre")) {
			loadModel(node, "persos", nom, "3ds");
		} else {
			loadModel(node, "persos", nom, "ms3d");
		}
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "persos", nom, "jpg");
	}
}
