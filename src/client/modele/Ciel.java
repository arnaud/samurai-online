package client.modele;


import com.jme.image.Texture;
import com.jme.scene.Skybox;
import com.jme.util.TextureManager;

public class Ciel extends Skybox {
	
	private static final long serialVersionUID = 1L;

	public Ciel(Monde client) {

        // Create a skybox
        super("Ciel", 163, 163, 163);

        Texture north = TextureManager.loadTexture(
            Monde.class.getClassLoader().getResource(
            "data/textures/Left.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture south = TextureManager.loadTexture(
        		Monde.class.getClassLoader().getResource(
            "data/textures/Right.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture east = TextureManager.loadTexture(
        		Monde.class.getClassLoader().getResource(
            "data/textures/Back.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture west = TextureManager.loadTexture(
        		Monde.class.getClassLoader().getResource(
            "data/textures/Front.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture up = TextureManager.loadTexture(
        		Monde.class.getClassLoader().getResource(
            "data/textures/top.jpg"),
            Texture.MM_LINEAR,
            Texture.FM_LINEAR);
        Texture down = new Texture();

        this.setTexture(Skybox.NORTH, north);
        this.setTexture(Skybox.WEST, west);
        this.setTexture(Skybox.SOUTH, south);
        this.setTexture(Skybox.EAST, east);
        this.setTexture(Skybox.UP, up);
        this.setTexture(Skybox.DOWN, down);
        this.preloadTextures();
        
        setIsCollidable(false);
        
        client.getRootNode().attachChild(this);
	}

}
