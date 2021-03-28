package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class BoyCollision implements CollisionListener {
    private final Boy boy;
    private static SoundClip boyCollisionSound;

    public BoyCollision(Boy boy){
        this.boy = boy;
    }

    // If bread collides with boy, destroy bread and decrease boy's lives
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Bread && collisionEvent.getReportingBody() == boy){
            collisionEvent.getOtherBody().destroy();
            boyCollisionSound.play();
            boy.setHearts();
        }
    }

    // play boy collide with bread sound
    static {
        try {
            /*
            https://freesound.org/people/pan14/sounds/263655/
             */
            boyCollisionSound = new SoundClip("data/boycollision.wav");
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
