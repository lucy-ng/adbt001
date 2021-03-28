package game.Bodies;
import city.cs.engine.*;

public class Shelf extends StaticBody {
    private static final Shape shelfShape = new PolygonShape(
            1.78f,-0.84f,
            7.71f,-1.26f,
            5.64f,-1.58f,
            -6.71f,-1.62f,
            -7.91f,-1.29f,
            -3.05f,-0.87f
    );

    private static final BodyImage shelfImage = new BodyImage("data/shelf.png", 5f);

    // Shelf platform
    public Shelf(World world) {
        super(world, shelfShape);
        addImage(shelfImage);
    }
}
