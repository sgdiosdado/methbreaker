/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package methbreaker;

import java.awt.image.BufferedImage;

/**
 *
 * @author antoniomejorado
 */
public class Assets {

    public static BufferedImage background;     // to store background image
    public static BufferedImage player[];       // to store the player animation
    public static BufferedImage playerSprites;  // to store the player spritesheet
    public static BufferedImage methbrick;      // to store the meth image
    public static BufferedImage ball[];         // to store the ball animation
    public static BufferedImage ballSprites;    // to store the ball spritesheet
    public static BufferedImage lives;          // to store the lives (heart) image
    public static BufferedImage boostPowerUp;   // to store the boost powerUp image
    public static BufferedImage growthPowerUp;  // to store the groth powerUp image
    public static BufferedImage gameOverScreen; // to store the game over image
    public static SoundClip brickBreaking;      // to store the brick breaking sound
    public static SoundClip boostPowerUpSound;  // to store the boost power-up sound
    public static SoundClip growthPowerUpSound;  // to store the growth power-up sound
    public static SoundClip lifeLost;           // to store the life lost sound
    public static SoundClip playerHit;          // to store the ball hitting the player sound

    /**
     * initializing the images of the game
     */
    public static void init() {
        // Loading sounds
        brickBreaking = new SoundClip("/sounds/meth_breaking.wav");
        boostPowerUpSound = new SoundClip("/sounds/powerUp.wav");
        lifeLost = new SoundClip("/sounds/life_lost.wav");
        playerHit = new SoundClip("/sounds/player_hit.wav");
        growthPowerUpSound  = new SoundClip("/sounds/growth.wav");
        
        // Loading images and spritesheets
        background = ImageLoader.loadImage("/images/desert_background.png");
        playerSprites = ImageLoader.loadImage("/images/hank_bar.png");
        methbrick = ImageLoader.loadImage("/images/methbrick.png");
        ballSprites = ImageLoader.loadImage("/images/pizza.png");
        lives = ImageLoader.loadImage("/images/life.png");
        boostPowerUp = ImageLoader.loadImage("/images/boost_powerUp.png");
        growthPowerUp = ImageLoader.loadImage("/images/donut.png");
        gameOverScreen = ImageLoader.loadImage("/images/game_over.png");
        
        
        // Creating array of images before animations
        SpriteSheet playerSpritesheet = new SpriteSheet(playerSprites);
        player = new BufferedImage[4];
        ball = new BufferedImage[4];
        SpriteSheet ballSpritesheet = new SpriteSheet(ballSprites);
        
        // Cropping the pictures from the seet into the array
        for (int i = 0; i < player.length; i++) {
            player[i] = playerSpritesheet.crop(i * 64, 0, 64, 18);
        }
        for(int i = 0; i < ball.length; i++){
            ball[i] = ballSpritesheet.crop(i * 64, 0, 64, 64);
        }
    }

}
