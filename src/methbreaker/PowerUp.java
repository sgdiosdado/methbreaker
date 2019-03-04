/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author inakijaneiro
 */
public class PowerUp extends Item {
    
    private int speed;

    public PowerUp(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.speed = 3;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public boolean intersecta(Object obj) {   
        return obj instanceof Player && getHitbox().intersects(((Player) (obj)).getPerimetro());
    }  
    

    @Override
    public void tick() {
        //only moves down
        setY(getY() + getSpeed());
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.powerUp, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
