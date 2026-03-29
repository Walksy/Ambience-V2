package walksy.ambience.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
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

    @Inject(method = "render", at = @At("HEAD"))
    private void ambience$captureFluidType(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        overrideVertex.set(fluidState.isIn(FluidTags.LAVA));
    }

    @ModifyArgs(
        method = "render",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/render/block/FluidRenderer;vertex(Lnet/minecraft/client/render/VertexConsumer;FFFFFFFFI)V"
        )
    )
    private void ambience$modifyLavaVertexColor(Args args) {
        if (!Config.modEnabled || !Config.lavaTintEnabled || !this.overrideVertex.get()) return;
        args.set(4, Config.lavaTint.getRed() / 255F);
        args.set(5, Config.lavaTint.getGreen() / 255F);
        args.set(6, Config.lavaTint.getBlue() / 255F);
    }
}
