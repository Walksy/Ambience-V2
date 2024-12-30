package walksy.ambience.mixin.sodium;

import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadViewMutable;
import net.caffeinemc.mods.sodium.client.render.chunk.compile.pipeline.DefaultFluidRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.manager.EnvironmentColorOverrider;
import walksy.ambience.manager.NativeImageManager;

@Mixin(targets = "net.caffeinemc.mods.sodium.fabric.render.DefaultFluidRenderer", remap = false)
public abstract class DefaultFluidRendererMixin {

    @Inject(method = "setVertex", at = @At("HEAD"), cancellable = true)
    private static void setVertex(ModelQuadViewMutable quad, int i, float x, float y, float z, float u, float v, CallbackInfo ci) {
        if (EnvironmentColorOverrider.FluidOverrider.shouldOverride(Fluids.LAVA)) {
            Sprite sprite = NativeImageManager.applyGrayscale( quad.getSprite());
            quad.setSprite(sprite);
            quad.setX(i, x);
            quad.setY(i, y);
            quad.setZ(i, z);
            quad.setTexU(i, u);
            quad.setTexV(i, v);
            ci.cancel();
        }
    }
}
