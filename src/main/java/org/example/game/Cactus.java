package org.example.game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Cactus {
    Image image;
    double height;
    double x = 700;
    double y = 0;
    double width = 0;
    double speed = -7;

    public Cactus(double height, Image image) {
        this.height = height;
        this.image = image;
        this.width = image.getWidth();
        this.y = 250-height;
    }
    public void draw(GraphicsContext gc) {
        gc.drawImage(image, x, y, width, height);
    }
    public void update() {
        this.x += speed;

    }
    public Rectangle2D getBounds() {
        return new Rectangle2D(x, y+10, width, height-10);
    }
}
