package game.Bodies;
import city.cs.engine.*;
import javax.sound.sampled.*;
import java.io.IOException;

/**
 * Toast Class extends Walker Class
 * @author Lucy Nguyen
 */
public class Toast extends Walker {
    /**
     * Toast shape
     */
    private static final Shape toastShape = new PolygonShape(
            0.07f,1.86f,
            1.4f,0.82f,
            1.04f,-1.36f,
            -1.4f,-1.41f,
            -1.48f,1.17f,
            -0.06f,1.87f);

    /**
     * Toast walking left image
     */
    private static final BodyImage toastLeftImage = new BodyImage("data/toastleft.gif", 5f);

    /**
     * Toast walking right image
     */
    private static final BodyImage toastRightImage = new BodyImage("data/toastright.gif",5f);

    /**
     * Player's lives
     */
    // Lives
    private int hearts;

    /**
     * Boolean value if player has key
     */
    // Key
    private boolean haveKey;

    /**
     * Boolean value if player picks up bread
     */
    // Bread
    private boolean haveBread;

    /**
     * Player's score
     */
    // Score
    private int score;

    /**
     * Player's name
     */
    // Player name
    private String playerName;

    /**
     * Toast constructor
     * @param world Add toast to world
     */
    // Toast constructor
    public Toast(World world) {
        super(world, toastShape);
        addImage(toastRightImage);
        hearts = 3;
        haveKey = false;
        haveBread = false;
        score = 0;
    }

    /**
     * Set gravity acceleration for jumping
     * @param g Gravity value
     */
    @Override
    public void setGravityScale(float g) {
        super.setGravityScale(g);
    }

    /**
     * Return's player score
     * @return Return's player score
     */
    // return score
    public int getScore(){
      return this.score;
    }

    /**
     * Transfer player's score from level
     * @param number Score from previous level
     */
    // transfer score
    public void transferScore(int number){this.score = this.score + number; }

    /**
     * Set player's score
     * @param number Score from level
     */
    // set score
    public void setScore(int number) { this.score = number; }

    /**
     * Return player's lives
     * @return Return player's lives
     */
    // Returns number of lives
    public int getHearts() {
        return this.hearts;
    }

    /**
     * Transfer player's lives from level
     * @param number Lives from previous level
     */
    // transfer lives
    public void transferHearts(int number){
        this.hearts = this.hearts + number;
        this.hearts = this.hearts - 3;
    }

    /**
     * Set number of lives for player
     * @param number Set number of lives for player
     */
    // set number of lives
    public void setHearts(int number){
        this.hearts = number;
    }

    /**
     * Add to lives and score when jam is picked up
     */
    // Increment lives when picking up jam
    public void gainHearts(){
        this.hearts++;
        this.score = this.score + 100;
        System.out.println("Picked jam up!");
        System.out.println("Lives:" + this.hearts);
    }

    /**
     * Lose life and decrease score when player collides with obstacles
     */
    // Decrement lives when colliding with obstacles
    public void loseHearts(){
        this.hearts--;
        if (this.score != 0){
            this.score = this.score - 50;
        }
        System.out.println("I lost a life!");
        System.out.println("Lives:" + this.hearts);
        // If lives = 0, destroy player
        if (this.hearts == 0){
            this.destroy();
            // Game over sound
            try {
            /*
            https://freesound.org/people/jivatma07/sounds/173859/
            */
                SoundClip loseSound = new SoundClip("data/gameover.wav");
                loseSound.play();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * Return true or false if toast has key
     * @return Return boolean value if toast has key
     */
    // Returns true or false if toast has key
    public boolean getKey(){
        return this.haveKey;
    }

    /**
     * Set haveKey to true if player picks up key
     */
    // Make haveKey true
    public void keyTrue(){ this.haveKey = true;}

    /**
     * If haveKey is true, add to score
     */
    // Picks up key
    public void pickupKey(){
        this.haveKey = true;
        this.score = this.score + 500;
        System.out.println("I picked up a key!");
    }

    /**
     * Set haveBread to true
     */
    // set bread pickup to true
    public void setBread(){
        this.haveBread = true;
    }

    /**
     * Return true or false if player picks up bread
     * @return Return boolean value if player picks up bread
     */
    // return if player picks up bread for throwing bread
    public boolean getBread(){ return this.haveBread; }

    /**
     * Set walking left image
     */
    // Set image for toast walking left
    public void setToastLeftImage(){
        removeAllImages();
        addImage(toastLeftImage);
    }

    /**
     * Set walking right image
     */
    // Set image for toast walking right
    public void setToastRightImage(){
        removeAllImages();
        addImage(toastRightImage);
    }

    /**
     * Set player's name
     * @param playerName Player's name is set for toast
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }
}
