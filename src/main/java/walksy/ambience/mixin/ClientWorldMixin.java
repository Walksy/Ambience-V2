package walksy.ambience.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.EnvironmentManager;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {

    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    private void onGetSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Vec3d> cir) {
        if (ConfigIntegration.CONFIG.instance().skyEnabled && ConfigIntegration.CONFIG.instance().modEnabled)
        {
            EnvironmentManager.INSTANCE.setSkyColor(cir);
            cir.cancel();
        }
    }

    @Inject(method = "getCloudsColor", at = @At("HEAD"), cancellable = true)
    private void onGetCloudColor(float tickDelta, CallbackInfoReturnable<Vec3d> cir) {
        if (ConfigIntegration.CONFIG.instance().cloudEnabled && ConfigIntegration.CONFIG.instance().modEnabled) {
            EnvironmentManager.INSTANCE.setCloudColor(cir);
            cir.cancel();
        }
    }
}
