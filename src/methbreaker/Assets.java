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
    public static BufferedImage player[]; // pictures to go right
    public static BufferedImage sprites; // to store the sprites
    public static BufferedImage methbrick;


    /**
     * initializing the images of the game
     */
    public static void init() {
        background = ImageLoader.loadImage("/images/desert_background.png");
        sprites = ImageLoader.loadImage("/images/hank_bar.png");
        methbrick = ImageLoader.loadImage("/images/methbrick.png");
        // Creating array of images before animations
        SpriteSheet spritesheet = new SpriteSheet(sprites);
        player = new BufferedImage[2];
        // cropping the pictures from the seet into the array
        for (int i = 0; i < 2; i++) {
            player[i] = spritesheet.crop(i * 64, 0, 64, 18);
        }
    }

}
