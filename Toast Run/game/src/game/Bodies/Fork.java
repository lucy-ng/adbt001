package game.Bodies;
import city.cs.engine.*;

public class Fork extends DynamicBody {
    private static final Shape forkShape = new PolygonShape(
            0.34f,2.48f,
            0.39f,-2.42f,
            -0.39f,-2.41f,
            -0.36f,2.5f,
            0.26f,2.49f);

    private static final BodyImage forkImage = new BodyImage("data/fork.png", 5f);

    // Fork obstacle
    public Fork(World world) {
        super(world, forkShape);
        addImage(forkImage);
    }
}
