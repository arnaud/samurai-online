package test;

import com.jme.renderer.Camera;
import com.jme.scene.Node;


/**
 * This class represents a simple sound sample in the scenegraph. It is adapted to allow
 * the creation of a com.jme.scene.Node which can be attached to other Node
 * instances. The sound, obviously, will follow the node itself!
 * <p/>
 * The following is the right way to operate with a SoundNode:
 * <p/>
 * Node aSimpleNode=new Node("NodeName");
 * ...
 * <p/>
 * SoundNode music=SoundNode.create("MusicNode");
 * music.setSampleAddress("turn.wav"); //Address as a String
 * music.setCamera(this.cam); //Our cam becomes our ears!
 * music.setLoop(true); //If you want looping (by default is false)
 * music.play(); //Now listen!
 * aSimpleNode.attachChild(music);
 * <p/>
 * ...
 * music.stop() //Easy to understand!
 * <p/>
 * There are also a few methods to retrieve and change the state of the sample.
 * <p/>
 * If you want to have your sound used as a background sound, you should
 * create an OpenALSoundNode and attach it to your cameraNode. By this
 * way your sound node will always follow your camera and will always face
 * it the same way: so you'll have your soundtrack. Maybe if i'll have
 * time i'll create a method which easily configure this without the need
 * of a CameraNode.
 * <p/>
 * This is still a basic implementation, but makes its job well!
 * Only one note: actually this class is able to load only .wav sound file.
 * <p/>
 * FEEL FREE TO MODIFY THIS CLASS FOR ANY PURPOSE AND REMEMBER TO TEST THIS
 * CLASS WHICH HAS NOT BEEN FULLY TESTED!
 * <p/>
 * Note: positioning results will usually be better when using mono sound samples
 * <p/>
 * TODO:
 * Implement various sound format reading
 * Implement event triggers (actually you have to start playing manually)
 * Implement soundtrack feature (as previously explained)
 *
 * @author Alberto Plebani
 * @author Irrisor (interface extracted)
 */
public abstract class SoundNode extends Node {
    public SoundNode( String name ) {
        super( name );
    }

    /**
     * This method is used to load the sample. Call this passing it a valid
     * address String.
     *
     * @param sampleAddress The sample address to load.
     */
    public abstract int setSampleAddress( String sampleAddress );

    /**
     * This method is used to make the sample playing in loop
     *
     * @param lo: set to true if you want loop or false if not.
     */
    public abstract void setLoop( boolean lo );

    /**
     * This method plays the sample.
     */
    public abstract void play();

    /**
     * This method stops the sample.
     */
    public abstract void stop();

    /**
     * This method pauses the sample. Another call to play() makes the sample
     * playing from where it was paused.
     */
    public abstract void pause();

    /**
     * This method check if the sample is playing.
     *
     * @return true if the sample is actually playing
     */
    public abstract boolean isPlaying();

    /**
     * This method check if the sample is stopped.
     *
     * @return true if the sample is actually stopped
     */
    public abstract boolean isStopped();

    /**
     * This method check if the sample is paused.
     *
     * @return true if the sample is actually paused
     */
    public abstract boolean isPaused();

    /**
     * Use this method every time you want to
     * instantiate a new SoundNode
     *
     * @param name the name of the node that you want to assign.
     */
    public static SoundNode create( String name ) {
        return new OpenALSoundNode( name );
    }

    /**
     * @return sample address set by {@link #setSampleAddress(String)}
     */
    public abstract String getSampleAddress();
}

/*
 * $log$
 */

