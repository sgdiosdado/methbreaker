/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Formatter;

/**
 *
 * @author inakijaneiro
 */
public class PowerUp extends Item {
    
    private int speed;
    public enum Type {
        BOOST {
            public String toString() {
                return "BOOST";
            }
        },
        GROWTH {
            public String toString() {
                return "GROWTH";
            }
        }
    }
    private Type type;

    public PowerUp(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.speed = 3;
        if (Math.random() < 0.5){
            this.type = Type.BOOST;
        }
        else {
            this.type = Type.GROWTH;
        }
     }
    
    public PowerUp(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.speed = 3;
//        this.type = Type.BOOST;
        if (type.equals("BOOST")) {
            this.type = Type.BOOST;
        } else if (type.equals("GROWTH")) {
            this.type = Type.GROWTH;
        }     
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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
    
    public void save(Formatter file){
        file.format("%s%s%s%s%s", getX() + " ", getY() + " ", getWidth() + " ", getHeight() + " ", getType().toString() + " ");
    }
    
    @Override
    public void tick() {
        //only moves down
        setY(getY() + getSpeed());
    }

    @Override
    public void render(Graphics g) {
        if (getType() == Type.BOOST) {
            g.drawImage(Assets.boostPowerUp, getX(), getY(), getWidth(), getHeight(), null);
        } else if (getType() == Type.GROWTH) {
            g.drawImage(Assets.growthPowerUp, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
