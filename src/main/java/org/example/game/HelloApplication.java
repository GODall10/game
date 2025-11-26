package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    Image bigcactus1 = new  Image(getClass().getResource("big-cactus1.png").toExternalForm());
    Image bigcactus2 = new  Image(getClass().getResource("big-cactus2.png").toExternalForm());
    Image bigcactus3 = new  Image(getClass().getResource("big-cactus3.png").toExternalForm());
    Image cactus1 = new  Image(getClass().getResource("cactus1.png").toExternalForm());
    Image cactus2 = new  Image(getClass().getResource("cactus2.png").toExternalForm());
    Image cactus3 = new  Image(getClass().getResource("cactus3.png").toExternalForm());
    Image gameovr = new   Image(getClass().getResource("game-over.png").toExternalForm());
    Image reset = new   Image(getClass().getResource("reset.png").toExternalForm());
    Image trk = new   Image(getClass().getResource("track.png").toExternalForm());
    AnimationTimer gameTimer;

    double playerx = 50;
    int width = 750;
    int height = 250;
    int dinoHeight = 90;
    int dinoWidth = 88;
    int velocityX=-12;
    int velocityY=0;
    int gravity = 1;
    int numgen = 0;
    int max = 1000;
    int min = 0;
    boolean gameOver = false;
    double playery = height - dinoHeight;
    private Sprite player;
    private Cactus cactus;
    private Track track;
    private double paddingy = 10;
    private double paddingx = 10;
    Rectangle2D playerbounds;
    Rectangle2D cactusbounds;
    Pane root = new Pane();
    Canvas canvas;
    @Override
    public void start(Stage stage) {
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        Scene scene= new Scene(root,width,height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image Playersprite = new Image(getClass().getResource("dino-run.gif").toExternalForm());
        player = new Sprite(50,(height-dinoHeight),dinoWidth,dinoHeight);
        cactus = new Cactus(100,cactus1);
        track = new Track(10);
        System.setProperty("prism.order", "d3d");
        System.setProperty("prism.forceGPU", "true");
        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                player.jump();
                if(gameOver == true) {
                    gameOver = false;
                    cactus = null;
                    gameTimer.start();
                }

            }
        });

        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        gameLoop(gc);
    }

    private void gameLoop(GraphicsContext gc) {
         gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                if(cactus==null) {
                    int numgen = (int) (Math.random() * ((max - min) + 1)) + min;
                    if (numgen < 500) {
                    } else if (numgen > 500 && numgen < 550) {
                        cactus = new Cactus(100, bigcactus1);
                    } else if (numgen > 550 &&  numgen < 575) {
                        cactus = new Cactus(100, bigcactus2);
                    }else if (numgen > 575 && numgen < 600) {
                        cactus = new Cactus(100, bigcactus3);
                    }else if (numgen > 600 && numgen < 850) {
                        cactus = new Cactus(100, cactus1);
                    }else if (numgen > 850&& numgen < 950) {
                        cactus = new Cactus(100, cactus2);
                    }else if (numgen > 950 && numgen < 1000) {
                        cactus = new Cactus(100, cactus3);
                    }
                }
                if (cactus != null) {
                    cactus.draw(gc);
                    cactus.update();
                    if (cactus.x +cactus.width < 0) {
                        cactus = null;
                    }

                }

                // update degli sprite
                player.update();

                //block.update(); // se il blocco non si muove, resta vuoto

                // draw degli sprite
                player.draw(gc);
                track.draw(gc);

                track.update(1);

                // collisioni
                if(cactus != null) {
                    if (player.getBounds().intersects(cactus.getBounds())) {
                        gc.drawImage(gameovr,200,50);
                        gc.drawImage(reset,350,100);
                        gameTimer.stop();
                        gameOver = true;
                    }
                }
            }
        } ; gameTimer.start();
    }

    public static void main(String[] args) {
        launch();
    }
}
