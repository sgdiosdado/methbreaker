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

    public static BufferedImage background; // to store background image
    public static BufferedImage player[];   // pictures to go right
    public static BufferedImage playerSprites;    // to store the sprites
    public static BufferedImage methbrick;  // to store the meth image
    public static BufferedImage ball[];       // to store the ball (pizza) image
    public static BufferedImage ballSprites;
    public static BufferedImage lives;      // to store the lives (heart) image
    public static BufferedImage powerUp;    // to store the powerUp image

    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/desert_background.png");
        playerSprites = ImageLoader.loadImage("/images/hank_bar.png");
        methbrick = ImageLoader.loadImage("/images/methbrick.png");
        ballSprites = ImageLoader.loadImage("/images/pizza_animated.png");
        lives = ImageLoader.loadImage("/images/heart.png");
        powerUp = ImageLoader.loadImage("/images/redBall.png");
        // Creating array of images before animations
        SpriteSheet playerSpritesheet = new SpriteSheet(playerSprites);
        player = new BufferedImage[4];
        // cropping the pictures from the seet into the array
        for (int i = 0; i < player.length; i++) {
            player[i] = playerSpritesheet.crop(i * 64, 0, 64, 18);
        }
        ball = new BufferedImage[4];
        SpriteSheet ballSpritesheet = new SpriteSheet(ballSprites);
        for(int i = 0; i < ball.length; i++){
            ball[i] = ballSpritesheet.crop(i * 64, 0, 64, 64);
        }
    }

}
