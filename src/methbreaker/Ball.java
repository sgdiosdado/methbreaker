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
public class Ball extends Item {
    
    private Game game;
    
    Ball(int x, int y, int width, int height, Game game)  {
        super(x, y, width, height);
        this.game = game;
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.ball, getX(), getY(), getWidth(), getHeight(), null);
    }
    
}