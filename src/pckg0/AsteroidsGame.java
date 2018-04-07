package pckg0;

import javafx.animation.AnimationTimer;
import javafx.application.Application;

import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;

import java.util.ArrayList;
import java.util.List;




public class AsteroidsGame extends Application {

    private Pane root;

    private List<GameObject> bullets=new ArrayList<GameObject>();
    private List<GameObject> enemies=new ArrayList<GameObject>();

    private GameObject player;
    private GameObject b1=new Border1();
    private GameObject b2=new Border2();
    private GameObject b3=new Border3();
    private GameObject b4=new Border4();




    private Parent createContent() {
        root=new Pane();
        root.setPrefSize(600,600);
        root.getChildren().addAll(b1.getView(),b2.getView(),b3.getView(),b4.getView());

        player=new Player();
        player.setVelocity(new Point2D(1,0));

        addGameObject(player,300,300);

        AnimationTimer timer=new AnimationTimer(){
            @Override
            public void handle(long now){
                onUpdate();
            }
        };
        timer.start();

        return root;
    }

    private void addBullet(GameObject bullet,double x, double y){
        bullets.add(bullet);
        addGameObject(bullet,x,y);
    }
    private void addEnemy(GameObject enemy,double x, double y){
        enemies.add(enemy);
        addGameObject(enemy,x,y);
    }

    private void addGameObject(GameObject object, double x, double y){
        object.getView().setTranslateX(x);
        object.getView().setTranslateY(y);
        root.getChildren().add(object.getView());
    }

    private void onUpdate(){
        for(GameObject bullet:bullets){
            for(GameObject enemy:enemies){
                if(bullet.isColliding(enemy)){
                    bullet.setAlive(false);
                    enemy.setAlive(false);

                    root.getChildren().removeAll(bullet.getView(),enemy.getView());
                }
            }


        }
        for(GameObject bullet:bullets){
                if((bullet.isColliding(b1)|(bullet.isColliding(b2))|(bullet.isColliding(b3))|(bullet.isColliding(b4)))){
                    bullet.setAlive(false);
                    root.getChildren().removeAll(bullet.getView());
                }
        }


        bullets.removeIf(GameObject::isDead);
        enemies.removeIf(GameObject::isDead);

        bullets.forEach(GameObject::update);
        enemies.forEach(GameObject::update);




        player.update();

        if(Math.random()<0.01){
            addEnemy(new Enemy(), Math.random()*root.getPrefWidth(),Math.random()*root.getPrefHeight());
        }
    }

    private void handle(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            player.rotateLeft();
        }
        if (e.getCode() == KeyCode.RIGHT) {
            player.rotateRight();
        }
        if (e.getCode() == KeyCode.SPACE) {
            Bullet bullet = new Bullet();
            bullet.setVelocity(player.getVelocity().normalize().multiply(5));
            addBullet(bullet, player.getView().getTranslateX(), player.getView().getTranslateY());
        }
    }

    private class Player extends GameObject {


        Player() {

            super(new Rectangle(-20,-10,40,20));
        }
    }

    private class Bullet extends GameObject {

        Bullet() {
            super(new Circle(0,0,5, Color.YELLOW));
        }
    }

    private class Enemy extends GameObject {
        Enemy() {
            super(new Circle(0,0,15, Color.CORAL));
        }
    }
    public class Border1 extends GameObject{


        Border1(){
            super(new Line(0,0,0,600));
        }
    }

    public class Border2 extends GameObject{


        Border2(){
            super(new Line(0,0,600,0));
        }
    }
    public class Border3 extends GameObject{


        Border3(){
            super(new Line(0,600,600,600));
        }
    }
    public class Border4 extends GameObject{


        Border4(){
            super(new Line(600,600,600,0));
        }
    }




    @Override
    public void start(Stage stage) throws Exception{
        stage.setScene(new Scene(createContent()));
        stage.getScene().setOnKeyPressed(this::handle);


        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }


}
