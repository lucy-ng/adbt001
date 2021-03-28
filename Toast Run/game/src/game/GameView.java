package game;
import city.cs.engine.*;
import game.Levels.Level3;

import java.awt.*;
import javax.swing.*;

public class GameView extends UserView {
    private final Game game;
    private Image image;
    private final Image keyIcon;
    private final Image heartIcon;

    // Set up view with background image and key image
    public GameView(Game game, World world, int width, int height) {
        super(world, width, height);
        image = new ImageIcon("data/kitchen.png").getImage();
        keyIcon = new ImageIcon("data/keyicon.png").getImage();
        heartIcon = new ImageIcon("data/heart.png").getImage();
        this.game = game;
    }

    // Set up background image for level
    public void setImage(Image image) {
        this.image = image;
    }
    @Override
    protected void paintBackground(Graphics2D background){
        background.drawImage(image, 0, 0, this);
    }

    // Display
    @Override
    protected void paintForeground(Graphics2D g) {
        displayPlayerHearts(g);
        displayGameOverMessage(g);
        displayGameEndMessage(g);
        displayBoyHearts(g);
        displayScore(g);
        displayKey(g);
    }

    public void displayPlayerHearts(Graphics2D g){
        // Display number of lives
        int numHearts = game.getHearts();
        for (int i = 1; i < numHearts + 1; i++){
            g.drawImage(heartIcon, 50*i, 50, 50, 50,this);
        }
    }

    public void displayGameOverMessage(Graphics2D g){
        int numHearts = game.getHearts();
        // game over message
        if (numHearts == 0){
            game.getGameMusic().stop();
            g.drawString("Game Over!", 400, 275);
            g.drawString("Click restart to start again.", 370, 325);
        }
    }

    public void displayGameEndMessage(Graphics2D g){
        // game end message
        if (game.getLevel() instanceof Level3 && game.getLevel().isComplete() && !game.getLevel().isRunning()){
            g.drawString("Finish!", 400, 275);
        }
    }

    public void displayBoyHearts(Graphics2D g){
        // Display number of lives of boy
        if (game.getLevel() instanceof Level3){
            int numBoyHearts = game.getBoyHearts();
            for (int x = 1; x < numBoyHearts + 1; x++){
                g.drawImage(heartIcon, (50*x)+500, 50, 50, 50,this);
            }
        }
    }

    public void displayScore(Graphics2D g){
        // Display score
        g.drawString("Score: " + game.getScore(), 400, 15);
    }

    public void displayKey(Graphics2D g){
        // Display if player has key
        if (game.getKey()){
            g.drawImage(keyIcon, 50,130, this);
        }
    }
}