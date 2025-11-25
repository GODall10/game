package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.media.AudioClip;

public class sprite {
    static Image image;
    AudioClip clipjmp = new  AudioClip(getClass().getResource("jump.mp3").toExternalForm());
    double x;
    double heightp=250;
    double height=90;
    double y;
    double width=88;
    double velY;
    double gravity=1;
    boolean grounded= true;
    private final double groundY = 250;
    public sprite( double x, double y, double width, double height) {
        this.image = new Image(getClass().getResource("dino-run.gif").toExternalForm());;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
    }

        public void update() {
            velY += gravity;
            y += velY;

            // collisione con terreno
            if (y + height >= groundY) {
                y = groundY - height;
                velY = 0;
                grounded = true;
            }
        }

    public void jump(){
        if (grounded==true) {
            velY = -20;  // forza del salto verso l'alto
            grounded = false;
            sprite.image= new Image(getClass().getResource("dino-jump.png").toExternalForm());
            clipjmp.play();
        }
        sprite.image=new Image(getClass().getResource("dino-run.gif").toExternalForm());
    }


}
