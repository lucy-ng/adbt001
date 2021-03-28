package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;
import javax.sound.sampled.*;
import java.io.IOException;

public class ToastCollision implements CollisionListener {
    private final Toast toast;
    private static SoundClip toastCollisionSound;
    private static SoundClip breadCollisionSound;

    public ToastCollision(Toast toast){
        this.toast = toast;
    }

    @Override
    public void collide(CollisionEvent collisionEvent) {
        // Player collide with knife
        // Toast loses 1 heart
        if (collisionEvent.getReportingBody() == toast && collisionEvent.getOtherBody() instanceof Knife){
            toastCollisionSound.play();
            toast.loseHearts();
        }
        // Player collide with fork
        // Toast loses 1 heart
        else if (collisionEvent.getReportingBody() instanceof Fork && collisionEvent.getOtherBody() == toast){
            collisionEvent.getReportingBody().destroy();
            toastCollisionSound.play();
            toast.loseHearts();
        }
        // Player collide with bread
        // Pick up bread weapon
        else if (collisionEvent.getReportingBody() instanceof Bread && collisionEvent.getOtherBody() == toast){
            breadCollisionSound.play();
            collisionEvent.getReportingBody().destroy();
            toast.setBread();
        }
        // Player collide with spoon
        // Toast loses 1 heart
        else if (collisionEvent.getReportingBody() instanceof Spoon && collisionEvent.getOtherBody() == toast){
            collisionEvent.getReportingBody().destroy();
            toastCollisionSound.play();
            toast.loseHearts();
        }
    }

    // Collision sound
    static {
        try {
            /*
            https://freesound.org/people/cabled_mess/sounds/371451/
             */
            toastCollisionSound = new SoundClip("data/collide.wav");
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    // Bread pickup sound
    static {
        try {
            /*
            https://freesound.org/people/grunz/sounds/109663/
             */
            breadCollisionSound = new SoundClip("data/breadsuccess.wav");
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
