package walksy.ambience.mixin;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.EnvironmentManager;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {
    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void onGetWaterColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir)
    {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().waterEnabled)
        {
            EnvironmentManager.INSTANCE.setWaterColor(cir);
            cir.cancel();
        }
    }

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void onGetGrassColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().grassEnabled) {
            EnvironmentManager.INSTANCE.setGrassColor(cir);
            cir.cancel();
        }
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void onGetFoliageColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        //if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().foliageEnabled) {
            cir.setReturnValue(EnvironmentManager.INSTANCE.getFoliageColor(-1));
            cir.cancel();
        //}
    }
}
