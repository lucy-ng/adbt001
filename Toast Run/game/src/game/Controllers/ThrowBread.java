package game.Controllers;
import game.Collisions.*;
import game.GameView;
import game.Levels.*;
import game.Bodies.*;
import org.jbox2d.common.Vec2;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ThrowBread extends MouseAdapter {
    private final GameLevel level;
    private final GameView view;

    public ThrowBread(GameLevel level, GameView view){
        this.level = level;
        this.view = view;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        setBreadPosition(e);
    }

    public void setBreadPosition(MouseEvent e){
        //add bread to world
        Bread bread = new Bread(view.getWorld());
        bread.addCollisionListener(new WallCollision());
        //mouse coordinates
        Point mousePoint = e.getPoint();
        // mouse to world coordinates
        Vec2 worldPoint = view.viewToWorld(mousePoint);
        // coordinates of player
        Vec2 playerPoint = new Vec2(level.getToast().getPosition());

        // Throw bread to right if mouse coordinates are right of player
        if (worldPoint.x - playerPoint.x > 0) {
            bread.setLinearVelocity(new Vec2(10,10));
            bread.setPosition(new Vec2(playerPoint.x + 5, playerPoint.y));
        }
        // Throw bread to left if mouse coordinates are left of player
        else if (worldPoint.x - playerPoint.x < 0) {
            bread.setLinearVelocity(new Vec2(-10,10));
            bread.setPosition(new Vec2(playerPoint.x - 5, playerPoint.y));
        }
        // Throw bread to right if mouse coordinates are right of player
        else{
            bread.setPosition(new Vec2(playerPoint.x + 5, playerPoint.y));
            bread.setLinearVelocity(new Vec2(10,10));
        }
    }
}
