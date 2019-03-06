/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author inakijaneiro
 */
public class Game implements Runnable {

    public final int PADDING;               // constant for the padding of the videogame
    public final String STATEBOOST;         // constant for 2x speed player boost
    public final String STATEGROWTH;        // constant for growth in player boost
    private BufferStrategy bs;              // to have several buffers when displaying
    private Graphics g;                     // to paint objects
    private Display display;                // to display in the game 
    private String title;                   // title of the window
    private int width;                      // width of the window
    private int height;                     // height of the window
    private Thread thread;                  // thread to create the game
    private boolean running;                // to set the game
    private boolean isPaused;               // to set the pause
    private Player player;                  // to use a player
    private KeyManager keyManager;          // to manage the keyboard
    private LinkedList<Meth> methbricks;    // to store a set of meth bricks
    private Ball ball;                      // to store the ball
    private int lives;                      // to store the amount of lives in the game
    private int score;                      // to store the game score
    private boolean gameEnded;              // to store if the game is over
    private LinkedList<PowerUp> powerUps;   // to store the Power-Up object
    private HashMap<String, Boolean> states;// to store the states of all power-ups in game
    private int statesCounter;              // to count how much time has passed since state

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
        this.running = false;
        this.keyManager = new KeyManager();
        this.methbricks = new LinkedList<Meth>();
        this.powerUps = new LinkedList<PowerUp>();
        this.states = new HashMap<String, Boolean>();
        this.PADDING = 30;
        this.isPaused = false;
        this.lives = 3;
        this.STATEBOOST = "BoostSpeed";
        this.STATEGROWTH = "GrowthPlayer";
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

    public int getScore() {
        return score;
    }

    public int getLives() {
        return lives;
    }

    public Player getPlayer() {
        return player;
    }

    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    public HashMap<String, Boolean> getStates() {
        return states;
    }

    public void setStates(HashMap<String, Boolean> states) {
        this.states = states;
    }

    public int getStatesCounter() {
        return statesCounter;
    }

    public void setStatesCounter(int statesCounter) {
        this.statesCounter = statesCounter;
    }

    public void setPowerUps(LinkedList<PowerUp> powerUps) {
        this.powerUps = powerUps;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    public void eraseStates() {
        
        if (states.get(STATEBOOST)) {
            player.setSpeed(player.getSpeed() / 2);
        } else if (states.get(STATEGROWTH)) {
            player.setWidth(player.getWidth() / 2);
        }
        
        for (Map.Entry<String, Boolean> entry : getStates().entrySet()) {
            entry.setValue(false);
        }
        statesCounter = 0;
    }
    
    /**
     * Initialising the display window of the game
     */
    private void init() {
        display = new Display(title, width, height);
        Assets.init();
        player = new Player(getWidth() / 2 - getWidth() / 14, getHeight() - (PADDING * 2), 1, getWidth() / 7, PADDING, this);
        ball = new Ball((player.getX() + (player.getWidth()) / 2) - 16, player.getY() - 32, 32, 32, this);
        display.getJframe().addKeyListener(keyManager);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 19; j++) {
                methbricks.add(new Meth(PADDING + 64 * j, PADDING*2 + i * 64, 64, 64, this));
            }
        }
        states.put(STATEBOOST, false);
        states.put(STATEGROWTH, false);
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
        // To check the pause flag and modify it when need it.
        if (getKeyManager().p && getKeyManager().isPressable()) {
            isPaused = !isPaused;
            getKeyManager().setPressable(false);
        }
        // When the game is pause, the ticks are not executed.
        if (!isPaused) {
            player.tick();
            ball.tick();
            if (states.get(STATEBOOST) || states.get(STATEGROWTH)){
                statesCounter++;
            }
            if (statesCounter == 900) {
                eraseStates();
            }

            if (!getKeyManager().movement) {
                getPlayer().setCanMove(true);
            }

            for (int i = 0; i < methbricks.size(); i++) {
                Meth meth = methbricks.get(i);
                // checking collision between player and bad
                if (ball.intersecta(meth)) {
                    Assets.brickBreaking.play();
                    if (Math.random() < 0.9) {
                        powerUps.add(new PowerUp((meth.getX() + meth.getWidth() / 2) - 16, meth.getY() + meth.getHeight() + 32, 32, 32));
                    }
                    if (ball.getX() > meth.getX()) {
                        ball.bounce(Ball.Side.LEFT);
                    } else if (ball.getX() < meth.getX()) {
                        ball.bounce(Ball.Side.RIGHT);
                    }
                    if (ball.getY() > meth.getY()) {
                        ball.bounce(Ball.Side.TOP);
                    } else if (ball.getY() < meth.getY()) {
                        ball.bounce(Ball.Side.BOTTOM);
                    }
                    methbricks.remove(i);
                    setScore(getScore() + 10);
                }
                if (ball.intersecta(player)) {
                    ball.setY(player.getY() - ball.getHeight());
                    ball.bounce(Ball.Side.BOTTOM);
                    Assets.playerHit.play();
                }
            }

            for (int i = 0; i < powerUps.size(); i++) {
                PowerUp powerUp = powerUps.get(i);
                powerUp.tick();
                if (powerUp.getY() >= getHeight() - powerUp.getHeight()) {
                    powerUps.remove(powerUp);
                }

                if (powerUp.intersecta(player)) {
                    if (powerUp.getType() == PowerUp.Type.BOOST) {
                        Assets.boostPowerUpSound.play();
                        if (!states.get(STATEBOOST)) {
                            eraseStates();
                            player.setSpeed(player.getSpeed() * 2);
                            states.put(STATEBOOST, true);
                        }
                    } else if (powerUp.getType() == PowerUp.Type.GROWTH) {
                        Assets.growthPowerUpSound.play();
                        if (!states.get(STATEGROWTH)) {
                            eraseStates();
                            player.setWidth(player.getWidth() * 2);
                            states.put(STATEGROWTH, true);
                        }
                    }
                    powerUps.remove(powerUp);
                    statesCounter = 0;
                    
                }
            }

            if (getLives() == 0) {
                gameEnded = true;
            }
        }
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
            if (!gameEnded) {
                g.drawImage(Assets.background, 0, 0, width, height, null);
                g.setFont(new Font("Dialog", Font.BOLD, 24));
                g.setColor(Color.WHITE);
                g.drawString("Score: " + getScore(), PADDING, 24 + PADDING/2);
                player.render(g);
                for (int i = 0; i < methbricks.size(); i++) {
                    methbricks.get(i).render(g);
                }
                ball.render(g);
                for (int i = 0; i < lives; i++) {
                    g.drawImage(Assets.lives, getWidth() - 40 - (i * 40) - PADDING - 5, 8, 40, 48, null); // EL -5 es estetico
                }
                for (int i = 0; i < powerUps.size(); i++) {
                    powerUps.get(i).render(g);
                }
            } else {
                g.drawImage(Assets.gameOverScreen, 0, 0, width, height, null);
                ball.setxSpeed(0);
                ball.setySpeed(0);
                player.setCanMove(false);
                bs.show();
                g.dispose();
            }
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
