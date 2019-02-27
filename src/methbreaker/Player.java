/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;

/**
 *
 * @author inakijaneiro
 */
public class Player extends Item {

    private Game game;
    private int speed;
    private Animation currentAnimation;

    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y, width, height);
        this.game = game;
        this.speed = 8;
        currentAnimation = new Animation(Assets.player, 200);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSpeed() {
        return speed;
    }

    public Game getGame(){
        return game;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public void tick() {

        currentAnimation.tick();
        // moving player depending on flags
        if (getGame().getKeyManager().left) {
            setX(getX() - getSpeed());
        }
        if (getGame().getKeyManager().right) {
            setX(getX() + getSpeed());
        }

        // reset x position and y position if colision
        if (getX() + getWidth() > getGame().getWidth()) { // right side
            setX(getGame().getWidth() - getWidth());
        } else if (getX() < 0) { // left side
            setX(0);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}