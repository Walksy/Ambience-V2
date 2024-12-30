package walksy.ambience.manager;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.Sprite;
import walksy.ambience.interfaze.ISpriteContents;

public class NativeImageManager {


    public Sprite[] applyGrayscale(Sprite[] sprites)
    {
        Sprite[] spritz = new Sprite[sprites.length];
        for (int i = 0; i < sprites.length; i++)
        {
            spritz[i] = applyGrayscale(sprites[i]);
        }
        return spritz;
    }

    /**
     * @Author Walksy
     * Desaturates a sprite
     *
     * By converting the sprite to a greyscaled color, the sprite texture becomes a neutral
     * base where the brightness values determine the appearance, and custom
     * colors can be applied accurately without the original sprite color mixing in with the custom color
     */

    public static Sprite applyGrayscale(Sprite sprite) {
        try {
            //Retrieve the image texture of the sprite
            NativeImage image = ((ISpriteContents) sprite.getContents()).getImage();
            if (image != null) {
                //Iterate though every pixel in the texture
                for (int y = 0; y < image.getHeight(); y++) {
                    for (int x = 0; x < image.getWidth(); x++) {
                        //Get the color based off the pixel
                        int color = image.getColorArgb(x, y);

                        //Extract color components of the pixel
                        int alpha = (color >> 24) & 0xFF;
                        int red = (color >> 16) & 0xFF;
                        int green = (color >> 8) & 0xFF;
                        int blue = color & 0xFF;

                        //Calculate the brightness (luminance) based off the color of the pixel
                        int luminance = (int) (0.3 * red + 0.59 * green + 0.11 * blue);
                        //Create a new grayscale color using the calculated luminance
                        int grayscaleColor = (alpha << 24) | (luminance << 16) | (luminance << 8) | luminance;
                        //Replaces the original color of the pixel with the desaturated version

                        image.setColorArgb(x, y, grayscaleColor);
                    }
                }
                /**
                 * This method is attempting to upload a texture to the GPU, but the changes made to the texture in
                 * the CPU memory may not be correctly synchronized with OpenGL. When modifying a texture's pixels
                 * directly (like with image.setColorArgb), it can cause the texture to become misaligned or incorrectly
                 * sized for OpenGL.
                 *
                 * Causes an OpenGL error, effecting FPS
                 */
                //sprite.upload(); //Rebind the updated texture

                //Probably not needed
                //((ISpriteContents) sprite.getContents()).setMipmapLevels(new NativeImage[]{image});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sprite; //Return the sprite
    }
}
