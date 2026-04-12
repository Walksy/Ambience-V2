package walksy.ambience.mixin;

import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.Config;

@Mixin(ClientLevel.ClientLevelData.class)
public class ClientLevelDataMixin {

    @Inject(method = "getGameTime", at = @At("HEAD"), cancellable = true)
    public void onGetTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if (Config.overrideTime && Config.modEnabled) {
            cir.setReturnValue((long)Config.overridedTime);
        }
    }
}
