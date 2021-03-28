package game.Bodies;
import city.cs.engine.*;

public class Key extends StaticBody {
    private static final Shape keyShape = new PolygonShape(
            -0.892f,0.975f, 2.007f,0.133f, -1.024f,-0.974f, -2.023f,0.001f, -1.66f,0.827f
    );

    private static final BodyImage keyImage = new BodyImage("data/key.png", 2f);

    // Key pickup
    public Key(World world) {
        super(world, keyShape);
        addImage(keyImage);
    }
}
