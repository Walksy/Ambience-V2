package walksy.ambience.mixin.blaze3d;

import com.mojang.blaze3d.systems.RenderSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.FogManager;

@Mixin(RenderSystem.class)
public class RenderSystemMixin {

    @ModifyArgs(method = "clearColor", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/platform/GlStateManager;_clearColor(FFFF)V"))
    private static void clearColor(Args args)
    {
        if (ConfigIntegration.CONFIG.instance().overworldSkyGradientEnabled && ConfigIntegration.CONFIG.instance().modEnabled) {
            FogManager.INSTANCE.overrideRenderSystemFog(args);
        }
    }
}
