package game.Bodies;
import city.cs.engine.*;
import game.Levels.GameLevel;
import org.jbox2d.common.Vec2;

/**
 * Boy Class extends Walker Class and implements StepListener
 * @author Lucy Nguyen
 */
public class Boy extends Walker implements StepListener {
    /**
     * Shape of boy
     */
    private static final Shape boyShape = new PolygonShape(
            0.21f,2.51f, 1.39f,0.5f, 1.18f,-2.83f, -0.21f,-2.79f, -1.7f,0.77f, -0.57f,2.43f
    );

    /**
     * Image of boy walking right
     */
    private static final BodyImage boyRightImage = new BodyImage("data/boyright.gif",7.5f);

    /**
     * Image of boy walking left
     */
    private static final BodyImage boyLeftImage = new BodyImage("data/boyleft.gif",7.5f);

    /**
     * Boy's lives
     */
    // Lives
    private int hearts;

    /**
     * Boolean value if boy is destroyed
     */
    // if boy is destroyed
    private boolean isDestroyed;

    /**
     * States of boy
     */
    // State of boy: walking left, walking right, or standing still
    private enum State{
        walk_left,
        walk_right,
        stand_still
    }

    /**
     * Boy's state
     */
    private State state;

    /**
     * Distance between boy and player
     */
    // Distance between boy and point
    public static float range = 20;

    /**
     * GameLevel class
     */
    private final GameLevel level;

    /**
     * Boy constructor
     * @param level Adds boy to level
     */
    // Boy has 3 lives and stands still
    public Boy(GameLevel level) {
        super(level, boyShape);
        this.level = level;
        addImage(boyLeftImage);
        hearts = 3;
        isDestroyed = false;
        state = State.stand_still;
        getWorld().addStepListener(this);
    }

    /**
     * Set boolean value for isDestroyed
     */
    // Set and get isDestroyed
    public void set_isDestroyed(){
        isDestroyed = true;
    }

    /**
     * Return true or false if boy is destroyed
     * @return Get boolean value for isDestroyed
     */
    public boolean get_isDestroyed(){
        return isDestroyed;
    }

    /**
     * Return lives for boy
     * @return Return lives for boy
     */
    // Set and get lives
    public int getHearts() {
        return hearts;
    }

    /**
     * Decrement lives of boy if boy collides with bread and destroy if lives = 0
     */
    public void setHearts(){
        hearts--;
        // Destroy boy if lives = 0
        if (hearts == 0) {
            this.destroy();
            set_isDestroyed();
        }
    }

    /**
     * Set lives of boy when game is loaded
     * @param number Set initial lives of boy
     */
    // Set hearts when loading game
    public void setInitialHearts(int number){
        hearts = number;
    }

    /**
     * Set boy walking left image
     */
    // Set walking left and walking right images
    public void setBoyRightImage(){
        removeAllImages();
        addImage(boyRightImage);
    }

    /**
     * Set boy walking right image
     */
    public void setBoyLeftImage(){
        removeAllImages();
        addImage(boyLeftImage);
    }

    /**
     * Calculates range between boy and player on left side
     * @return Returns range between boy and player on left side
     */
    // Calculating range between boy and player
    public boolean inRangeLeft(){
        Body player = level.getToast();
        float distance = getPosition().x - player.getPosition().x;
        return distance > 0 && distance < range;
    }

    /**
     * Calculates range between boy and player on right side
     * @return Returns range between boy and player on right side
     */
    public boolean inRangeRight(){
        Body player = level.getToast();
        float distance = getPosition().x - player.getPosition().x;
        return distance < 0 && distance > -range;
    }

    /**
     * Boy walks left or right depending on player position
     * @param stepEvent
     */
    @Override
    public void preStep(StepEvent stepEvent) {
        // Walk left if player is on left side of boy
        if (inRangeLeft()){
            if (state != State.walk_left){
                state = State.walk_left;
            }
        }
        // Walk right if player is on right side of boy
        else if (inRangeRight()){
            if (state != State.walk_right){
                state = State.walk_right;
            }
        }
        // Stand still if not in range
        else {
            if (state != State.stand_still){
                state = State.stand_still;
                setLinearVelocity(new Vec2(0,0));
                this.stopWalking();
            }
        }
        // change state
        refreshState();
    }

    /**
     * Refreshes boy's state
     */
    private void refreshState(){
        switch (state) {
            // Walk left
            case walk_left -> {
                this.setBoyLeftImage();
                this.startWalking(-5);
            }
            // Walk right
            case walk_right -> {
                this.setBoyRightImage();
                this.startWalking(5);
            }
            default -> this.stopWalking();
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) { }
}
