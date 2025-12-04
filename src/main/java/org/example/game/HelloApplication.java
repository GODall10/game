package org.example.game;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;


public class HelloApplication extends Application {

    Image bigcactus1 = new  Image(getClass().getResource("big-cactus1.png").toExternalForm());
    Image bigcactus2 = new  Image(getClass().getResource("big-cactus2.png").toExternalForm());
    Image bigcactus3 = new  Image(getClass().getResource("big-cactus3.png").toExternalForm());
    Image cactus1 = new  Image(getClass().getResource("cactus1.png").toExternalForm());
    Image cactus2 = new  Image(getClass().getResource("cactus2.png").toExternalForm());
    Image cactus3 = new  Image(getClass().getResource("cactus3.png").toExternalForm());
    Image gameovr = new   Image(getClass().getResource("game-over.png").toExternalForm());
    Image reset = new   Image(getClass().getResource("reset.png").toExternalForm());

    Image bird = new Image(getClass().getResource("bird2.png").toExternalForm());

    Image dead = new   Image(getClass().getResource("dino-dead.png").toExternalForm());
    AnimationTimer gameTimer;
    AudioClip clipup = new  AudioClip(getClass().getResource("100up.mp3").toExternalForm());
    Label score;
    ArrayList<Cactus> enemies = new ArrayList<Cactus>();
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
    double spawntimer;
    boolean gameOver = false;
    double playery = height - dinoHeight;
    private Sprite player;
    private Cactus cactus;
    private Track track;
    private double contdouble;
    private double paddingy = 10;
    private double paddingx = 10;
    Rectangle2D playerbounds;
    Rectangle2D cactusbounds;
    Pane root = new Pane();
    private long lastTime = 0;
    private double scoreDouble = 0;
    Canvas canvas;
    @Override
    public void start(Stage stage) {
        score = new Label("Score:");
        score.setLayoutX(40);
        score.setLayoutY(40);
        score.setStyle("-fx-font-size: 20;");
        canvas = new Canvas(width, height);
        root.getChildren().add(canvas);
        root.getChildren().add(score);
        Scene scene= new Scene(root,width,height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image Playersprite = new Image(getClass().getResource("dino-run.gif").toExternalForm());
        player = new Sprite(50,(height-dinoHeight),dinoWidth,dinoHeight);
        cactus = new Cactus(100,cactus1);
        track = new Track(11);
        System.setProperty("prism.order", "d3d");
        System.setProperty("prism.forceGPU", "true");
        canvas.setCache(true);
        canvas.setCacheHint(CacheHint.SPEED);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {

                if(gameOver) {
                    Timeline delay = new Timeline(new KeyFrame(Duration.millis(500),e-> {
                        gameOver = false;
                        cactus = null;
                        player.setState("RUNNING");
                        lastTime = 0;     // IMPORTANTE per evitare punteggi sfasati
                        scoreDouble = 0;
                        contdouble = 0;
                        gameTimer.start();
                    }));
                    delay.setCycleCount(1);
                    delay.play();
                    return;
                } else {
                    player.jump();
                }

            }
            if (event.getCode() == KeyCode.DOWN) {
                player.duck();
            }
        });
        scene.setOnKeyReleased(event -> {
           if (event.getCode() == KeyCode.DOWN) {
               player.resetduck();
           }
        });

        stage.setScene(scene);
        stage.show();
        stage.requestFocus();
        gameLoop(gc);
        gameTimer.start();
    }

    private void gameLoop(GraphicsContext gc) {
         gameTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if(lastTime == 0) {
                    lastTime = now;
                }
                double deltaSeconds = (now - lastTime) / 1_000_000_000.0; // nanosecondi → secondi
                lastTime = now;
                scoreDouble += deltaSeconds * 10;
                contdouble += deltaSeconds * 10;
                if(contdouble >= 100) {
                    clipup.play();
                    contdouble = 0;
                }
                score.setText("score: " + (int)scoreDouble);

                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                spawntimer += deltaSeconds;
                if (enemies.isEmpty()) {
                    spawnEnemy();
                } else {
                    Cactus last = enemies.get(enemies.size() - 1);

                    // se l'ultimo è abbastanza lontano dalla destra → spawn
                    if (last.x < width - ((int)(Math.random() * ((750 - 380) + 1)) + 380)) {
                        spawnEnemy();
                    }
                }

                for (int i = 0; i < enemies.size(); i++) {
                    cactus = enemies.get(i);
                    cactus.draw(gc);
                    cactus.update();
                    if (cactus.x + cactus.width < 0) {
                        enemies.remove(i);
                        i--;
                        continue;
                    }
                    if(scoreDouble>400){
                        cactus.speed=-10;
                    }else if(scoreDouble>1000){
                        cactus.speed=-11;
                    } else if (scoreDouble>2500) {
                        cactus.speed=-12;
                    }

                    if (player.getBounds().intersects(cactus.getBounds())) {

                        player.die(); // cambia stato a DEAD

                        // ridisegno tutto sul canvas prima di fermare il timer
                        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        track.draw(gc);
                        if (cactus != null) cactus.draw(gc);
                        player.draw(gc);
                        enemies.clear();// ora mostra DEAD

                        gc.drawImage(gameovr, 200, 50);
                        gc.drawImage(reset, 350, 100);

                        gameTimer.stop();
                        scoreDouble = 0;
                        contdouble = 0;
                        score.setText("score: " + (int)scoreDouble);
                        gameOver = true;
                        return;
                    }
                }


                // update degli sprite
                player.update();
                // draw degli sprite
                player.draw(gc);
                track.draw(gc);

                track.update(1);
            }
        } ;
    }

    public static void main(String[] args) {
        launch();
    }
    private void spawnEnemy() {
        int numgen = (int) (Math.random() * 1000);

        if (numgen < 100) {
            enemies.add(new Cactus(100, bigcactus1));
        }
        else if (numgen < 200) {
            enemies.add(new Cactus(100, bigcactus2));
        }
        else if (numgen < 300) {
            enemies.add(new Cactus(100, bigcactus3));
        }
        else if (numgen < 600) {
            enemies.add(new Cactus(100,cactus1));
        }
        else if (numgen < 800) {
            enemies.add(new Cactus(100,cactus2));
        }
        else {
            enemies.add(new Cactus(100,cactus3));
        }
    }
}
