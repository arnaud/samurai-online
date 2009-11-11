package test;

import com.jme.app.SimpleGame;
import com.jme.scene.shape.Sphere;
import com.jme.scene.Controller;
import com.jme.math.Quaternion;
import com.jme.math.Vector3f;
import com.jme.bounding.BoundingSphere;

/**
 * @author
 */
public class TestSoundNode extends SimpleGame {
    protected void simpleInitGame() {
        cam.setLocation( new Vector3f( 0, 0, 5 ) );
        cam.update();
        ListenerNode listenerNode = ListenerNode.create( "listener" );
        listenerNode.setCamera( cam );
        rootNode.attachChild( listenerNode );

        final SoundNode soundNode = SoundNode.create( "sound" );
        soundNode.setSampleAddress( "data/sound/FootstepsMono.wav" );
        soundNode.setLoop( true );
        soundNode.play();
        soundNode.getLocalTranslation().set( 0, 0, -5 );
        rootNode.attachChild( soundNode );

        Sphere sphere = new Sphere( "sphere", 10, 10, 1 );
        soundNode.attachChild( sphere );
        sphere.setModelBound( new BoundingSphere() );
        sphere.updateModelBound();

        soundNode.addController( new Controller() {
            Vector3f axis = new Vector3f( 0, 1, 0 );
            Quaternion rot = new Quaternion();

            public void update( float time ) {
                rot.fromAngleNormalAxis( time/2, axis );
                rot.multLocal( soundNode.getLocalTranslation() );
            }
        } );
    }

    public static void main( String[] args ) {
        new TestSoundNode().start();
    }
}

/*
 * $log$
 */

