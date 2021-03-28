package game.Collisions;
import city.cs.engine.*;
import game.Bodies.*;
import game.Levels.GameLevel;
import org.jbox2d.common.Vec2;
import java.util.Random;

public class SpawnSpoon implements DestructionListener {
    private final Boy boy;
    private final GameLevel level;

    public SpawnSpoon(GameLevel level, Boy boy){
        this.level = level;
        this.boy = boy;
    }

    // If a spoon is destroyed, spawn spoon
    @Override
    public void destroy(DestructionEvent destructionEvent) {
        if (destructionEvent.getSource() instanceof Spoon){
            spawnSpoon();
        }
    }

    public void spawnSpoon(){
        // Spawn spoon with different speeds
        Random random = new Random();
        int speed = random.nextInt(10);
        Spoon spoon = new Spoon(level);
        spoon.setLinearVelocity(new Vec2(-speed,speed));
        Vec2 boyPosition = new Vec2(boy.getPosition());
        // If boy is destroyed, destroy spoons
        if (boy.get_isDestroyed()){
            spoon.destroy();
        }
        spoon.setPosition(new Vec2(boyPosition.x - 5, boyPosition.y + 5));
        spoon.addCollisionListener(new WallCollision());
        spoon.addCollisionListener(new ToastCollision(level.getToast()));
        spoon.addDestructionListener(new SpawnSpoon(level,boy));
    }
}