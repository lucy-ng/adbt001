package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;
import game.Levels.*;
import game.*;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class DoorCollision implements CollisionListener {
    private final GameLevel level;
    private final Game game;
    private static SoundClip levelSuccess;

    public DoorCollision(GameLevel level, Game game){
        this.level = level;
        this.game = game;
    }

    // If toast has the key and collides with door, go to next level
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Door && level.isComplete()){
            levelSuccess.play();
            game.goToNextLevel();
        }
        // Destroy bread if it touches door
        else if (collisionEvent.getReportingBody() instanceof Bread && collisionEvent.getOtherBody() instanceof Door){
            collisionEvent.getReportingBody().destroy();
        }
    }

    // play level success sound
    static {
        try {
            /*
            https://freesound.org/people/grunz/sounds/109662/
             */
            levelSuccess = new SoundClip("data/levelsuccess.wav");
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
}
