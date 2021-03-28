package game.Collisions;
import game.Bodies.*;
import game.Levels.*;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.util.Random;

public class SpawnFork implements DestructionListener {
    private final GameLevel level;

    public SpawnFork(GameLevel level){
        this.level = level;
    }

    // When a fork is destroyed, spawn fork
    @Override
    public void destroy(DestructionEvent destructionEvent) {
        if (destructionEvent.getSource() instanceof Fork){
            spawnFork();
        }
    }

    public void spawnFork(){
        // Spawn fork in random coordinates
        Random random = new Random();
        int position = random.nextInt(15);
        Fork fork = new Fork(level);
        fork.setPosition(new Vec2(position,position));
        fork.addCollisionListener(new ToastCollision(level.getToast()));
        fork.addCollisionListener(new WallCollision());
        fork.addDestructionListener(new SpawnFork(level));
    }
}
