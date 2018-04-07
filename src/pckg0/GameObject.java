package pckg0;

import javafx.geometry.Point2D;
import javafx.scene.Node;



public class GameObject {


    private Node view;
    private Point2D velocity=new Point2D(0, 0);

    private boolean alive=true;

    public GameObject(Node view) {
        this.view=view;

    }

    public void update(){

        if((view.getTranslateX() + velocity.getX())>600){
            view.setTranslateX(0 + velocity.getX());
        }
        else if(((view.getTranslateX() + velocity.getX())<0)){
            view.setTranslateX(600 + velocity.getX());
        }else{
            view.setTranslateX(view.getTranslateX() + velocity.getX());
        }


        if((view.getTranslateY() + velocity.getY())>600){
            view.setTranslateY(0 + velocity.getY());
        }
        else if(((view.getTranslateY() + velocity.getY())<0)){
            view.setTranslateY(600 + velocity.getY());
        }else{
            view.setTranslateY(view.getTranslateY() + velocity.getY());
        }
    }
    public void setVelocity(Point2D velocity){
        this.velocity=velocity;
    }
    public Point2D getVelocity(){
        return velocity;
    }
    public Node getView(){
        return view;
    }

    public boolean isAlive(){
        return alive;
    }


    public boolean isDead(){
        return !alive;
    }



    public void setAlive(boolean alive){
        this.alive=alive;
    }

    public double getRotate(){
        return view.getRotate();
    }

    public void rotateRight(){
        view.setRotate(view.getRotate()+5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate() ))));
    }
    public void rotateLeft(){
        view.setRotate(view.getRotate()-5);
        setVelocity(new Point2D(Math.cos(Math.toRadians(getRotate())), Math.sin(Math.toRadians(getRotate() ))));
    }
    public boolean isColliding(GameObject other){
        return getView().getBoundsInParent().intersects(other.getView().getBoundsInParent());
    }

}
