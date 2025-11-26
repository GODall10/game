package org.example.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Track {
    Image track;
    private double offset= 0;
    private double speed;
    Image image;
    double x1 = 0;
    double x2;
    double width=750;//image.getWidth();
    double height =0; //image.getHeight();
    double y = 0;
    double tileWidth;
    double tileHeight;
    double x = 0;

    public Track(int speed) {
        this.speed = speed;
        track = new Image(getClass().getResource("track.png").toExternalForm());
        tileWidth = 800;
        tileHeight = 25;
    }
    void update(double dt){
        offset -= speed * dt;

        if (offset <= -tileWidth) {
            offset += tileWidth;
        }
    }
    void draw(GraphicsContext gc){
        for (int i = -1; i <= width / tileWidth + 1; i++) {
             x = i * tileWidth + offset;
            gc.drawImage(
                    track,
                    x,
                    250-tileHeight,
                    tileWidth,
                    tileHeight
            );

        }

    }
}
