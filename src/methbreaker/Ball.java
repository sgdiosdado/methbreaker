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
    private boolean movable; //sets true when first move is done by player
    private int speed;
    
    Ball(int x, int y, int width, int height, Game game)  {
        super(x, y, width, height);
        this.game = game;
        this.speed = 4;
    }

    public Game getGame() {
        return game;
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
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean intersecta(Object obj) {   
        return (obj instanceof Meth && getHitbox().intersects(((Meth) (obj)).getPerimetro()) 
                || obj instanceof Player && getHitbox().intersects(((Player) (obj)).getPerimetro()));
    }        

    @Override
    public void tick() {
        if (isMovable()) {
            setY(getY() - getSpeed());
        }
        // reset x position and y position if colision
        if (getX() + getWidth() > getGame().getWidth()) { // right side
            setX(getGame().getWidth() - getWidth());
            setSpeed(getSpeed() * -1);
        } else if (getX() < 0) { // left side
            setX(0);
            setSpeed(getSpeed() * -1);
        }
        
        if (getY() >= game.getHeight()) {
            setY(game.getHeight() - getHeight());
            setSpeed(getSpeed() * -1);
        }
        else if (getY() <= 0) {
            setY(0);
            setSpeed(getSpeed() * -1);
        }    
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
