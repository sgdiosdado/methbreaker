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

    private int width;
    private int height;
    private Game game;
    private int speed;
    private Animation currentAnimation;

    public Player(int x, int y, int direction, int width, int height, Game game) {
        super(x, y);
        this.width = width;
        this.height = height;
        this.game = game;
        this.speed = 1;
        currentAnimation = new Animation(Assets.playerRight, 100);
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
        if (game.getKeyManager().left) {
           setX(getX() - 3);
        }
        if (game.getKeyManager().right) {
           setX(getX() + 3);
        }

        // reset x position and y position if colision
        if (getX() + 80 > game.getWidth()) { // right side
            setX(game.getWidth() - 80);
            currentAnimation.setIndex(0);
            currentAnimation.setSpeed(150);
        } else if (getX() < -20) { // left side
            setX(-20);
            currentAnimation.setIndex(0);
            currentAnimation.setSpeed(150);
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(currentAnimation.getCurrentFrame(), getX(), getY(), getWidth(), getHeight(), null);
    }

}
