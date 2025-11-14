package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    double playerx = 50;
    double playery = 0;
    Image Playersprite = new Image("sprite1.png");
    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(800, 600);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();     // logica di gioco
                render(gc);   // disegno
            }
        }.start();

        stage.setScene(new Scene(new StackPane(canvas)));
        stage.show();
    }

    private void update() {
        // muovi personaggi, controlli, fisica, ecc
    }

    private void render(GraphicsContext gc) {
        gc.clearRect(0, 0, 800, 600); // pulisci
        gc.drawImage(Playersprite, playerx, playery);
    }

    public static void main(String[] args) {
        launch();
    }
}
