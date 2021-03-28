package game.Bodies;
import city.cs.engine.*;

public class Door extends StaticBody{
    private static final Shape doorShape = new PolygonShape(
            1.05f,2.45f, 1.48f,2.08f, 1.36f,-2.39f, -1.33f,-2.43f, -1.52f,2.07f, -0.94f,2.46f
    );

    private static final BodyImage doorImage = new BodyImage("data/door.png", 5f);

    // Door
    public Door(World world) {
        super(world, doorShape);
        addImage(doorImage);
    }
}
