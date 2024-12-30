package walksy.ambience.mixin.sodium;

import net.caffeinemc.mods.sodium.client.model.color.ColorProvider;
import net.caffeinemc.mods.sodium.client.model.quad.ModelQuadView;
import net.caffeinemc.mods.sodium.client.world.LevelSlice;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.manager.EnvironmentColorOverrider;

import java.awt.*;
import java.util.Arrays;

@Mixin(targets = "net.caffeinemc.mods.sodium.fabric.render.FluidRendererImpl$DefaultRenderContext", remap = false)
public abstract class SodiumFluidRendererMixin {

    /**
     * Do NOT use
     * While it does fix lava sprites being discolored when using sodium, it causes an OpenGL error:
     * OpenGL debug message: id=1281, source=API, type=ERROR, severity=HIGH, message='GL_INVALID_VALUE error generated. Size and/or offset out of range.'
     * See: DefaultFluidRendererMixin:setVertex
     */
    /*
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void overrideFluidRender(FluidRenderHandler handler, BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        if (EnvironmentColorOverrider.FluidOverrider.shouldOverride(fluidState.getFluid())) {
            Sprite[] sprites = NativeImageManager.INSTANCE.applyGrayScale(handler.getFluidSprites(this.level, this.blockPos, this.fluidState));
            this.renderer.render(this.level, this.blockState, this.fluidState, this.blockPos, this.offset, this.collector, this.meshBuilder, this.material, this.getColorProvider(fluidState.getFluid()), sprites);
            ci.cancel();
        }
    }
     */


    @Inject(method = "getColorProvider", at = @At("HEAD"), cancellable = true)
    private void overrideColorProvider(Fluid fluid, CallbackInfoReturnable<ColorProvider<FluidState>> cir) {
        if (EnvironmentColorOverrider.FluidOverrider.shouldOverride(fluid)) {
            if (fluid.getDefaultState().isIn(FluidTags.LAVA)) {
                cir.setReturnValue(this::lavaColor);
            }
        }
    }


    @Unique
    private void lavaColor(LevelSlice level, BlockPos pos, BlockPos.Mutable posMutable, FluidState state, ModelQuadView quads, int[] colors) {
        int colorValue = EnvironmentColorOverrider.FluidOverrider.getColor(Fluids.LAVA);
        Color c = new Color(colorValue);
        int abgr = (c.getAlpha() << 24) | (c.getRed() << 16) | (c.getGreen() << 8) | c.getBlue();
        Arrays.fill(colors, abgr);
    }

}
