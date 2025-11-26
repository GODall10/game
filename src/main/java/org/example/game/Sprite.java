package org.example.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Sprite {
    public enum State {
        RUNNING,
        JUMPING,
        DEAD,
        DUCK
    }
    private State state = State.RUNNING;
    private Image runImg;
    private Image jumpImg;
    private Image deadImg;
    private Image duckImg;
    private Rectangle2D bounds;
    AudioClip clipjmp = new  AudioClip(getClass().getResource("jump.mp3").toExternalForm());
    private double duckRatio;

    double x;
    double heightp=250;
    double height=90;

    private static double startHeight=90;
    private static double startWidth=88;

    private static double startX;
    private static double startY;
    double y;
    double width=88;
    double velY;
    double gravity=1;
    double paddingX = 10;
    double paddingY = 10;
    boolean grounded= true;
    double duckHeight = 55;
    double duckWidth = 115;
    double ducky;
    private final double groundY = 250;
    public Sprite(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds= new Rectangle2D(this.x+10, this.y+10, this.width-20, this.height-10);
        runImg = new Image(getClass().getResource("dino-run.gif").toExternalForm());
        jumpImg = new Image(getClass().getResource("dino-jump.png").toExternalForm());
        deadImg = new Image(getClass().getResource("dino-dead.png").toExternalForm());
        duckImg = new Image(getClass().getResource("dino-duck.gif").toExternalForm());
        this.duckHeight = duckHeight;
        this.duckWidth = duckWidth;
        this.ducky=250-duckHeight;
        duckRatio = duckImg.getWidth() / duckImg.getHeight();
        this.startX = x;
        this.startY = y;
        this.startHeight = width;
        this.startWidth = height;
    }
    public void draw(GraphicsContext gc) {
        Image imgtoDraw;
        switch (state) {

            case RUNNING:
                imgtoDraw =  runImg;
                break;
            case JUMPING:
                imgtoDraw =  jumpImg;
                break;
            case DEAD:
                imgtoDraw =  deadImg;
                break;
            case DUCK:
                imgtoDraw =  duckImg;
                break;
            default:
                imgtoDraw = runImg;
                break;
        }
        gc.drawImage(imgtoDraw, x, y, width, height);
    }

        public void update() {
            velY += gravity;
            y += velY;
            bounds= new Rectangle2D(this.x+10, this.y+10, this.width-20, this.height-10);

            // collisione con terreno
            if (y + height >= groundY) {
                y = groundY - height;
                velY = 0;
                grounded = true;

                if (state != State.DEAD && state != State.DUCK) {
                    state = State.RUNNING;
                    height = startHeight;
                    width = startWidth;

                }

            }else{
                // se è in aria e non morto, stato JUMPING
                if (state != State.DEAD) {
                    state = State.JUMPING;
                }
                grounded = false;
            }


        }

    public void jump(){
        if (grounded==true) {
            velY = -21;  // forza del salto verso l'alto
            grounded = false;
            clipjmp.play();
            state = State.JUMPING;
        }

    }
    public boolean collidesWith(Rectangle2D a, Rectangle2D b) {
        return a.intersects(b);
    }
    public Rectangle2D getBounds() {
        return bounds;
    }

    public void die() {
        state = State.DEAD;
    }
    public void duck() {
        if (grounded && state != State.DEAD) {
            state = State.DUCK;
            height = duckHeight;
            width = height * duckRatio; // mantieni proporzioni
            y = groundY - height;       // abbassati
            bounds = new Rectangle2D(x + 10, y + 10, width - 20, height - 10);
        }
    }
    public void resetduck() {
        if (grounded && state != State.DEAD) {
            state = State.RUNNING;
            height = startHeight;
            width = startWidth;
            y = groundY - height;
            bounds = new Rectangle2D(x + 10, y + 10, width - 20, height - 10);
        }
    }
}
