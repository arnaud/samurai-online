package test;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import com.jme.math.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 * This class is based on the OpenAL LWJGL tutorial. It is an implementation of {@link SoundNode}.
 * Created on 29-mar-2006
 *
 * @author Alberto Plebani
 */
class OpenALSoundNode extends SoundNode {

    private final Vector3f lastPosition = new Vector3f();
    private final Vector3f lastVelocity = new Vector3f();
    private String sampleAddress;
    private boolean loop = false;

    public static final int PLAYING = 0;
    public static final int PAUSED = 1;
    public static final int STOPPED = 2;
    public static final int UNDEFINED = 3;

    private final int  buffer;
    private final int source;
    private FloatBuffer sourcePos = BufferUtils.createFloatBuffer( 3 ).put( new float[]{0.0f, 0.0f, 0.0f} );
    private FloatBuffer sourceVel = BufferUtils.createFloatBuffer( 3 ).put( new float[]{0.0f, 0.0f, 0.0f} );

    /**
     * This is the only costructor. Use this every time you want to
     * instantiate a new OpenALSoundNode (long class name only because
     * SoundNode is already in use by two different classes!)
     *
     * @param nodeName: the name of the node that you want to assign.
     */
    public OpenALSoundNode( String nodeName ) {
        super( nodeName );
        this.lastPosition.set( this.getWorldTranslation() ).addLocal( this.getLocalTranslation() );
        initAL();
        initFlip();

        // Bind the buffer with the source.
        IntBuffer intBuffer = BufferUtils.createIntBuffer( 1 );
        AL10.alGenSources( intBuffer );
        source = intBuffer.get( 0 );

        // Load wav data into a buffer.
        intBuffer.rewind();
        AL10.alGenBuffers( intBuffer );
        buffer = intBuffer.get( 0 );
    }

    protected void finalize() throws Throwable {
        IntBuffer intBuffer = BufferUtils.createIntBuffer( 1 );
        intBuffer.put( 0, source );
        AL10.alDeleteSources( intBuffer );

        intBuffer.rewind();
        intBuffer.put( 0, buffer );
        AL10.alDeleteBuffers( intBuffer );

        super.finalize();
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for basic inizialization.
     */
    private void initFlip() {
        sourcePos.flip();
        sourceVel.flip();
    }

    /**
     * This method returns the last location of the node.
     *
     * @return the lastPosition.
     */
    public Vector3f getLastPosition() {
        return lastPosition;
    }

    public int setSampleAddress( String sampleAddress ) {
        this.sampleAddress = sampleAddress;

        if ( AL10.alGetError() != AL10.AL_NO_ERROR ) {
            return AL10.AL_FALSE;
        }

        WaveData waveFile = WaveData.create( sampleAddress );

        AL10.alBufferData( buffer, waveFile.format, waveFile.data, waveFile.samplerate );
        waveFile.dispose();

        if ( AL10.alGetError() != AL10.AL_NO_ERROR ) {
            return AL10.AL_FALSE;
        }

        AL10.alSourcei( source, AL10.AL_BUFFER, buffer );
        AL10.alSourcef( source, AL10.AL_PITCH, 1.0f );
        AL10.alSourcef( source, AL10.AL_GAIN, 1.0f );
        AL10.alSource( source, AL10.AL_POSITION, sourcePos );
        AL10.alSource( source, AL10.AL_VELOCITY, sourceVel );

        if ( this.loop ) {
            AL10.alSourcei( source, AL10.AL_LOOPING, AL10.AL_TRUE );
        }
        else {
            AL10.alSourcei( source, AL10.AL_LOOPING, AL10.AL_FALSE );
        }

        AL10.alSourcef( source, AL10.AL_ROLLOFF_FACTOR, 0.1f );
        AL10.alListenerf( AL10.AL_GAIN, 1 );

        // Do another error check and return.
        if ( AL10.alGetError() == AL10.AL_NO_ERROR ) {
            return AL10.AL_TRUE;
        }

        return AL10.AL_FALSE;
    }

    public String getSampleAddress() {
        return sampleAddress;
    }

    public void setLoop( boolean lo ) {
        this.loop = lo;
        if ( this.loop ) {
            AL10.alSourcei( source, AL10.AL_LOOPING, AL10.AL_TRUE );
        }
        else {
            AL10.alSourcei( source, AL10.AL_LOOPING, AL10.AL_FALSE );
        }
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for basic inizialization.
     */
    static void initAL() {
        if ( !AL.isCreated() ) {
            try {
                AL.create( null, 15, 22050, true );
            } catch ( LWJGLException le ) {
                le.printStackTrace();
                return;
            }
            AL10.alGetError();
        }
    }

    /**
     * In this special
     * Node implementation, method it addiotnally adjusts the sound source
     * location and/or the ear location and orientation, but only if one of
     * this has changed since last draw. This method is called through the
     * various update chain.
     */
    public void updateGeometricState( float time, boolean initiator ) {
        super.updateGeometricState( time, initiator );

        //System.out.println("Check");
        Vector3f actualPosition = this.getWorldTranslation().add( this.getLocalTranslation() );
        //System.out.println(actualPosition+":"+this.lastPosition);
        if ( !actualPosition.equals( this.lastPosition ) ) {
            //System.out.println("DifferentPosition");
            lastVelocity.set( actualPosition ).subtractLocal( lastPosition ).divideLocal( time );
            lastPosition.set( actualPosition );
            updateLocation();
        }
    }

    /**
     * Users should never call this method manually! This method is
     * responsible for updating the source location.
     */
    private void updateLocation() {
        sourcePos.put( 0, this.lastPosition.x );
        sourcePos.put( 1, this.lastPosition.y );
        sourcePos.put( 2, this.lastPosition.z );
        AL10.alSource( source, AL10.AL_POSITION, sourcePos );
        sourceVel.put( 0, this.lastVelocity.x );
        sourceVel.put( 1, this.lastVelocity.y );
        sourceVel.put( 2, this.lastVelocity.z );
        AL10.alSource( source, AL10.AL_VELOCITY, sourceVel );
    }

    public void play() {
        AL10.alSourcePlay( source );
    }

    public void stop() {
        AL10.alSourceStop( source );
    }

    public void pause() {
        AL10.alSourcePause( source );
    }

    public boolean isPlaying() {
        int result = AL10.alGetSourcei( source, AL10.AL_SOURCE_STATE );
        return result == AL10.AL_PLAYING;
    }

    public boolean isStopped() {
        int result = AL10.alGetSourcei( source, AL10.AL_SOURCE_STATE );
        return result == AL10.AL_STOPPED;
    }

    public boolean isPaused() {
        int result = AL10.alGetSourcei( source, AL10.AL_SOURCE_STATE );
        return result == AL10.AL_PAUSED;
    }

    /**
     * This method check the state. There are three states allowed: playing,
     * stopped and paused, which refers to the state of the sample.
     * There is another state which is undefined. This is used when the
     * state of the sample is not in one of the other states.
     *
     * @return The state by the use of the constants defined in this class.
     */
    public int checkState() {
        int result = AL10.alGetSourcei( source, AL10.AL_SOURCE_STATE );
        if ( result == AL10.AL_PAUSED ) {
            return OpenALSoundNode.PAUSED;
        }
        if ( result == AL10.AL_PLAYING ) {
            return OpenALSoundNode.PLAYING;
        }
        if ( result == AL10.AL_STOPPED ) {
            return OpenALSoundNode.STOPPED;
        }
        return OpenALSoundNode.UNDEFINED;
    }

}