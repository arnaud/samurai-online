package util.loader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import com.jme.image.Texture;
import com.jme.scene.Node;
import com.jme.scene.state.TextureState;
import com.jme.system.DisplaySystem;
import com.jme.util.TextureManager;
import com.jmex.model.XMLparser.JmeBinaryReader;
import com.jmex.model.XMLparser.Converters.AseToJme;
import com.jmex.model.XMLparser.Converters.FormatConverter;
import com.jmex.model.XMLparser.Converters.MaxToJme;
import com.jmex.model.XMLparser.Converters.Md2ToJme;
import com.jmex.model.XMLparser.Converters.Md3ToJme;
import com.jmex.model.XMLparser.Converters.MilkToJme;
import com.jmex.model.XMLparser.Converters.ObjToJme;

/**
 * Classe permettant de charger un modèle (physique) depuis le fichier
 * de données (samourai-data) et d'y appliquer une texture
 * Les modèles, une fois chargés, sont mis en mémoire en vue d'une éventuelle
 * réutilisation
 * @author Arnaud
 *
 */
public abstract class ModelLoader {

	/**
	 * Collection de modèles ayant été chargés
	 */
	private static Collection modeles = new Vector();
	
	/**
	 * Charge le modèle physique depuis le fichier de données (samourai-data)
	 * @param node Node auquel est associé le modèle de données
	 * @param type
	 * @param nom
	 * @param extension
	 */
	protected static void loadModel(Node node, String type, String nom, String extension){
		// Cas où le modèle a déja été utilisé
		System.out.println("> chargement du modèle ["+type+"/"+nom+"] : "+isAlreadyUsed(type, nom));
		if(isAlreadyUsed(type, nom)){
			byte[] is = getModelAlreadyUsed(type, nom);
    		JmeBinaryReader jbr = new JmeBinaryReader();
    		jbr.setProperty("bound", "box");
			try {
				jbr.loadBinaryFormat(node, new ByteArrayInputStream(is));
			} catch (IOException e) {
				e.printStackTrace();
			}
		// Cas où le modèle est chargé pour la première fois
		}else{
			// Chargement du modèle
			byte[] is = loadModelPath(node, type+"/"+nom+"."+extension);
			// Enregistrement du modèle dans la collection
			if(is!=null) {
				addModeleLoaded(is, type, nom);
			}
		}
	}
	
	/**
	 * Charge le modèle physique depuis le fichier de données (samourai-data)
	 * @param node Node auquel est associé le modèle de données
	 * @param path Chemin permettant d'accéder à la données (dans "data/modeles/")
	 */
	public static byte[] loadModelPath(Node node, String path){
        ByteArrayOutputStream BO = new ByteArrayOutputStream();
        URL url = ModelLoader.class.getClassLoader().getResource("data/modeles/"+path);
        byte[] inputStream = null;
        try {
    		FormatConverter convert = null;
    		String[] x = path.split("[.]");
    		String extension = x[1];
    		JmeBinaryReader jbr = new JmeBinaryReader();
    		jbr.setProperty("bound", "box");
    		// On utilise le loader approprié en fonction du type de format
    		if(!extension.equals("jme")){
	    		if(extension.equals("obj")){
	        		convert = new ObjToJme();
	    		}else if(extension.equals("ms3d")){
	        		convert = new MilkToJme();
	    		}else if(extension.equals("3ds")){
	        		convert = new MaxToJme();
	    		}else if(extension.equals("max")){
	        		convert = new MaxToJme();
	    		}else if(extension.equals("ase")){
	        		convert = new AseToJme();
	    		}else if(extension.equals("md2")){
	        		convert = new Md2ToJme();
	    		}else if(extension.equals("ms3d")){
	        		convert = new Md3ToJme();
	    		}else{
	    			System.err.println("Extension "+extension+" inconnue !");
	    			System.exit(-1);
	    		}
	    		convert.convert(url.openStream(), BO);
	    		inputStream = BO.toByteArray();
	            //jbr.setProperty("texurl",ModelLoader.class.getClassLoader().getResource("data/modeles/"));
	    		jbr.loadBinaryFormat(node, new ByteArrayInputStream(inputStream));
    		}else{
    			//Format .JME > non supporté pour le moment
    			System.err.println(node);
    			System.err.println(url);
    			System.err.println(url.openStream());
        		jbr.loadBinaryFormat(node, url.openStream());
    		}
		} catch (IOException e) {
			System.err.println("Modèle non trouvé ! ["+path+"]");
		} catch (NullPointerException npe) {
			System.err.println("Modèle non trouvé ! ["+path+"]");
		}
		return inputStream;
	}
	
