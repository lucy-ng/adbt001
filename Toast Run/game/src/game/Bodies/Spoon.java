package game.Bodies;
import city.cs.engine.*;

public class Spoon extends DynamicBody {
    private static final Shape spoonShape = new PolygonShape(
            0.06f,2.45f, 0.55f,1.12f, 0.27f,-2.3f, -0.26f,-2.33f, -0.51f,1.21f, -0.16f,2.42f
    );

    private static final BodyImage spoonImage = new BodyImage("data/spoon.png",5f);

    // Boy throws spoon
    public Spoon(World world) {
        super(world, spoonShape);
        addImage(spoonImage);
    }
}
