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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {
    @Unique
    private final ThreadLocal<Integer> alphas = new ThreadLocal<>();
    /*
    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo ci) {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().lavaEnabled && fluidState.isIn(FluidTags.LAVA)) {
            alphas.set(-2);
        } else {
            alphas.set(-1);
        }
    }

    @Inject(method = "vertex", at = @At("HEAD"), cancellable = true)
    private void onVertex(VertexConsumer vertexConsumer, float x, float y, float z, float red, float green, float blue, float u, float v, int light, CallbackInfo info) {
        int alpha = alphas.get();

        if (alpha == -2) {
            Color color = ConfigIntegration.CONFIG.instance().lavaColor;
            vertex(vertexConsumer, x, y, z, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), u, v, light);
            info.cancel();
        }
        else if (alpha != -1) {
            vertex(vertexConsumer, x, y, z, (int) (red * 255), (int) (green * 255), (int) (blue * 255), alpha, u, v, light);
            info.cancel();
        }
    }

    @Unique
    private void vertex(VertexConsumer vertexConsumer, float x, float y, float z, int red, int green, int blue, int alpha, float u, float v, int light) {
        vertexConsumer.vertex(x, y, z).color(red, green, blue, alpha).texture(u, v).light(light).normal(0.0f, 1.0f, 0.0f);
    }

     */
}
