package game.Levels;
import city.cs.engine.*;
import game.Bodies.*;
import game.Collisions.*;
import game.Game;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Level3 extends GameLevel implements ActionListener{
    private Timer timer;
    private SolidFixture toasterToast;

    public Level3(Game game){
        super();

        // Make toaster
        PolygonShape toasterBaseShape = new PolygonShape(1.88f,1.01f, 2.3f,0.61f, 2.32f,-1.83f, -2.44f,-1.85f, -2.46f,0.66f, -2.24f,1.01f);
        PolygonShape toasterToastShape = new PolygonShape(0.96f,2.12f, 1.48f,1.56f, 1.38f,1.03f, -1.5f,0.99f, -1.82f,1.56f, -1.47f,2.04f);
        DynamicBody toaster = new DynamicBody(this);
        SolidFixture toasterBase = new SolidFixture(toaster, toasterBaseShape);
        toasterBase.setFriction(5f);
        toasterBase.setRestitution(0);
        toasterToast = new SolidFixture(toaster, toasterToastShape);
        toasterToast.setRestitution(1.5f);
        toaster.addImage(new BodyImage("data/toaster.png", 5f));
        toaster.setPosition(new Vec2(17,-13));

        Color red = new Color(255,46,81);
        // make bottom border
        getGroundBottom().setFillColor(red);
        getGroundBottom().setPosition(new Vec2(0, -15f));
        // make top border
        getGroundTop().setFillColor(red);
        getGroundTop().setPosition(new Vec2(0,15));

        // Create door
        getDoor().setPosition(new Vec2(-17,1));

        // make platforms
        Shelf shelf1 = new Shelf(this);
        shelf1.setPosition(new Vec2(-15,0));
        Shelf shelf2 = new Shelf(this);
        shelf2.setPosition(new Vec2(15,0));

        // Add timer to spawn spoon
        timer = new Timer(2000, this);
        timer.setInitialDelay(100);
        timer.start();
    }

    @Override
    public void populate(Game game){
        super.populate(game);
        getJam().destroy();
        getBread().destroy();
        getFork().destroy();

        // create player
        getToast().setPosition(new Vec2(-20,-15));
        getToast().addCollisionListener(new Pickup(getToast()));

        // create opponent
        getBoy().setPosition(new Vec2(12,-13));
        getBoy().addCollisionListener(new BoyCollision(getBoy()));

        // create key
        getKey().setPosition(new Vec2(20,0));
    }

    @Override
    public boolean isComplete() {
        if (getToast().getKey()){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public String getLevelName() {
        return "Level3";
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Spawn spoon every 2 seconds
        Random random = new Random();
        int speed = random.nextInt(10);
        Spoon spoon = new Spoon(this);
        // Throw spoon
        spoon.setLinearVelocity(new Vec2(-speed,speed));
        Vec2 boyPosition = new Vec2(getBoy().getPosition());
        // If boy is not in world, destroy spoon to stop spawning spoons
        if (getBoy().get_isDestroyed()){
            spoon.destroy();
        }
        spoon.setPosition(new Vec2(boyPosition.x - 5, boyPosition.y + 5));
        spoon.addCollisionListener(new WallCollision());
        spoon.addCollisionListener(new ToastCollision(this.getToast()));
    }
}