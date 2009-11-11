package util.loader;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;

public class ObjetInventaireLoader {

	/**
	 * Charge l'image d'un objet d'inventaire
	 * @param type
	 * @param nom
	 * @return
	 */
	public static Image loadImageObjet(String type, String nom){
		Image image = null;
		URL url = ObjetInventaireLoader.class.getClassLoader().getResource("data/objets/"+type+"/"+nom+".gif");
		image = new ImageIcon(url).getImage();
		return image;
	}

}
