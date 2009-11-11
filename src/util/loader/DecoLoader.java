package util.loader;

import client.modele.ObjetDecoratif;

import com.jme.math.FastMath;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class DecoLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		loadModel(node, "deco", nom, "3ds");
		/*if(nom.compareTo("pont")==0) {
			((ObjetDecoratif)node).setDirection(new Vector3f(-1, 1, 3));
			node.rotateUpTo(new Vector3f(1,0,0));
    		Quaternion z90= new Quaternion();
    		z90.fromAngleNormalAxis(FastMath.DEG_TO_RAD*90, new Vector3f(-1,0,0));
    		node.setLocalRotation(z90);
    		//node.setLocalScale(0.5f);
		}*/
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "deco", nom, "png");
	}
}
