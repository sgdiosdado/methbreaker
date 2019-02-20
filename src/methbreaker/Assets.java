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
    public static BufferedImage playerUp[]; // pictures to go up
    public static BufferedImage playerLeft[]; // pictures to go left
    public static BufferedImage playerDown[]; // pictures to go down
    public static BufferedImage playerRight[]; // pictures to go right
    public static BufferedImage sprites; // to store the sprites


    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/desert_background.png");
        sprites = ImageLoader.loadImage("/images/sprite-sheet-tarea1.png");
        // creating array of images before animations
        SpriteSheet spritesheet = new SpriteSheet(sprites);
        playerUp = new BufferedImage[9];
        playerLeft = new BufferedImage[9];
        playerDown = new BufferedImage[9];
        playerRight = new BufferedImage[9];
        // cropping the pictures from the seet into the array
        for (int i = 0; i < 9; i++) {
            playerUp[i] = spritesheet.crop(i * 64, 512, 64, 64);
            playerLeft[i] = spritesheet.crop(i * 64, 576, 64, 64);
            playerDown[i] = spritesheet.crop(i * 64, 640, 64, 64);
            playerRight[i] = spritesheet.crop(i * 64, 704, 64, 64);
        }
    }

}
