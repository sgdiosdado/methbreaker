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

    private Game game;          // To store the context
    private boolean movable;    // Sets true when first move is done by player
    private int speed;          // To store the speed of the ball
    private int xSpeed;         // To store x-axis speed
    private int ySpeed;         // To store y-axis speed

    public enum Side {
        TOP, RIGHT, BOTTOM, LEFT
    }

    Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.speed = 4;
        this.xSpeed = -speed;
        this.ySpeed = -speed;
    }

    /**
     * Gets the game context
     *
     * @return game.
     */
    public Game getGame() {
        return game;
    }

    /**
     * gets the speed of the ball
     *
     * @return speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the x-speed of the ball
     *
     * @return xSpeed.
     */
    public int getxSpeed() {
        return xSpeed;
    }

    /**
     * Gets the y-speed of the ball
     *
     * @return ySpeed.
     */
    public int getySpeed() {
        return ySpeed;
    }

    /**
     * Checks if the ball can move
     *
     * @return movable
     */
    public boolean isMovable() {
        return movable;
    }

    /**
     * Makes the ball movable or unmovable
     *
     * @param movable
     */
    public void setMovable(boolean movable) {
        this.movable = movable;
    }

    /**
     * Sets the speed of the ball
     *
     * @param speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Puts the ball and the bar in the default position
     */
    public void reset() {
        game.getPlayer().setX(game.getWidth() / 2 - game.getWidth() / 14);
        game.getPlayer().setY(game.getHeight() - (game.PADDING * 2));
        setX(((game.getPlayer().getX() + (game.getPlayer().getWidth())/2) - 16));
        setY(game.getPlayer().getY() - 32);
        setMovable(false);
        game.getPlayer().setCanMove(false);
    }

    /**
     * Sets the x-speed of the ball
     *
     * @param xSpeed
     */
    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    /**
     * Sets the y-speed of the ball
     *
     * @param ySpeed
     */
    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public void bounce(Side side) {
        // Top side
        if ((getxSpeed() < 0 && getySpeed() < 0) && side == Side.TOP) {
            setySpeed(getySpeed() * -1);
        } else if ((getxSpeed() > 0 && getySpeed() < 0) && side == Side.TOP) {
            setySpeed(getySpeed() * -1);
        } // Left side
        else if ((getxSpeed() < 0 && getySpeed() > 0) && side == Side.LEFT) {
            setxSpeed(getxSpeed() * -1);
        } else if ((getxSpeed() < 0 && getySpeed() < 0) && side == Side.LEFT) {
            setxSpeed(getxSpeed() * -1);
        } // Bottom side
        else if ((getxSpeed() > 0 && getySpeed() > 0) && side == Side.BOTTOM) {
            setySpeed(getySpeed() * -1);
        } else if ((getxSpeed() < 0 && getySpeed() > 0) && side == Side.BOTTOM) {
            setySpeed(getySpeed() * -1);
        } // Right side
        else if ((getxSpeed() > 0 && getySpeed() < 0) && side == Side.RIGHT) {
            setxSpeed(getxSpeed() * -1);
        } else if ((getxSpeed() > 0 && getySpeed() > 0) && side == Side.RIGHT) {
            setxSpeed(getxSpeed() * -1);
        }
    }

    /**
     * Creates a Circle object and simulates the "Hitbox" of the ball
     *
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
            setY(getY() + getySpeed());
            setX(getX() + getxSpeed());
        }
        // Reset x position and y position if colision
        if (getX() + getWidth() > getGame().getWidth()) {   // right side
            setX(getGame().getWidth() - getWidth());
            bounce(Side.RIGHT);
        } else if (getX() < 0) {                            // left side
            setX(0);
            bounce(Side.LEFT);
        }
        
        if (getY() >= game.getHeight() - getHeight()) { //down
            setY(game.getHeight() - getHeight());
            setSpeed(getSpeed() * -1);
            reset();
            game.setLives(game.getLives() - 1);
        }
        else if (getY() <= 0) { // up
            setY(0);
            bounce(Side.TOP);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }

}
