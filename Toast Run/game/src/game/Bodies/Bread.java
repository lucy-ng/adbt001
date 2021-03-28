package game.Bodies;
import city.cs.engine.*;

public class Bread extends DynamicBody{
    private static final Shape breadShape = new PolygonShape(
            0.385f,1.228f, 1.351f,0.474f, 1.01f,-1.144f, -0.999f,-1.156f, -1.289f,0.441f, -0.681f,1.189f
    );

    private static final BodyImage breadImage = new BodyImage("data/bread.png", 2.5f);

    // Bread weapon pickup for level 3
    public Bread(World world) {
        super(world, breadShape);
        addImage(breadImage);
    }
}
