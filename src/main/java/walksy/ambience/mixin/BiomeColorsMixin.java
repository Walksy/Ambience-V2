package walksy.ambience.mixin;

import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.EnvironmentColorOverrider;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {

    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void overrideWaterColor(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir)
    {
        if (!ConfigIntegration.CONFIG.instance().waterEnabled || !ConfigIntegration.CONFIG.instance().modEnabled) return;
        cir.setReturnValue(EnvironmentColorOverrider.FluidOverrider.getColor());
    }
}
