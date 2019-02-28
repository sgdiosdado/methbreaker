/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;
import javafx.scene.shape.Circle;

/**
 *
 * @author inakijaneiro
 */
public class Ball extends Item {
    
    private Game game;
    private boolean movable;
    private int speed;
    
    Ball(int x, int y, int width, int height, Game game)  {
        super(x, y, width, height);
        this.game = game;
        this.speed = 3;
    }

    /**
     * gets the speed of the ball
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }
   
    /**
     * Checks if the ball can move
     * @return movable
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * Makes the ball movable or unmovable
     * @param movable 
     */
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    /**
     * Sets the speed of the ball
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Creates a Circle object and simulates the "Hitbox" of the ball
     * @return new Circle
     */
    public Circle getHitbox() {
        return new Circle(getX() + (getWidth() / 2), getY() + getHeight() / 2, getWidth() / 2);
    }

    /*
    public boolean intersecta(Meth meth) {
        Circle c0 = getHitbox();
        Rectangle r1= meth.getPerimetro();
        
        double x0 = c0.getCenterX();
        double x1 = r1.getCenterX();
        double y0 = c0.getCenterY();
        double y1 = r1.getCenterY();
      
        
        return Math.hypot(x0-x1, y0-y1) <= (r0 + r1);
    }
    */
    @Override
    public void tick() {
        if (isMovable()) {
            setY(getY() - getSpeed());
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
