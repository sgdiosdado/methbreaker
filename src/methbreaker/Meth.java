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
public class Meth extends Item {
    
    private Game game;
    private boolean exists;

    /**
     * Constructs a Meth instance
     * 
     * @param x
     * @param y
     * @param width
     * @param height
     * @param game 
     */
    Meth(int x, int y, int width, int height, Game game)  {
        super(x, y, width, height);
        this.game = game;
    }
    
    /**
     * Gets a Rectangle instance, functions as a hitbox
     * @return 
     */
    public Rectangle getPerimetro() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    /**
     * Writes it's needed attributes in a file
     * 
     * @param file 
     */
    public void save(Formatter file){
        file.format("%s%s%s%s", getX() + " ", getY() + " ", getWidth() + " ", getHeight() + " ");
    }
    
    @Override
    public void tick() {
    }

    /**
     * Draws the instance in the canvas
     * 
     * @param g 
     */
    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.methbrick, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}
