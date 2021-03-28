package game.Bodies;
import city.cs.engine.*;

public class Jam extends StaticBody {
    private static final Shape jamShape = new PolygonShape(
            0.71f,1.155f,
            0.835f,0.85f,
            0.84f,-1.125f,
            -0.835f,-1.145f,
            -0.875f,0.845f,
            -0.735f,1.185f
    );

    private static final BodyImage jamImage = new BodyImage("data/jam.png",2.5f);

    // Jam pickup
    public Jam(World world) {
        super(world, jamShape);
        addImage(jamImage);
    }
}
