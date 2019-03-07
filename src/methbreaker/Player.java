/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Formatter;

public class Player extends Item {

    private Game game;
    private int speed;
    private Animation currentAnimation;
    private boolean canMove;

    /**
     * Constructs a Player instance
     * 
     * @param x
     * @param y
     * @param direction
     * @param width
     * @param height
     * @param game 
     */
    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.speed = 8;
        currentAnimation = new Animation(Assets.player, 60);
        this.canMove = true;
    }

    /**
     * Gets the x
     * 
     * @return 
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the y
     * 
     * @return 
     */
    public int getY() {
        return y;
    }

    /**
     * Gets the height
     * 
     * @return 
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Gets the width
     * 
     * @return 
     */
    public int getWidth() {
        return width;
    }

    /**
     * Gets the speed
     * 
     * @return 
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Gets the game
     * 
     * @return 
     */
    public Game getGame(){
        return game;
    }

    /**
     * Gets if the player can move
     * 
     * @return 
     */
    public boolean canMove() {
        return canMove;
    }

    /**
     * Sets canMove
     * 
     * @param canMove 
     */
    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
    }
    
    /**
     * Sets x
     * 
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets y
     * 
     * @param y 
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets height
     * 
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets width
     * 
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets speed
     * 
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Writes it's data in the saving file
     * 
     * @param file 
     */
    public void save(Formatter file){
        file.format("%s%s%s%s%s", getX() + " ", getY() + " ", getSpeed() + " ", getWidth() + " ", getHeight() + " ");
    }
    
    /**
     * Load it's necessary data form a file
     * 
     * @param x
     * @param y
     * @param speed
     * @param width
     * @param height 
     */
    public void load(int x, int y, int speed, int width, int height){
        setX(x);
        setY(y);
        setSpeed(speed);
        setWidth(width);
        setHeight(height);
    }

    /**
     * Ticks all the necessary attributes
     */
    @Override
    public void tick() {
        currentAnimation.tick();
        if (canMove) {
            
            // Moving player depending on flags
            if(getGame().getKeyManager().left || getGame().getKeyManager().right){
                getGame().getBall().setMovable(true);
            }
            if (getGame().getKeyManager().left) {
                setX(getX() - getSpeed());
            }
            if (getGame().getKeyManager().right) {
                setX(getX() + getSpeed());
            }

            // Resets x position and y position if colision
            if (getX() + getWidth() > getGame().getWidth()) {   // right side
                setX(getGame().getWidth() - getWidth());
            } else if (getX() < 0) {                            // left side
                setX(0);
            }
        }
    }

    /**
     * Gets a Rectangle instance (hit box)
     * @return 
     */
     public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
     
    /**
     * Draws the player on the canvas
     * 
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
