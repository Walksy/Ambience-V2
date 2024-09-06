package walksy.ambience.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.world.biome.FoliageColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import walksy.ambience.manager.EnvironmentManager;

@Mixin(FoliageColors.class)
public abstract class FoliageColorsMixin {

    @ModifyReturnValue(method = "getBirchColor", at = @At("RETURN"))
    private static int onGetBirchColor(int original) {
        return EnvironmentManager.INSTANCE.getFoliageColor(original);
    }

    @ModifyReturnValue(method = "getSpruceColor", at = @At("RETURN"))
    private static int onGetSpruceColor(int original) {
        return EnvironmentManager.INSTANCE.getFoliageColor(original);
    }

    @ModifyReturnValue(method = "getMangroveColor", at = @At("RETURN"))
    private static int onGetMangroveColor(int original) {
        return EnvironmentManager.INSTANCE.getFoliageColor(original);
    }
}
