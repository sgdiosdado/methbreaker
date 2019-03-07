/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Formatter;

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

    /**
     * Constructs a PowerUp instance
     * 
     * @param x
     * @param y
     * @param width
     * @param height 
     */
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
    
    /**
     * Constructs a PowerUp instance with an extra parameter
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param type 
     */
    public PowerUp(int x, int y, int width, int height, String type) {
        super(x, y, width, height);
        this.speed = 3;
        if (type.equals("BOOST")) {
            this.type = Type.BOOST;
        } else if (type.equals("GROWTH")) {
            this.type = Type.GROWTH;
        }     
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
     * Gets the speed
     * 
     * @return 
     */
    public int getSpeed() {
        return speed;
    }
    
    /**
     * Gets the type of powerUp
     * @return 
     */
    public Type getType() {
        return type;
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
     * Gets the height
     * 
     * @return 
     */
    public int getHeight() {
        return height;
    }
    
    /**
     * Gets the hit box
     * 
     * @return 
     */
    public Rectangle getHitbox() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }


    /**
     * Sets the x
     * 
     * @param x 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Sets the y
     * 
     * @return 
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     * Sets the type of powerUp
     * @param type 
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * Sets the width
     * 
     * @param width 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the height
     * 
     * @param height 
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Gets the speed
     * 
     * @param speed 
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    /**
     * Gets if the powerUp intersected with the player
     * 
     * @param obj
     * @return 
     */
    public boolean intersecta(Object obj) {   
        return obj instanceof Player && getHitbox().intersects(((Player) (obj)).getPerimetro());
    }  
    
    /**
     * Writes it's attributes in a file
     * @param file 
     */
    public void save(Formatter file){
        file.format("%s%s%s%s%s", getX() + " ", getY() + " ", getWidth() + " ", getHeight() + " ", getType().toString() + " ");
    }
    
    /**
     * Ticks the necessary attributes
     */
    @Override
    public void tick() {
        //only moves down
        setY(getY() + getSpeed());
    }

    /**
     * Draws the powerUp in the canvas
     * 
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        if (getType() == Type.BOOST) {
            g.drawImage(Assets.boostPowerUp, getX(), getY(), getWidth(), getHeight(), null);
        } else if (getType() == Type.GROWTH) {
            g.drawImage(Assets.growthPowerUp, getX(), getY(), getWidth(), getHeight(), null);
        }
    }
    
}
