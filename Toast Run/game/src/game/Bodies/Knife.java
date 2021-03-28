package game.Bodies;
import city.cs.engine.*;

public class Knife extends StaticBody {
    private static final Shape knifeShape = new PolygonShape(
            0.09f,2.49f, 0.38f,0.16f, 0.29f,-2.37f, -0.24f,-2.33f, -0.39f,0.16f, -0.06f,2.4f
    );

    private static final BodyImage knifeImage = new BodyImage("data/knife.png", 5f);

    // Knife obstacle
    public Knife(World world){
        super(world, knifeShape);
        addImage(knifeImage);
    }
}
