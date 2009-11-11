package client.gui;

import java.net.URL;

import client.modele.Monde;

import com.jme.image.Image;
import com.jme.image.Texture;
import com.jme.input.AbsoluteMouse;
import com.jme.scene.Spatial;
import com.jme.scene.state.AlphaState;
import com.jme.scene.state.TextureState;
import com.jme.util.TextureManager;

public class Curseur extends AbsoluteMouse {
	
	private static final long serialVersionUID = 1L;
	
	private boolean isVisible;

	public Curseur(Monde monde) {

        super( "Curseur", monde.getDisplay().getWidth(), monde.getDisplay().getHeight() );
        
        isVisible = false;
        
        // Get a picture for my mouse.
        TextureState ts = monde.getDisplay().getRenderer().createTextureState();
        URL cursorLoc;
        cursorLoc = Curseur.class.getClassLoader().getResource("data/cursor/cursor1.png" );
        Texture t = TextureManager.loadTexture( cursorLoc, Texture.MM_LINEAR,
                Texture.FM_LINEAR, Image.GUESS_FORMAT_NO_S3TC, 1, true );
        ts.setTexture( t );
        this.setRenderState( ts );

        // Make the mouse's background blend with what's already there
        AlphaState as = monde.getDisplay().getRenderer().createAlphaState();
        as.setBlendEnabled( true );
        as.setSrcFunction( AlphaState.SB_SRC_ALPHA );
        as.setDstFunction( AlphaState.DB_ONE_MINUS_SRC_ALPHA );
        as.setTestEnabled( true );
        as.setTestFunction( AlphaState.TF_GREATER );
        this.setRenderState( as );

        // Assign the mouse to an input handler
        this.registerWithInputHandler( monde.getInput() );

        monde.getRootNode().attachChild( this );

        // important for JMEDesktop: use system coordinates
        this.setUsingDelta( false );
        this.getXUpdateAction().setSpeed( 1 );
        this.getYUpdateAction().setSpeed( 1 );

        this.setCullMode( Spatial.CULL_NEVER );
        
        //this.setLocalTranslation(new Vector3f(display.getWidth()/2, display.getHeight()/2, 0));
	}
	
	public void setVisible(boolean visibility) {
		isVisible = visibility;
		if(isVisible)
			this.setZOrder(0);
		else
			this.setZOrder(2);
	}
	
	public boolean isVisible() {
		return isVisible;
	}

}
