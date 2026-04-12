package walksy.ambience.mixin;

import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.client.renderer.block.FluidRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.Config;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Unique
    private final ThreadLocal<Boolean> overrideVertex = ThreadLocal.withInitial(() -> false);

    @Inject(method = "tesselate", at = @At("HEAD"))
    private void ambience$captureFluidType(BlockAndTintGetter level, BlockPos pos, FluidRenderer.Output output, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        overrideVertex.set(fluidState.is(FluidTags.LAVA));
    }

    @ModifyArgs(
        method = "vertex",
        at = @At(
            value = "INVOKE",
            target = "Lcom/mojang/blaze3d/vertex/VertexConsumer;addVertex(FFFIFFIIFFF)V"
        )
    )
    private void ambience$modifyLavaVertexColor(Args args) {
        if (!Config.modEnabled || !Config.lavaTintEnabled || !this.overrideVertex.get()) return;
        args.set(3, Config.lavaTint.getRGB());
    }
}
