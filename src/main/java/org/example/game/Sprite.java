package org.example.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class Sprite {
    public enum State {
        RUNNING,
        JUMPING,
        DEAD
    }
    private State state = State.RUNNING;
    private Image runImg;
    private Image jumpImg;
    private Image deadImg;
    private Rectangle2D bounds;
    AudioClip clipjmp = new  AudioClip(getClass().getResource("jump.mp3").toExternalForm());

    double x;
    double heightp=250;
    double height=90;
    double y;
    double width=88;
    double velY;
    double gravity=1;
    double paddingX = 10;
    double paddingY = 10;
    boolean grounded= true;
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

                if (state != State.DEAD) {
                    state = State.RUNNING;
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
}
