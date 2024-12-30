package walksy.ambience.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {

    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir)
    {
        if (ConfigIntegration.CONFIG.instance().overrideTime && ConfigIntegration.CONFIG.instance().modEnabled)
        {
            cir.setReturnValue((long)ConfigIntegration.CONFIG.instance().overridedTime);
            cir.cancel();
        }
    }
}
