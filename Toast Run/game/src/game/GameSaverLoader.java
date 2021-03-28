package game;
import game.Bodies.*;
import game.Collisions.*;
import game.Levels.*;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.io.*;

public class GameSaverLoader {
    // read name from file
    public static void read(String fileName, Game game, String playerName)
        throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();

            if (line != null){
                String[] tokens = line.split(",");
                String name = tokens[0];
                String levelName = tokens[1];
                int score = Integer.parseInt(tokens[2]);
                int lives = Integer.parseInt(tokens[3]);
                boolean key = Boolean.parseBoolean(tokens[4]);
                System.out.println("Name: " + name + ", Level: " + levelName + ", Score: " + score + ", Lives: " + lives
                        + ", Key: " + key);
                // name in file and file is not empty
                if (name.matches(playerName)){
                    game.setNameFromFile(true);
                    game.setFileNull(false);
                }
                // name not in file and file is not empty
                else if (!name.matches(playerName)){
                    game.setNameFromFile(false);
                    game.setFileNull(false);
                }
            }
            else {
                game.setNameFromFile(false);
                game.setFileNull(true);
            }
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }

    // Save game to text file
    public static void save(String fileName, GameLevel level, String playerName)
            throws IOException {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            // Write player name, level name, player score and player lives and if player has key
            writer.write(playerName + "," + level.getLevelName() + "," + level.getToast().getScore() + "," +
                    level.getToast().getHearts() + "," + level.getToast().getKey() + "\n");

            for (StaticBody body : level.getStaticBodies()) {
                if (body instanceof Key) {
                    writer.write("Key," + body.getPosition().x + "," + body.getPosition().y + "\n");
                } else if (body instanceof Jam) {
                    writer.write("Jam," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }
            }

            for (DynamicBody body : level.getDynamicBodies()) {
                if (body instanceof Toast) {
                    writer.write("Toast," + body.getPosition().x + "," + body.getPosition().y + "\n");
                } else if (body instanceof Fork) {
                    writer.write("Fork," + body.getPosition().x + "," + body.getPosition().y + "\n");
                } else if (body instanceof Boy) {
                    writer.write("Boy," + body.getPosition().x + "," + body.getPosition().y + "," + ((Boy) body).getHearts() + "\n");
                } else if (body instanceof Bread) {
                    writer.write("Bread," + body.getPosition().x + "," + body.getPosition().y + "\n");
                }
            }
        }
    }

    public static void empty(String fileName)
        throws IOException {
        StringBuilder buffer = new StringBuilder();

        FileReader fr = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fr);
        String line;

        while (reader.readLine() != null){
            line = "";
            buffer.append(line);
        }
        fr.close();

        String replaceLine = buffer.toString();
        // replace line
        FileOutputStream fileOutputStream = new FileOutputStream("save.txt");
        fileOutputStream.write(replaceLine.getBytes());
        fileOutputStream.close();
    }

    // overwrite to file
    public static void overwrite(String fileName, GameLevel level, String playerName)
        throws IOException {
        StringBuilder stringBuffer = new StringBuilder();

        FileReader fr = new FileReader(fileName);
        BufferedReader reader = new BufferedReader(fr);

        // Positions of bodies
        String keyPosition = "", jamPosition = "", toastPosition = "", forkPosition = "",
                boyPosition = "", breadPosition = "";

        for (StaticBody body : level.getStaticBodies()){
            if (body instanceof Key){
                keyPosition = "Key," + body.getPosition().x + "," + body.getPosition().y + "\n";
            }
            else if (body instanceof Jam){
                jamPosition = "Jam," + body.getPosition().x + "," + body.getPosition().y + "\n";
            }
        }

        for (DynamicBody body: level.getDynamicBodies()){
            if (body instanceof Toast){
                toastPosition = "Toast," + body.getPosition().x + "," + body.getPosition().y + "\n";
            }
            else if (body instanceof Fork){
                forkPosition = "Fork," + body.getPosition().x + "," + body.getPosition().y + "\n";
            }
            else if (body instanceof Boy){
                boyPosition = "Boy," + body.getPosition().x + "," + body.getPosition().y + "," + ((Boy) body).getHearts() + "\n";
            }
            else if (body instanceof Bread){
                breadPosition = "Bread," + body.getPosition().x + "," + body.getPosition().y + "\n";
            }
        }

        // the line that is going to replace existing line
        while (reader.readLine() != null){
            String line = playerName + "," + level.getLevelName() + "," + level.getToast().getScore() + "," +
                    level.getToast().getHearts() + "," + level.getToast().getKey() + "\n";
            if (stringBuffer.length() == 0){
                stringBuffer.append(line);
            }
        }
        String positions = keyPosition + jamPosition + toastPosition + forkPosition + boyPosition + breadPosition + "\n";
        stringBuffer.append(positions);
        fr.close();

        String replaceLine = stringBuffer.toString();
        // replace line
        FileOutputStream fileOutputStream = new FileOutputStream("save.txt");
        fileOutputStream.write(replaceLine.getBytes());
        fileOutputStream.close();
    }

    // Loads game from text file
    public static GameLevel load(String fileName, Game game, String playerName)
        throws IOException {
        FileReader fr = null;
        BufferedReader reader = null;
        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);
            String line = reader.readLine();

            GameLevel level = null;

            while (line != null){
                String[] tokens = line.split(",");
                String name = tokens[0];
                String levelName = tokens[1];
                int score = Integer.parseInt(tokens[2]);
                int lives = Integer.parseInt(tokens[3]);
                boolean key = Boolean.parseBoolean(tokens[4]);
                System.out.println("Name: " + name + ", Level: " + levelName + ", Score: " + score + ", Lives: " + lives
                        + ", Key: " + key);

                if (playerName.matches(name)) {
                    // Returns level from level name in text file
                    switch (levelName) {
                        case "Level1" -> level = new Level1();
                        case "Level2" -> level = new Level2();
                        case "Level3" -> level = new Level3(game);
                    }
                    line = reader.readLine();
                    while (line != null) {
                        String[] t = line.split(",");

                        switch (t[0]) {
                            case "Key" -> {
                                Key k = new Key(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                k.setPosition(new Vec2(x, y));
                                level.setKey(k);
                            }
                            case "Jam" -> {
                                Jam j = new Jam(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                level.setJam(j);
                                j.setPosition(new Vec2(x, y));
                            }
                            case "Toast" -> {
                                Toast toast = new Toast(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                toast.setPosition(new Vec2(x, y));
                                level.setToast(toast);
                                // Add collision listeners
                                DoorCollision doorCollision = new DoorCollision(level, game);
                                toast.addCollisionListener(doorCollision);
                                toast.setGravityScale(5);
                                toast.addCollisionListener(new Pickup(toast));
                            }
                            case "Fork" -> {
                                Fork f = new Fork(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                f.setPosition(new Vec2(x, y));
                                level.setFork(f);
                            }
                            case "Boy" -> {
                                Boy b = new Boy(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                b.setPosition(new Vec2(x, y));
                                int hearts = Integer.parseInt(t[3]);
                                b.setInitialHearts(hearts);
                                level.setBoy(b);
                            }
                            case "Bread" -> {
                                Bread bread = new Bread(level);
                                float x = Float.parseFloat(t[1]);
                                float y = Float.parseFloat(t[2]);
                                bread.setPosition(new Vec2(x, y));
                                level.setBread(bread);
                            }
                        }
                        line = reader.readLine();
                    }

                    // Set player's name
                    level.getToast().setPlayerName(name);
                    // Set player's score and lives from text file
                    level.getToast().setScore(score);
                    level.getToast().setHearts(lives);
                    if (key){ level.getToast().keyTrue(); }
                }
            }
            return level;
        }
        finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}
