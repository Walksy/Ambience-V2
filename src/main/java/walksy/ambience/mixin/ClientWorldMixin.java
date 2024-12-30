package walksy.ambience.mixin;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.manager.EnvironmentColorOverrider;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {


    @Inject(method = "getSkyColor", at = @At("HEAD"), cancellable = true)
    public void overrideSkyColor(Vec3d cameraPos, float tickDelta, CallbackInfoReturnable<Integer> cir)
    {
        if (EnvironmentColorOverrider.SkyOverrider.shouldOverrideSky()) {
            cir.setReturnValue(EnvironmentColorOverrider.SkyOverrider.getColor());
        }
    }

    @Inject(method = "getCloudsColor", at = @At("HEAD"), cancellable = true)
    public void overrideCloudColor(float tickDelta, CallbackInfoReturnable<Integer> cir)
    {
        if (EnvironmentColorOverrider.SkyOverrider.shouldOverrideClouds()) {
            cir.setReturnValue(EnvironmentColorOverrider.SkyOverrider.getCloudColor());
        }
    }
}
