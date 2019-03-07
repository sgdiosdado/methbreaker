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
import java.io.File;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
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
    private Formatter file;                 // to store the saved game file.
    private Scanner scanner;

    /**
     * To create title,	width and height and set the game is still not running
     *
     * @param title to set the title of the window
     * @param width to set the width of the window
     * @param height to set the height of the window
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

    /**
     * To get the keyManager
     *
     * @return <code>KeyManager</code> instance
     */
    public KeyManager getKeyManager() {
        return keyManager;
    }

    /**
     * To get the Ball
     *
     * @return <code>Ball</code> instance
     */
    public Ball getBall() {
        return ball;
    }

    /**
     * To get the score
     *
     * @return <code>int</code> score
     */
    public int getScore() {
        return score;
    }

    /**
     * To get the lives
     *
     * @return <code>int</code> lives
     */
    public int getLives() {
        return lives;
    }

    /**
     * To get the Player
     *
     * @return <code>Player</code> instance
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * To get the powerUps
     *
     * @return <code>LinkedList</code> of powerUps
     */
    public LinkedList<PowerUp> getPowerUps() {
        return powerUps;
    }

    /**
     * Gets HashMap with all the states (powerUps)
     *
     * @return
     */
    public HashMap<String, Boolean> getStates() {
        return states;
    }

    /**
     *
     *
     * @param statesCounter
     */
    public void setStatesCounter(int statesCounter) {
        this.statesCounter = statesCounter;
    }

    /**
     * Sets the amount of lives available
     *
     * @param lives
     */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /**
     * Sets the score
     *
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }
    
    private void loadMethbricks() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 19; j++) {
                methbricks.add(new Meth(PADDING + 64 * j, PADDING*2 + i * 64, 64, 64, this));
            }
        }
    }  
    
    /**
     * Erases all states (powerUps)
     */
    public void eraseStates() {

        if (states.get(STATEBOOST)) {
            player.setSpeed(8);
        }
        if (states.get(STATEGROWTH)) {
            player.setWidth(getWidth() / 7);
        }

        for (Map.Entry<String, Boolean> entry : getStates().entrySet()) {
            entry.setValue(false);
        }
        statesCounter = 0;
    }
    
    public void loopSong(SoundClip song) {
        song.setLooping(true);
        song.play();
    }

    /**
     * Calls every save method from the classes and writes the score and lives
     * on a file
     */
    private void save(){
        try{
            file = new Formatter("game.txt");
        } catch (Exception e) {
            System.out.println("Hubo un problema con el guardado");
        }
        ball.save(file);
        player.save(file);
        file.format("%s", getScore() + " ");
        file.format("%s", getLives() + " ");
        file.format("%s", powerUps.size() + " ");

        // Iterates over all the powerUps and write them in the file
        for (int i = 0; i < powerUps.size(); i++) {
            PowerUp powerUp = powerUps.get(i);

            powerUp.save(file);
        }
        file.format("%s", methbricks.size() + " ");

        // Iterates over all the bricks and write them in the file
        for (int i = 0; i < methbricks.size(); i++) {
            Meth meth = methbricks.get(i);

            meth.save(file);
        }
        
        file.format("%s", statesCounter + " ");
        file.format("%s", states.toString() + " ");
       
        file.close();
    }
    
    /**
     * Reads the saved-game file and uses the load methods of the classes to
     * restore the saved game
     */
    private void load() {
        try {
            int x, y, xspeed, yspeed, width, height;
            String type;
            scanner = new Scanner(new File("game.txt"));

            // Loads ball attributes
            x = scanner.nextInt();
            y = scanner.nextInt();
            xspeed = scanner.nextInt();
            yspeed = scanner.nextInt();
            ball.load(x, y, xspeed, yspeed);

            if (xspeed > 0 || yspeed > 0) {
                ball.setMovable(true);
            }

            // Loads player attributes
            x = scanner.nextInt();
            y = scanner.nextInt();
            xspeed = scanner.nextInt();
            width = scanner.nextInt();
            height = scanner.nextInt();
            player.load(x, y, xspeed, width, height);

            // Loads score and lives
            x = scanner.nextInt();
            y = scanner.nextInt();
            setScore(x);
            setLives(y);

            // Loads powerUps
            int numOfPowerUps = scanner.nextInt();
            powerUps.clear();
            for (int i = 0; i < numOfPowerUps; i++) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                width = scanner.nextInt();
                height = scanner.nextInt();
                type = scanner.next();
                powerUps.add(new PowerUp(x, y, width, height, type));
            }

            // Loads methbricks attributes
            int numOfMethbricks = scanner.nextInt();
            methbricks.clear();
            for (int i = 0; i < numOfMethbricks; i++) {
                x = scanner.nextInt();
                y = scanner.nextInt();
                width = scanner.nextInt();
                height = scanner.nextInt();
                methbricks.add(new Meth(x, y, width, height, this));
            }
            
            x = scanner.nextInt();
            statesCounter = x;
            
            int statesSize = states.size();
            states.clear();
            
            for (int i = 0; i < statesSize; i++) {
                type = scanner.next();
                type = type.replace("{", "");
                int index = type.indexOf("=");
                String key = type.substring(0, index);
                String value = type.substring(index + 1, type.length() - 1);

                if (value.equals("false")) {
                    states.put(key, false);
                }
                else if (value.equals("true")) {
                    states.put(key, true);
                }
            }

        } catch (Exception e) {
            System.out.println("Hubo un problema con la carga.");
        }

    }
    
    /**
     * Restores the game to initial state
     */
    private void reset() {
        ball.reset();
        powerUps.clear();
        eraseStates();
        setLives(3);
        setScore(0);
        gameEnded = false;
        loadMethbricks();
        Assets.gameOverMusic.stop();
        Assets.youDiedMusic.stop();
        loopSong(Assets.music);
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
        loadMethbricks();
        states.put(STATEBOOST, false);
        states.put(STATEGROWTH, false);
        loopSong(Assets.music);
    }

    /**
     * Keeps tracks of FPS of the game
     */
    @Override
    public void run() {
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

    /**
     * Checks all the instances 60 times per second
     */
    private void tick() {
        keyManager.tick();
        // To check the pause flag and modify it when need it
        if (getKeyManager().p && getKeyManager().isPressable()) {
            isPaused = !isPaused;
            getKeyManager().setPressable(false);
        }
        // When pressed, calls the save method
        if (getKeyManager().g) {
            save();
        }
        // When pressed, calls the load method
        if (getKeyManager().c) {
            load();
        }
        
        // When the game is pause, the ticks are not executed.
        if (!isPaused) {
            player.tick();
            ball.tick();
            
            // If there are no bricks, the player wins
            if (methbricks.size() == 0 && !gameEnded){
                gameEnded = true;
                Assets.music.stop();
                loopSong(Assets.gameOverMusic);
            }
            else if (gameEnded) {
                if (getKeyManager().space) {
                    reset();
                }
            }

            // After 15 seconds (900 ticks) the powerUps go off
            if (states.get(STATEBOOST) || states.get(STATEGROWTH)) {
                statesCounter++;
            }
            if (statesCounter == 900) {
                eraseStates();
            }
            
            // Stops ticking a key until it's released
            if (!getKeyManager().movement) {
                getPlayer().setCanMove(true);
            }
            
            // Collisions of the ball with all the bricks
            for (int i = 0; i < methbricks.size(); i++) {
                Meth meth = methbricks.get(i);
                // When ball hits meth
                if (ball.intersecta(meth)) {
                    Assets.brickBreaking.play();
                    
                    // Calculates random drop rate for powerUps
                    if (Math.random() < 0.9) {
                        powerUps.add(new PowerUp((meth.getX() + meth.getWidth() / 2) - 16, meth.getY() + meth.getHeight() + 32, 32, 32));
                    }
                    
                    // Checks at which side of the block the collision took in
                    if (ball.getY() > meth.getY()) {
                        ball.bounce(Ball.Side.TOP);
                    } else if (ball.getY() < meth.getY()) {
                        ball.bounce(Ball.Side.BOTTOM);
                    }
                    if (ball.getX() > meth.getX() && ball.getxSpeed() != 0) {
                        ball.bounce(Ball.Side.LEFT);
                    } else if (ball.getX() < meth.getX() && ball.getxSpeed() != 0) {
                        ball.bounce(Ball.Side.RIGHT);
                    }
                    
                    // The brick is remove from LinkedList and it adds 10 points to the score
                    methbricks.remove(i);
                    setScore(getScore() + 10);
                }
                
                // When the ball hits the player
                if (ball.intersecta(player)) {
                    ball.setY(player.getY() - ball.getHeight());
                    ball.bounceAtPlayer(player.getWidth(), player.getX());
                    Assets.playerHit.play();
                }
            }

            // For each powerUp instance
            for (int i = 0; i < powerUps.size(); i++) {
                PowerUp powerUp = powerUps.get(i);
                powerUp.tick();
                
                // When the powerUp touchs the bottom of the canvas it's removed
                if (powerUp.getY() >= getHeight() - powerUp.getHeight()) {
                    powerUps.remove(powerUp);
                }
                
                // When the powerUp hits the player
                if (powerUp.intersecta(player)) {
                    
                    // Gives the player a movement speed bonus
                    if (powerUp.getType() == PowerUp.Type.BOOST) {
                        Assets.boostPowerUpSound.play();
                        if (!states.get(STATEBOOST)) {
                            eraseStates();
                            player.setSpeed(player.getSpeed() * 2);
                            states.put(STATEBOOST, true);
                        }
                    // Gives the player a size bonus
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
            
            // When the player runs out of lives, the game finishes
            if (getLives() == 0 && !gameEnded) {
                gameEnded = true;
                Assets.music.stop();
                loopSong(Assets.youDiedMusic);
            }
        }
    }

    private void render() {
        // Get the buffer from the display
        bs = display.getCanvas().getBufferStrategy();
        /* 
         * If it is null, we define one with 3 buffers to display images of the game,
         * if not null, then we display every image of the game but after clearing the
         * Rectanlge, getting the graphic object from the buffer strategy element.	
         * Show the graphic and dispose it to the trash system
         */
        if (bs == null) {
            display.getCanvas().createBufferStrategy(3);
        } else {
            g = bs.getDrawGraphics();
            if (!gameEnded) {
                // Draws the background, score and lives
                g.drawImage(Assets.background, 0, 0, width, height, null);
                g.setFont(new Font("Dialog", Font.BOLD, 24));
                g.setColor(Color.WHITE);
                g.drawString("Score: " + getScore(), PADDING, 24 + PADDING / 2);
                for (int i = 0; i < lives; i++) {
                    g.drawImage(Assets.lives, getWidth() - 40 - (i * 40) - PADDING - 5, 8, 40, 48, null); // EL -5 es estetico
                }
                
                // Draws the bricks
                for (int i = 0; i < methbricks.size(); i++) {
                    methbricks.get(i).render(g);
                }
                
                // Draws the existing powerUps
                for (int i = 0; i < powerUps.size(); i++) {
                    powerUps.get(i).render(g);
                }
                
                // Draws the player and ball
                player.render(g);
                ball.render(g);
            } else {
                if (methbricks.size() == 0) {
                    // Winning screen
                    g.drawImage(Assets.gameOverWin, 0, 0, width, height, null);
                } 
                else {
                    // Loosing screen
                    g.drawImage(Assets.gameOverScreen, 0, 0, width, height, null);
                }
                // Stops all instances
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
     * Sets the thread for the game
     */
    public synchronized void start() {
        if (!running) {
            running = true;
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the thread
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
