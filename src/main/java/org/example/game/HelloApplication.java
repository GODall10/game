package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    double playerx = 50;
    int width = 750;
    int height = 250;
    int dinoHeight = 90;
    int dinoWidth = 88;
    int velocityX=-12;
    int velocityY=0;
    int gravity = 1;

    double playery = height - dinoHeight;
    private sprite player;
    Pane root = new Pane();
    Canvas canvas;
    @Override
    public void start(Stage stage) {
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        Scene scene= new Scene(root,width,height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image Playersprite = new Image(getClass().getResource("dino-run.gif").toExternalForm());
        player = new sprite(50,(height-dinoHeight),dinoWidth,dinoHeight);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                player.jump();
            }
        });

        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        gameLoop(gc);
    }

    private void gameLoop(GraphicsContext gc) {
        new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

                // update degli sprite
                player.update();
                //block.update(); // se il blocco non si muove, resta vuoto

                // draw degli sprite
                player.draw(gc);
                //block.draw(gc);

                // collisioni
                /* if (player.collidesWith(block)) {
                    System.out.println("Collisione!");
                }*/
            }
        }.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
