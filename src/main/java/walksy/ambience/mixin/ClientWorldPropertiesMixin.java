package walksy.ambience.mixin;

import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.Config;

@Mixin(ClientWorld.Properties.class)
public class ClientWorldPropertiesMixin {

    @Inject(method = "getTimeOfDay", at = @At("HEAD"), cancellable = true)
    public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Config.overrideTime && Config.modEnabled) {
            cir.setReturnValue((long)Config.overridedTime);
            cir.cancel();
        }
    }
}
