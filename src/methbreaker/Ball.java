/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Map;
import javafx.scene.shape.Circle;

/**
 *
 * @author inakijaneiro
 */
public class Ball extends Item {

    private final int SPEED = 4;
    private Game game;          // To store the context
    private boolean movable;    // Sets true when first move is done by player
    private int xSpeed;         // To store x-axis speed
    private int ySpeed;         // To store y-axis speed
    private Animation currentAnimation;

    public enum Side {
        TOP, RIGHT, BOTTOM, LEFT
    }

    Ball(int x, int y, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.xSpeed = 0;
        this.ySpeed = -SPEED * 2;
        currentAnimation = new Animation(Assets.ball, 60);
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
     * Puts the ball and the bar in the default position
     */
    public void reset() {
        game.eraseStates();
        setxSpeed(0);
        setySpeed(-SPEED * 2);
        game.getPlayer().setX(game.getWidth() / 2 - game.getWidth() / 14);
        game.getPlayer().setY(game.getHeight() - (game.PADDING * 2));
        setX(((game.getPlayer().getX() + (game.getPlayer().getWidth()) / 2) - 16));
        setY(game.getPlayer().getY() - 32);
        setMovable(false);
        game.getPlayer().setCanMove(false);
        game.getPlayer().setSpeed(8);
        game.setStatesCounter(0);
        
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

    public void bounceAtPlayer(int playerWidth, int playerxPosition) {

        int playerSection = playerWidth / 5;
        setySpeed(-SPEED * 2);
        if (playerxPosition <= getX() && getX() < playerxPosition + playerSection) {
            setxSpeed(-SPEED * 2);
        }
        if (playerxPosition + playerSection <= getX() && getX() < playerxPosition + 2 * playerSection) {
            setxSpeed(-SPEED);
        }
        if (playerxPosition + playerSection * 2 <= getX() && getX() < playerxPosition + 3 * playerSection) {
            setxSpeed(0);
        }
        if (playerxPosition + playerSection * 3 <= getX() && getX() < playerxPosition + 4 * playerSection) {
            setxSpeed(SPEED);
        }
        if (playerxPosition + playerSection * 4 <= getX() && getX() < playerxPosition + 5 * playerSection) {
            setxSpeed(SPEED * 2);
        }
    }

    public void bounce(Side side) {
        // Top side
        if (side == Side.TOP){
            setySpeed(SPEED);
        } 
        // Left side
        else if (side == Side.LEFT) {
            setxSpeed(SPEED);
        } // Bottom side
        else if (side == Side.BOTTOM) {
            setySpeed(-SPEED);
        } // Right side
        else if (side == Side.RIGHT) {
            setxSpeed(-SPEED);
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
        currentAnimation.tick();
        if (isMovable()) {
            setY(getY() + getySpeed());
            setX(getX() + getxSpeed());
        }
        // Reset x position and y position if colision with the canvas
        if (getX() + getWidth() > getGame().getWidth()) {   // right side
            setX(getGame().getWidth() - getWidth());
            bounce(Side.RIGHT);
        } else if (getX() < 0) {                            // left side
            setX(0);
            bounce(Side.LEFT);
        }

        if (getY() >= getGame().getHeight() - getHeight()) { //down
            Assets.lifeLost.play();
            setY(getGame().getHeight() - getHeight());
            reset();
            getGame().setLives(getGame().getLives() - 1);
            getGame().getPowerUps().clear();
        } else if (getY() <= 0) { // up
            setY(0);
            bounce(Side.TOP);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
