package util.loader;

import client.modele.ObjetDecoratif;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class OutilLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		loadModel(node, "objets/outils", nom, "3ds");
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "objets/outils", nom, "png");
	}
}
