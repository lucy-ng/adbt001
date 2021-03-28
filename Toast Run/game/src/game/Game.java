package game;
import city.cs.engine.*;
import game.Collisions.*;
import game.Controllers.*;
import game.Levels.*;
import org.jbox2d.common.Vec2;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import javax.sound.sampled.*;
import javax.swing.event.*;

/**
 * Game Class
 * @author Lucy Nguyen
 */
public class Game {
    /**
     * GameLevel class
     */
    // create world
    private GameLevel level;

    /**
     * GameView class
     */
    // create game view
    private final GameView view;

    /**
     * ToastController class
     */
    // creates controller class to move toast
    private final ToastController toastController;

    /**
     * Background music
     */
    // create music
    private SoundClip gameMusic;

    /**
     * Player's name
     */
    // player name
    private String name;

    /**
     * Returns true or false if name from file
     */
    // return true or false if name from file
    private boolean nameFromFile;

    /**
     * Returns true or false if file is empty
     */
    // return true or false if file is empty
    private boolean fileNull;

    /**
     * Creates game
     */
    public Game(){
        // Frame
        final JFrame frame = new JFrame("Toast Run");
        frame.setLayout(new BorderLayout());

        // Create level 1 and populate
        level = new Level1();
        level.populate(this);
        // Make view
        view = new GameView(this, level,800,500);
        view.setZoom(16);
        // Background music for level 1
        try {
        /*
        https://freesound.org/people/Slaking_97/sounds/459706/
         */
            gameMusic = new SoundClip("data/level1music.wav");
            System.out.println("Loading level 1 music");
            gameMusic.loop();
        }

        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        // Senses keyboard inputs to move toast
        toastController = new ToastController(level.getToast());
        // Add keyboard inputs of player
        view.addKeyListener(toastController);

        // Title screen
        JPanel titleScreen = new JPanel();
        titleScreen.setPreferredSize(new Dimension(800,500));
        titleScreen.setLayout(null);
        frame.add(titleScreen, BorderLayout.CENTER);
        // Toast Run title
        JLabel toastRun = new JLabel("<html><div style='text-align: center;'>" + "Toast Run" + "</div></html>");
        toastRun.setBounds(320, 50, 200, 50);
        toastRun.setFont(new Font("Arial", Font.BOLD, 30));
        titleScreen.add(toastRun);
        // Enter name label
        JLabel nameLabel = new JLabel("<html><div style='text-align: center;'>" + "Enter your username:" + "</div></html>");
        nameLabel.setBounds(320, 150, 300, 50);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleScreen.add(nameLabel);
        // Input name box
        JTextField inputName = new JTextField();
        inputName.setBounds(240,200,300,50);
        inputName.setSize(300,30);
        titleScreen.add(inputName);

        // Start button
        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            name = inputName.getText();
            // If name is empty, display message to enter username
            if (name.equals("")){
                nameLabel.setBounds(320, 150, 300, 50);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                nameLabel.setText("Enter your username:");
                titleScreen.requestFocus();
            }
            else {
                readFile();
                // If username in file, display message to load game
                if (isNameFromFile() && !isFileNull()){
                    nameLabel.setBounds(300, 150, 600, 50);
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    nameLabel.setText("Click load to load the game.");
                    titleScreen.requestFocus();
                }
                else if (!isFileNull() && !isNameFromFile()){
                    nameLabel.setBounds(300, 150, 600, 50);
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    nameLabel.setText("Wrong username. Try again.");
                    titleScreen.requestFocus();
                }
                else {
                    // If username not in file, start new game
                    titleScreen.setVisible(false);
                    // switch to game
                    view.setVisible(true);
                    frame.getContentPane().add(view, BorderLayout.CENTER);
                    level.getToast().setPlayerName(name);
                    view.requestFocus();
                }
            }
        });
        titleScreen.add(start);
        start.setBounds(330,300,100,50);

        // Load
        JButton loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            name = inputName.getText();
            // If name is empty, display message to enter username
            if (name.equals("")) {
                nameLabel.setBounds(320, 150, 300, 50);
                nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                nameLabel.setText("Enter your username:");
                titleScreen.requestFocus();
            }
            else {
                readFile();
                // If username not in file, display error message
                if (!isNameFromFile() && !isFileNull()){
                    nameLabel.setBounds(300, 150, 600, 50);
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    nameLabel.setText("Wrong username. Try again.");
                    titleScreen.requestFocus();
                }
                else if (isFileNull() && !isNameFromFile()){
                    nameLabel.setBounds(300, 150, 600, 50);
                    nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
                    nameLabel.setText("Click start to start the game.");
                    titleScreen.requestFocus();
                }
                // If username in file, load game
                else {
                    titleScreen.setVisible(false);
                    // switch to game
                    view.setVisible(true);
                    frame.getContentPane().add(view, BorderLayout.CENTER);
                    // call method loadGame to load level from text file
                    loadGame();
                    view.requestFocus();
                }
            }
        });
        titleScreen.add(loadButton);
        loadButton.setBounds(330, 400, 100, 50);

        // Make menu and add components
        JMenuBar menu = new JMenuBar();
        menu.setPreferredSize(new Dimension(500,50));
        // Save
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            readFile();
            // save level to text file
            try{
                if (!isNameFromFile()){
                    GameSaverLoader.save("save.txt", level, name);
                }
                else if (isNameFromFile()){
                    GameSaverLoader.overwrite("save.txt", level, name);
                }
            } catch (IOException ioException){
                ioException.printStackTrace();
            }
            view.requestFocus();
        });
        menu.add(saveButton);
        // Pause
        JButton pause = new JButton("Pause");
        pause.addActionListener(e -> {
            // Pause level and pause music
            level.stop();
            gameMusic.pause();
            view.requestFocus();
        });
        menu.add(pause);
        // Resume
        JButton resume = new JButton("Resume");
        resume.addActionListener(e -> {
            // Resume level and resume music
            level.start();
            gameMusic.resume();
            view.requestFocus();
        });
        menu.add(resume);
        // Restart
        JButton restart = new JButton("Restart");
        restart.addActionListener(e -> {
            // Call restartLevel to restart level
            restartLevel();
            view.requestFocus();
        });
        menu.add(restart);
        // Quit
        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> System.exit(0));
        menu.add(quit);

        // Volume
        double volumeMin = 0;
        double volumeNormal = 2;
        double volumeMax = 3;
        // Slider for volume
        JSlider volume = new JSlider(0,3,2);
        Hashtable label = new Hashtable();
        // Add labels for volume
        label.put(((int) volumeMin), new JLabel("Mute Music"));
        label.put(1, new JLabel("1"));
        label.put(((int) volumeNormal), new JLabel("2"));
        label.put(((int) volumeMax), new JLabel("3"));
        volume.setLabelTable(label);
        // Distance between labels is 1
        volume.setMinorTickSpacing(1);
        volume.setPaintTicks(true);
        volume.setPaintLabels(true);
        volume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (volume.getValue() == 0){
                    // No music
                    gameMusic.pause();
                }
                else if (volume.getValue() == 1){
                    // 0.5x sound
                    gameMusic.setVolume(0.5);
                    gameMusic.resume();
                }
                else if (volume.getValue() == 2){
                    // 1x sound
                    gameMusic.setVolume(1);
                    gameMusic.resume();
                }
                else if (volume.getValue() == 3){
                    // 2x sound
                    gameMusic.setVolume(2);
                    gameMusic.resume();
                }
                view.requestFocus();
            }
        });
        menu.add(volume);

        // Add menu to frame
        frame.setJMenuBar(menu);

        // Quit window when x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // Frame cannot be resized
        frame.setResizable(false);
        // Size of frame to fit world view
        frame.pack();
        view.requestFocus();
        // Make frame visible
        frame.setVisible(true);
        // Debug view
        //JFrame debugView = new DebugViewer(level, 800, 500);

        // Starts level
        level.start();
    }

    /**
     * Loads up level from file
     * @param level Gets level name from file
     */
    public void setLevel(GameLevel level){
        this.level.stop();
        this.level = level;

        // loads Level 1
        if (level instanceof Level1){
            try {
            /*
            https://freesound.org/people/Slaking_97/sounds/459706/
             */
                gameMusic.stop();
                gameMusic = new SoundClip("data/level1music.wav");
                System.out.println("Loading level 1 music");
                gameMusic.loop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.setImage(new ImageIcon("data/kitchen.png").getImage());
            view.removeMouseListener(new ThrowBread(level, view));
            level.getToast().addCollisionListener(new ToastCollision(level.getToast()));
        }
        // loads Level 2
        else if (level instanceof Level2){
            try {
                /*
                https://freesound.org/people/dominictreis/sounds/514988/
                 */
                gameMusic.stop();
                gameMusic = new SoundClip("data/level2music.wav");
                System.out.println("Loading level 2 music");
                gameMusic.loop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.setImage(new ImageIcon("data/house.png").getImage());
            for (int i = 0; i < 4; i++){
                Random random = new Random();
                int position = random.nextInt(15);
                level.getFork().setPosition(new Vec2(position,position));
                level.getFork().addCollisionListener(new WallCollision());
                level.getFork().addCollisionListener(new ToastCollision(level.getToast()));
                level.getFork().addDestructionListener(new SpawnFork(level));
            }
            level.getBread().addCollisionListener(new ToastCollision(level.getToast()));
        }
        // loads Level 3
        else if (level instanceof Level3){
            try {
                /*
                https://freesound.org/people/Sunsai/sounds/415804/
                 */
                gameMusic.stop();
                gameMusic = new SoundClip("data/level3music.wav");
                System.out.println("Loading level 3 music");
                gameMusic.loop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            view.setImage(new ImageIcon("data/outside.png").getImage());
            view.addMouseListener(new ThrowBread(level, view));
            level.getBoy().addCollisionListener(new BoyCollision(level.getBoy()));
        }

        view.setWorld(this.level);
        view.setZoom(16);
        toastController.updateToast(this.level.getToast());
        this.level.start();

        // If toast has bread, add bread weapon
        if (this.level.getToast().getBread()){
            view.addMouseListener(new ThrowBread(this.level, view));
        }
    }

    /**
     * Restarts current level
     */
    // Restart level
    public void restartLevel(){
        level.stop();
        // Set initial hearts and score
        int hearts = 3;
        int score = 0;

        // Change current level to level 1
        level = new Level1();
        level.populate(this);
        try {
        /*
        https://freesound.org/people/Slaking_97/sounds/459706/
         */
            gameMusic.stop();
            gameMusic = new SoundClip("data/level1music.wav");
            System.out.println("Loading level 1 music");
            gameMusic.loop();
        }
        catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }

        // Display lives and score
        level.getToast().transferHearts(hearts);
        level.getToast().transferScore(score);
        view.setWorld(level);
        view.setZoom(16);
        view.setImage(new ImageIcon("data/kitchen.png").getImage());
        view.removeMouseListener(new ThrowBread(level, view));
        toastController.updateToast(level.getToast());
        level.start();
    }

    /**
     * Go to next level if player has key
     */
    // Go to next level
    public void goToNextLevel(){
        // Display lives and score
        int hearts = level.getToast().getHearts();
        int score = level.getToast().getScore();

        // If level 1, go to level 2
        if (level instanceof Level1 && level.isComplete()) {
            level.stop();

            // Level 2 world
            level = new Level2();
            level.populate(this);
            view.setWorld(level);
            view.setZoom(16);
            view.setImage(new ImageIcon("data/house.png").getImage());

            level.getToast().transferHearts(hearts);
            level.getToast().transferScore(score);

            // Level 2 Background music
            try {
                /*
                https://freesound.org/people/dominictreis/sounds/514988/
                 */
                gameMusic.stop();
                gameMusic = new SoundClip("data/level2music.wav");
                System.out.println("Loading level 2 music");
                gameMusic.loop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }

            toastController.updateToast(level.getToast());
            level.start();

            if (level.getToast().getBread()){
                view.addMouseListener(new ThrowBread(level, view));
            }
        }
        // If level2, go to level 3
        else if (level instanceof Level2 && level.isComplete()){
            level.stop();

            // Level 2 world
            level = new Level3(this);
            level.populate(this);
            view.setWorld(level);
            view.setZoom(16);
            view.setImage(new ImageIcon("data/outside.png").getImage());

            level.getToast().transferHearts(hearts);
            level.getToast().transferScore(score);

            // Level 3 Background music
            try {
                /*
                https://freesound.org/people/Sunsai/sounds/415804/
                 */
                gameMusic.stop();
                gameMusic = new SoundClip("data/level3music.wav");
                System.out.println("Loading level 3 music");
                gameMusic.loop();
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }

            // Player can throw bread
            view.addMouseListener(new ThrowBread(level, view));
            toastController.updateToast(level.getToast());
            level.start();
        }
        // If level 3 is complete, end game
        else if (level instanceof Level3 && level.isComplete()){
            // https://freesound.org/people/maxmakessounds/sounds/353546/
            try{
                gameMusic.stop();
                gameMusic = new SoundClip("data/endgamesound.wav");
            }
            catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
            level.stop();

            // Empty file when player has finished game
            try {
                GameSaverLoader.empty("save.txt");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    /**
     * Load game from file
     */
    // Load game from text file and set level from call to function setLevel
    public void loadGame(){
        try {
            GameLevel level = GameSaverLoader.load("save.txt",this, name);
            this.setLevel(level);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Read file to find if name is in file
     */
    // Read text file and find if username is in file
    public void readFile(){
        try {
            GameSaverLoader.read("save.txt", this, name);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    /**
     * Return true or false if file is empty
     * @return Returns boolean value of fileNull
     */
    public boolean isFileNull() {
        return fileNull;
    }

    /**
     * Return true or false if name is in file
     * @return Returns boolean value of nameFromFile
     */
    public boolean isNameFromFile() {
        return nameFromFile;
    }

    /**
     * Sets true or false if name is in file
     * @param nameFromFile Set boolean value of nameFromFile
     */
    public void setNameFromFile(boolean nameFromFile) {
        this.nameFromFile = nameFromFile;
    }

    /**
     * Sets true or false if file is empty
     * @param fileNull Set boolean value of fileNull
     */
    public void setFileNull(boolean fileNull) {
        this.fileNull = fileNull;
    }

    /**
     * Return lives from player in level
     * @return Return player's lives
     */
    // Get lives from player in level
    public int getHearts() { return level.getToast().getHearts(); }

    /**
     * Return lives from boy in level
     * @return Return boy's lives
     */
    // Get lives from boy in level
    public int getBoyHearts() { return level.getBoy().getHearts(); }

    /**
     * Return score from level
     * @return Return player's score
     */
    // Get score from player in level
    public int getScore() { return level.getToast().getScore(); }

    /**
     * Return true or false if player has key in level
     * @return Returns boolean value of haveKey
     */
    // Return if pickup key is true from player in level
    public boolean getKey(){ return level.getToast().getKey(); }

    /**
     * Return level
     * @return Return level
     */
    // Get level
    public GameLevel getLevel(){return this.level;}

    /**
     * Return background music
     * @return Return background music
     */
    // Get music
    public SoundClip getGameMusic() { return this.gameMusic; }

    /**
     * Run game
     * @param args Game is created
     */
    // Run game
    public static void main(String[] args){
        new Game();
    }
}