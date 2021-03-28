package game.Levels;
import city.cs.engine.*;
import city.cs.engine.Shape;
import game.Bodies.*;
import game.Collisions.*;
import game.Game;
import org.jbox2d.common.Vec2;
import java.awt.*;

public abstract class GameLevel extends World {
    private Toast toast;
    private final Door door;
    private Key key;
    private final Wall groundBottom;
    private final Wall groundTop;
    private Boy boy;
    private Jam jam;
    private Fork fork;
    private Bread bread;

    public GameLevel(){
        // Colour of platform
        Color red = new Color(255,46,81);
        door = new Door(this);

        // create platforms
        groundBottom = new Wall(this);
        groundTop = new Wall(this);

        // make left and right borders
        Shape borderShape = new BoxShape(0.5f,25);
        Body leftBorder = new StaticBody(this, borderShape);
        leftBorder.setFillColor(red);
        leftBorder.setPosition(new Vec2(-25,0));
        Body rightBorder = new StaticBody(this, borderShape);
        rightBorder.setFillColor(red);
        rightBorder.setPosition(new Vec2(25,0));
    }

    // Return objects
    public Toast getToast() {
        return toast;
    }
    public Door getDoor(){
        return door;
    }
    public Key getKey(){
        return key;
    }
    public Wall getGroundBottom() { return groundBottom; }
    public Wall getGroundTop() { return groundTop; }
    public Boy getBoy() { return boy;}
    public Jam getJam() { return jam;}
    public Fork getFork() { return fork; }
    public Bread getBread() { return bread; }

    // set objects to load game
    public void setToast(Toast t) { toast = t; }
    public void setKey(Key k){ key = k; }
    public void setBoy(Boy b) { boy = b; }
    public void setJam(Jam j) { jam = j; }
    public void setFork(Fork f) { fork = f; }
    public void setBread(Bread b) { bread = b; }

    // Return level name
    public abstract String getLevelName();
    // Return if level is complete
    public abstract boolean isComplete();

    // Populate with objects
    public void populate(Game game){
        toast = new Toast(this);
        key = new Key(this);
        boy = new Boy(this);
        jam = new Jam(this);
        fork = new Fork(this);
        bread = new Bread(this);

        // Add collision listeners
        DoorCollision doorCollision = new DoorCollision(this, game);
        toast.addCollisionListener(doorCollision);
        toast.setGravityScale(5);
    }
}