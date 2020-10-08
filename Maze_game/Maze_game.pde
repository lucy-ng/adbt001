String endMessage = "FINISH!";
player user; // Declares player from player class
ball[] balls; // List of balls
wall[] walls; // List of walls

void setup() {
  size(1000, 800);

  // Player
  user = new player(50, 50);

  // Walls
  walls = new wall[7];
  walls[0] = new wall(0, 600, 400, 30);
  walls[1] = new wall(200, 0, 30, 400);
  walls[2] = new wall(400, 200, 30, 430);
  walls[3] = new wall(400, 200, 400, 30);
  walls[4] = new wall(600, 400, 30, 400);
  walls[5] = new wall(800, 200, 30, 400);
  walls[6] = new wall(0, 650, 100, 130); // finish

  // Balls
  balls = new ball[10];
  for (int i = 0; i < balls.length; i++) {
    balls[i] = new ball();
    balls[i].xBall = random(50, 1600);
    balls[i].yBall = random(50, 800);
  }
}

void draw() {
  background(#BCBCBC);

  // Player
  user.draw();
  user.move(walls);

  // Walls
  for (int i = 0; i < walls.length; i++) {
    walls[i].draw();
  }
  
  // If player collides with ball, they go back to the beginning
  for (int i = 0; i < balls.length; i++) {
    balls[i].draw();
    balls[i].bounce();
    if (balls[i].collideWithPlayer(balls[i].xBall, balls[i].yBall, user.x, user.y) == true) {
      user.x = 50;
      user.y = 50;
    }
  }
}

class ball
{
  float xBall; // x position of ball
  float yBall; // y position of ball
  float diameter; // diameter of ball
  float velocity; // velocity of ball
  float xDist, yDist; // movement of ball

  ball()
  {
    xBall = 50;
    yBall = 50;
    diameter = 30;
    velocity = 2;
    xDist = random(-6, 6);
    yDist = random(-6, 6);
  }

  void draw() // draws ball
  {
    noStroke();
    fill(255, 255, 255);
    ellipse(xBall, yBall, diameter, diameter);
  }

  void bounce() 
  {
    velocity = random(-7, 7);

    if (xBall <= diameter/2) // Left border
    {
      xDist = abs(xDist); // Opposite direction
    }
    if (yBall <= diameter/2) // Top border
    {
      yDist = abs(yDist);
    }
    if (xBall >= width - diameter/2) // Right border
    {
      xDist = - abs(xDist);
    }
    if (yBall >= height - diameter/2) // Bottom border
    {
      yDist = - abs(yDist);
    }

    xBall = xBall + xDist;
    yBall = yBall + yDist;
  }

  boolean collideWithPlayer(float a1, float a2, float b1, float b2) {
    if (dist(a1, a2, b1, b2) <= (diameter + diameter)/2) 
    {
      return true;
    }
    return false;
  }
}


class player
{
  float x;
  float y;

  player(float newX, float newY)
  {
    x = newX;
    y = newY;
  }

  void draw() // draws ball
  {
    fill(255, 255, 0);
    ellipse(x, y, 30, 30);
  }


  void move(wall[] walls)
  { 
    float playerX = x;
    float playerY = y;
    boolean collideWall = false;

    if (keyPressed == true) {

      if (key == 'd') { // ball moves right
        playerX = playerX + 5;
        // checks collision against border in x direction
        if (playerX > 1000){
          playerX = playerX - 5;
        }
      }
      if (key == 'a') { // ball moves left
        playerX = playerX - 5;
        // checks collision against border in x direction
        if (playerX < 0){
          playerX = playerX + 5;
        }
      }
      if (key == 'w') { // ball moves up
        playerY = playerY - 5;
        // checks collision against border in y direction
        if (playerY < 0){
          playerY = playerY + 5;
        }
      }
      if (key == 's') { // ball moves down
        playerY = playerY + 5;
        // checks collision against border in y direction
        if (playerY > 800){
          playerY = playerY - 5;
        }
      }
    }
    
    
    for (int i = 0; i < walls.length; i++) {
      if (playerX > walls[i].x && playerX < (walls[i].x + walls[i].w) && playerY > walls[i].y && playerY < walls[i].y + walls[i].h) {
        if (i == 6){
          textSize(100);
          fill(255,255,255);
          text(endMessage, 300, 400);
        }
        collideWall = true;
      }
    }

    if (collideWall == false) {
      x = playerX;
      y = playerY;
    }
  }
}

class wall
{
  float x;
  float y;
  float w;
  float h;

  wall(float newX, float newY, float newW, float newH)
  {
    x = newX;
    y = newY;
    w = newW;
    h = newH;
  }

  void draw()
  {
    fill(0);
    rect(x, y, w, h);
  }
}
