package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;

public class WallCollision implements CollisionListener {
    // Destroy bodies if collide with platforms or walls
    @Override
    public void collide(CollisionEvent collisionEvent) {
        if (collisionEvent.getOtherBody() instanceof Wall){
            collisionEvent.getReportingBody().destroy();
        }
    }
}