	/**
	 * Vérifie si le modèle a déjà été chargé une fois
	 * @param type
	 * @param nom
	 * @return
	 */
	private static boolean isAlreadyUsed(String type, String nom){
		for(Iterator it = modeles.iterator(); it.hasNext();){
			ModeleLoaded modeleLoaded = (ModeleLoaded)it.next();
			if(modeleLoaded.nom.equals(type+"/"+nom)) return true;
		}
		return false;
	}
	
	/**
	 * Retourne le modèle correspondant au type et au nom cherchés (si modèle déjà chargé)
	 * @param type
	 * @param nom
	 * @return
	 */
	private static byte[] getModelAlreadyUsed(String type, String nom){
		for(Iterator it = modeles.iterator(); it.hasNext();){
			ModeleLoaded modeleLoaded = (ModeleLoaded)it.next();
			if(modeleLoaded.nom.equals(type+"/"+nom))
				return modeleLoaded.getIS();
		}
		return null;
	}
	
	/**
	 * Ajoute le modèle dans la collection des modèles ayant déjà été chargés
	 * @param is
	 * @param type
	 * @param nom
	 */
	//private static void addModeleLoaded(Node node, String type, String nom){
	private static void addModeleLoaded(byte[] is, String type, String nom){
		ModeleLoaded modele = new ModeleLoaded(is, type, nom);
		modeles.add(modele);
	}
	
	/**
	 * Applique la texture {type}/{nom}.{extension} sur le node 'node'
	 * @param node Node auquel est appliquée la texture
	 * @param display
	 * @param type
	 * @param nom
	 * @param extension
	 */
	protected static void loadTexture(Node node, DisplaySystem display, String type, String nom, String extension){
        loadTexture(node, display, type+"/"+nom+"."+extension);
	}
	
	/**
	 * Applique la texture se trouvant à {path} sur le node 'node'
	 * @param node Node auquel est appliquée la texture
	 * @param display
	 * @param path Chemin permettant d'accéder à la texture (dans "data/modeles/")
	 */
	public static void loadTexture(Node node, DisplaySystem display, String path){
		System.out.println("Chargement de la texture : " + path);
		TextureState ts = display.getRenderer().createTextureState();
        ts.setEnabled(true);
        try{
        	ts.setTexture(
	            TextureManager.loadTexture(
	            ModelLoader.class.getClassLoader().getResource(
	            "data/modeles/"+path),
	            Texture.MM_LINEAR,
	            Texture.FM_LINEAR));

		}catch(NullPointerException e){
			System.err.println("Texture non chargée ! ("+path+")");
		}
        node.setRenderState(ts);
	}
	
	
	/**
	 * Classe regroupant les informations utiles à un modèle déjà chargé.
	 * Permet de récupérer un clone du modèle utilisé.
	 * @author Arnaud
	 *
	 */
	private static class ModeleLoaded {
		
		private byte[] inputStream;
		
		public String nom;
		
		public ModeleLoaded(byte[] is, String type, String nom){
			//this.node = node;
			inputStream = is;
			this.nom = type+"/"+nom;
		}
		
		public byte[] getIS() {
			return inputStream;
		}
	}
}
