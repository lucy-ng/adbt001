package game.Levels;
import city.cs.engine.*;
import game.Bodies.*;
import game.Collisions.*;
import game.Game;
import org.jbox2d.common.Vec2;

import java.awt.*;

public class Level1 extends GameLevel{
    public Level1(){
        super();

        // Make toaster
        PolygonShape toasterBaseShape = new PolygonShape(1.88f,1.01f, 2.3f,0.61f, 2.32f,-1.83f, -2.44f,-1.85f, -2.46f,0.66f, -2.24f,1.01f);
        PolygonShape toasterToastShape = new PolygonShape(0.96f,2.12f, 1.48f,1.56f, 1.38f,1.03f, -1.5f,0.99f, -1.82f,1.56f, -1.47f,2.04f);
        DynamicBody toaster = new DynamicBody(this);
        SolidFixture toasterBase = new SolidFixture(toaster, toasterBaseShape);
        toasterBase.setFriction(5f);
        toasterBase.setRestitution(0);
        SolidFixture toasterToast = new SolidFixture(toaster, toasterToastShape);
        // Bounce
        toasterToast.setRestitution(1.5f);
        toaster.addImage(new BodyImage("data/toaster.png", 5f));
        toaster.setPosition(new Vec2(10,-12));

        Color red = new Color(255,46,81);
        // make bottom border
        getGroundBottom().setFillColor(red);
        getGroundBottom().setPosition(new Vec2(0, -15f));
        // make top border
        getGroundTop().setFillColor(red);
        getGroundTop().setPosition(new Vec2(0,15));

        // Make platforms
        // Bottom shelf
        Shelf shelf1 = new Shelf(this);
        shelf1.setPosition(new Vec2(-13,-8));
        Shelf shelf2 = new Shelf(this);
        shelf2.setPosition(new Vec2(-4,4));
        // Top shelf
        Shelf shelf3 = new Shelf(this);
        shelf3.setPosition(new Vec2(-17,4));
        // Right shelf
        Shelf shelf4 = new Shelf(this);
        shelf4.setPosition(new Vec2(15,-3));

        // Make obstacles
        Knife knife = new Knife(this);
        knife.setPosition(new Vec2(-11,-7));
        //knife.addCollisionListener(new ToastCollision(getToast()));
        Knife knife1 = new Knife(this);
        knife1.setPosition(new Vec2(-20,-7));
        //knife1.addCollisionListener(new ToastCollision(getToast()));
        Knife knife2 = new Knife(this);
        knife2.setPosition(new Vec2(14,-2));
        //knife2.addCollisionListener(new ToastCollision(getToast()));
        for (int i = 0; i < 3; i++){
            Knife knife3 = new Knife(this);
            knife3.setPosition(new Vec2(i*-8, 5));
            //knife3.addCollisionListener(new ToastCollision(getToast()));
        }

        // Create door
        getDoor().setPosition(new Vec2(20,-2));
    }

    @Override
    public void populate(Game game){
        super.populate(game);
        getBoy().destroy();
        getFork().destroy();
        getBread().destroy();

        // create player
        getToast().setPosition(new Vec2(-20,5));
        getToast().addCollisionListener(new Pickup(getToast()));
        getToast().addCollisionListener(new ToastCollision(getToast()));

        // Make pickups
        getJam().setPosition(new Vec2(20,-13));
        // create key
        getKey().setPosition(new Vec2(-15,-8));
    }

    // Return level 1
    @Override
    public String getLevelName() {
        return "Level1";
    }

    // If toast has key, return true
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
