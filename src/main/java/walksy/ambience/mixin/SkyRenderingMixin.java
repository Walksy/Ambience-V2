package walksy.ambience.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.SkyRendering;
import net.minecraft.client.render.state.SkyRenderState;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.Config;

@Mixin(SkyRendering.class)
public class SkyRenderingMixin {

    @Inject(method = "updateRenderState", at = @At("TAIL"))
    public void updateRenderState(ClientWorld world, float tickProgress, Camera camera, SkyRenderState state, CallbackInfo ci) {
        if (Config.modEnabled && Config.skyColorEnabled) {
            state.skyColor = Config.skyColor.getRGB();
        }
    }
}
