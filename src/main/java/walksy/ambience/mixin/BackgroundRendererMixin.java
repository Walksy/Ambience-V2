package walksy.ambience.mixin;

import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.WorldRenderer;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.FogManager;

import java.awt.*;

@Mixin(WorldRenderer.class)
public class BackgroundRendererMixin {

    @Inject(method = "renderSky(Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;FLnet/minecraft/client/render/Camera;ZLjava/lang/Runnable;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/BackgroundRenderer;applyFogColor()V", shift = At.Shift.AFTER))
    private void getApplyFog(Matrix4f matrix4f, Matrix4f projectionMatrix, float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback, CallbackInfo ci)
    {
        if (ConfigIntegration.CONFIG.instance().overworldSkyGradientEnabled && ConfigIntegration.CONFIG.instance().modEnabled) {
            FogManager.INSTANCE.overrideFog();
        }
    }
}
