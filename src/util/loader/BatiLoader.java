package util.loader;

import com.jme.scene.Node;
import com.jme.system.DisplaySystem;

public class BatiLoader extends ModelLoader {

	public static void loadModel(Node node, String nom){
		loadModel(node, "batiments", nom, "3ds");
	//	if(node.getChildren().size()==0)
	//		loadModel(node, "batiments", nom, "3ds");
		//node.setLocalScale(4f);
	}

	public static void loadTexture(Node node, DisplaySystem display, String nom){
		loadTexture(node, display, "batiments", nom, "jpg");
	}
}
