package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;
import javax.sound.sampled.*;
import java.io.IOException;

public class Pickup implements CollisionListener {
    private final Toast toast;
    private static SoundClip jamPickupSound;
    private static SoundClip keyPickupSound;

    public Pickup(Toast toast) {this.toast = toast;}

    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Jam){
            // Pick up jam and destroy when picked up
            jamPickupSound.play();
            toast.gainHearts();
            collisionEvent.getOtherBody().destroy();
        }
        else if (collisionEvent.getOtherBody() instanceof Key){
            // Pick up key and destroy when picked up
            keyPickupSound.play();
            toast.pickupKey();
            collisionEvent.getOtherBody().destroy();
        }
    }

    // Jam pickup sound
    static {
        try {
            /*
            https://freesound.org/people/kianda/sounds/328120/
             */
            jamPickupSound = new SoundClip("data/jamsuccess.wav");
        }
        catch (UnsupportedAudioFileException|IOException|LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // Key pickup sound
    static {
        try {
            /*
            https://freesound.org/people/JustInvoke/sounds/446111/
             */
            keyPickupSound = new SoundClip("data/keysuccess.wav");
        }
        catch (UnsupportedAudioFileException|IOException|LineUnavailableException e) {
            System.out.println(e);
        }
    }
}


