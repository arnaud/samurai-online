package test;

import java.nio.FloatBuffer;

import com.jme.renderer.Camera;
import com.jme.math.Vector3f;
import org.lwjgl.openal.AL10;
import org.lwjgl.BufferUtils;

/**
 * @author Alberto Plebani
 * @author Irrisor
 */
class OpenALListenerNode extends ListenerNode {
    protected Camera camera;
    protected final Vector3f lastCameraLocation = new Vector3f( Float.NaN, Float.NaN, Float.NaN );
    protected final Vector3f lastCameraDirection = new Vector3f();
    protected final Vector3f lastCameraUp = new Vector3f();
    protected FloatBuffer listenerPos = BufferUtils.createFloatBuffer( 3 ).put( new float[]{0.0f, 0.0f, 0.0f} );
    protected FloatBuffer listenerVel = BufferUtils.createFloatBuffer( 3 ).put( new float[]{0.0f, 0.0f, 0.0f} );
    protected FloatBuffer listenerOri = BufferUtils.createFloatBuffer( 6 ).put( new float[]{0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f} );

    public OpenALListenerNode( String name ) {
        super( name );

        OpenALSoundNode.initAL();
        initFlip();
        initPos();
    }

    private final Vector3f direction = new Vector3f();
    private final Vector3f up = new Vector3f();

    /**
     * update listener position acording to cam
     * @param time
     * @param initiator
     */
    public void updateGeometricState( float time, boolean initiator ) {
        super.updateGeometricState( time, initiator );

        adjustCameraData();
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for basic inizialization.
     */
    private void initPos() {
        AL10.alListener( AL10.AL_POSITION, listenerPos );
        AL10.alListener( AL10.AL_VELOCITY, listenerVel );
        AL10.alListener( AL10.AL_ORIENTATION, listenerOri );
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for basic inizialization.
     */
    private void initFlip() {
        listenerPos.flip();
        listenerVel.flip();
        listenerOri.flip();
    }

    public void setCamera( Camera camera ) {
        this.camera = camera;
        adjustCameraData();
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for setting data regarding ears position and
     * orientation (equivalent to the cam).
     */
    protected void adjustCameraData() {
        Vector3f camActualLocation;
        Vector3f camActualDirection;
        Vector3f camActualUp;
        if ( this.camera != null ) {
            camActualLocation = this.camera.getLocation();
            camActualDirection = this.camera.getDirection();
            camActualUp = this.camera.getUp();
        } else {
            camActualLocation = getLocalTranslation();
            camActualDirection = getLocalRotation().multLocal( direction.set( 0, 0, -1 ) );
            camActualUp = getLocalRotation().multLocal( up.set( 0, 1, 0 ) );
        }

        if ( ( !( camActualDirection.equals( this.lastCameraDirection ) ) )
                || ( !( camActualLocation.equals( this.lastCameraLocation ) ) )
                || ( !( camActualUp.equals( this.lastCameraUp ) ) ) ) {
            //System.out.println("DifferentEars");
            this.lastCameraLocation.set( camActualLocation );
            this.lastCameraDirection.set( camActualDirection );
            this.lastCameraUp.set( camActualUp );

            listenerPos.put( 0, camActualLocation.x );
            listenerPos.put( 1, camActualLocation.y );
            listenerPos.put( 2, camActualLocation.z );

            listenerOri.put( 0, camActualDirection.x );
            listenerOri.put( 1, camActualDirection.y );
            listenerOri.put( 2, camActualDirection.z );
            listenerOri.put( 3, camActualUp.x );
            listenerOri.put( 4, camActualUp.y );
            listenerOri.put( 5, camActualUp.z );
            AL10.alListener( AL10.AL_POSITION, listenerPos );
            AL10.alListener( AL10.AL_ORIENTATION, listenerOri );
        }
    }
}

/*
 * $log$
 */

