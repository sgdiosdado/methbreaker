/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.LinkedList;

/**
 *
 * @author inakijaneiro
 */
public class Game implements Runnable {

    private final int PADDING;              // constant for the padding of the videogame
    private BufferStrategy bs;              //to have several buffers when displaying
    private Graphics g;                     //to paint objects
    private Display display;                //to display in the game 
    private String title;                   //title of the window
    private int width;                      //width of the window
    private int height;                     //height of the window
    private Thread thread;                  //thread to create the game
    private boolean running;                //to set the game
    private Player player;                  // to use a player
    private KeyManager keyManager;          // to manage the keyboard
    private LinkedList<Meth> methbricks;    // to store a set of meth bricks
    private Ball ball;

    /**
     * to	create	title,	width	and	height	and	set	the	game	is	still	not	running
     *
     * @param	title	to	set	the	title	of	the	window
     * @param	width	to	set	the	width	of	the	window
     * @param	height	to	set	the	height	of	the	window
     */
    public Game(String title, int width, int height) {
        this.title = title;
        this.width = width;
        this.height = height;
        running = false;
        keyManager = new KeyManager();
        methbricks = new LinkedList<Meth>();
        this.PADDING = 30;
    }

    /**
     * To get the width of the game window
     *
     * @return an <code>int</code> value with the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * To get the height of the game window
     *
     * @return an <code>int</code> value with the height
     */
    public int getHeight() {
        return height;
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public Ball getBall() {
        return ball;
    }

    /* 
    public MouseManager getMouseManager() {
        return mouseManager;
    }
     */
    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        player = new Player(getWidth() / 2 - getWidth() / 14, getHeight() - (PADDING * 2), 1, getWidth() / 7, PADDING, this);
        ball = new Ball((player.getX() + (player.getWidth())/2) - 16, player.getY() - 32, 32, 32, this);
        display.getJframe().addKeyListener(keyManager);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 19; j++) {
                methbricks.add(new Meth(PADDING + 64 * j, PADDING + i * 64, 64, 64, this));
            }
        }
    }

    @Override
    public void run() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        init();
        // frames per second
        int fps = 60;
        // time for each tick in nano segs;
        double timeTick = 1000000000 / fps;
        // initializing delta
        double delta = 0;
        // define now to use inside the loop
        long now;
        // initializing last time to the computer time in nanoseconds
        long lastTime = System.nanoTime();
        while (running) {
            // setting the time now to the actual time
            now = System.nanoTime();
            // accumulating to delta the difference between times in timeTick units
            delta += (now - lastTime) / timeTick;
            //updating the last time
            lastTime = now;

            // if delta is positive we tick the game
            if (delta >= 1) {
                tick();
                render();
                delta--;
            }
        }
        stop();
    }

    private void tick() {
        keyManager.tick();
        //avancing a player with colision
        player.tick();
    }

    private void render() {
        //get the buffer from the display
        bs = display.getCanvas().getBufferStrategy();
        /*	if	it	is	null,	we	define	one	with	3	buffers	to	display	images	of
	the	game,	if	not	null,	then	we	display every	image	of	the	game	but
	after	clearing	the	Rectanlge,	getting	the	graphic	object	from	the	
	buffer	strategy	element.	
	show	the	graphic	and	dispose	it	to	the	trash	system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            g.drawImage(Assets.background, 0, 0, width, height, null);
            player.render(g);
            ball.render(g);
            for (int i = 0; i < methbricks.size(); i++) {
                methbricks.get(i).render(g);
            }
            //g.drawImage(Assets.player, x, height - 100, 100, 100, null);
            /* g.clearRect(0, 0, width, height);
            g.setColor(Color.red);
            g.drawRect(10, 10, 40, 40);
             */
            bs.show();
            g.dispose();
        }
    }

    /**
     * setting	the	thead	for	the	game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * stopping the thread
     */
    public synchronized void stop() {
        if (running) {
            running = false;
        }
        try {
            thread.join();
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
}
