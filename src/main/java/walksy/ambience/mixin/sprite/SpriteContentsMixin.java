package walksy.ambience.mixin.sprite;

import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.SpriteContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import walksy.ambience.interfaze.ISpriteContents;

@Mixin(SpriteContents.class)
public class SpriteContentsMixin implements ISpriteContents {

    @Shadow @Final private NativeImage image;

    @Shadow private NativeImage[] mipmapLevelsImages;

    @Override
    public NativeImage getImage() {
        return this.image;
    }

    @Override
    public void setMipmapLevels(NativeImage[] images) {
        this.mipmapLevelsImages = images;
    }
}
