package game.Bodies;
import city.cs.engine.*;

public class Wall extends StaticBody {
    private static final Shape groundShape = new BoxShape(28, 1f);

    // bottom wall and top wall
    public Wall(World world) {
        super(world, groundShape);
    }
}
