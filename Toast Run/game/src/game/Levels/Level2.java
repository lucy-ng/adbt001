package game.Levels;
import city.cs.engine.*;
import game.Bodies.*;
import game.Collisions.*;
import game.Game;
import org.jbox2d.common.Vec2;
import java.awt.*;
import java.util.Random;

public class Level2 extends GameLevel{
    public Level2(){
        super();

        Color red = new Color(255,46,81);
        // make bottom border
        getGroundBottom().setFillColor(red);
        getGroundBottom().setPosition(new Vec2(0,-15f));
        // make top border
        getGroundTop().setFillColor(red);
        getGroundTop().setPosition(new Vec2(0,15));

        // make platforms
        Shelf shelf1 = new Shelf(this);
        shelf1.setPosition(new Vec2(-12,5));
        Shelf shelf2 = new Shelf(this);
        shelf2.setPosition(new Vec2(-17,0));
        Shelf shelf3 = new Shelf(this);
        shelf3.setPosition(new Vec2(-12,-5));
        shelf3.rotateDegrees(20);

        // create cloud
        PolygonShape cloudShape = new PolygonShape(1.48f,3.55f, 6.95f,-0.67f, 4.16f,-3.46f, -3.25f,-3.52f, -7.07f,-1.24f, -1.82f,3.21f);
        StaticBody cloud = new StaticBody(this);
        GhostlyFixture cloudFixture = new GhostlyFixture(cloud, cloudShape);
        cloud.addImage(new BodyImage("data/cloud.png", 7.5f));
        cloud.setPosition(new Vec2(5,10));

        // Create door
        getDoor().setPosition(new Vec2(20,-11.5f));
    }

    @Override
    public void populate(Game game){
        super.populate(game);
        getBoy().destroy();

        // Make obstacles
        for (int i = 0; i < 3; i++){
            Random random = new Random();
            int position = random.nextInt(15);
            getFork().setPosition(new Vec2(position,position));
            getFork().addCollisionListener(new WallCollision());
            getFork().addCollisionListener(new ToastCollision(getToast()));
            getFork().addDestructionListener(new SpawnFork(this));
        }

        // create player
        getToast().setPosition(new Vec2(15,-11));
        getToast().setGravityScale(5);
        getToast().addCollisionListener(new Pickup(getToast()));

        // Add bread weapon pickup
        getBread().setPosition(new Vec2(-15,0));
        getBread().addCollisionListener(new ToastCollision(getToast()));

        // Make pickups
        getJam().setPosition(new Vec2(-7,5));
        // create key
        getKey().setPosition(new Vec2(-20,0));
    }

    // Return level 2
    @Override
    public String getLevelName() {
        return "Level2";
    }

    // Return true if player has key
    @Override
    public boolean isComplete() {
        if (getToast().getKey()){
            return true;
        }
        else{
            return false;
        }
    }
}
