package walksy.ambience.interfaze;

import net.minecraft.client.texture.NativeImage;

public interface ISpriteContents {

    NativeImage getImage();

    void setMipmapLevels(NativeImage[] images);
}
