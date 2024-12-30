package walksy.ambience.mixin;

import net.minecraft.client.render.*;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.EnvironmentRenderManager;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Shadow @Final private BufferBuilderStorage bufferBuilders;

    @Inject(method = "renderSky", at = @At(value = "TAIL"))
    public void renderSky(FrameGraphBuilder frameGraphBuilder, Camera camera, float tickDelta, Fog fog, CallbackInfo ci)
    {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().overworldSkyGradientEnabled) {
            EnvironmentRenderManager.INSTANCE.renderSky(this.bufferBuilders);
        }
    }
}
