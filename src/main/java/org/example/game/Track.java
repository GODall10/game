package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Track {
    Image image;
    double x1 = 0;
    double x2;
    double width=0;//image.getWidth();
    double height =0; //image.getHeight();
    double y = 0;
    double speed = -6;
    public Track(Image image) {
        this.image = image;
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.y = 250-height;
    }
    void update(){
        this.x1 -= speed;
        this.x2 -= speed;

        // se la prima esce, riportala dietro la seconda
        if (x1 + width <= 0) {
            x1 = x2 + width;
        }

        // se la seconda esce, riportala dietro la prima
        if (x2 + width <= 0) {
            x2 = x1 + width;
        }
    }
    void draw(GraphicsContext gc){
        gc.drawImage(image,x1,y,width,height);
    }
}
