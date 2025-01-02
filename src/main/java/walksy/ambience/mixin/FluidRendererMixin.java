package walksy.ambience.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.block.FluidRenderer;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.client.texture.Sprite;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.ConfigIntegration;
import walksy.ambience.manager.EnvironmentColorOverrider;
import walksy.ambience.manager.NativeImageManager;

import java.awt.*;

@Mixin(FluidRenderer.class)
public class FluidRendererMixin {

    @Shadow @Final private Sprite[] lavaSprites;
    @Shadow @Final private Sprite[] waterSprites;
    @Shadow private Sprite waterOverlaySprite;
    @Unique private final ThreadLocal<Boolean> overrideVertex = ThreadLocal.withInitial(() -> false);

    @Inject(method = "render", at = @At("HEAD"))
    private void onRender(BlockRenderView world, BlockPos pos, VertexConsumer vertexConsumer, BlockState blockState, FluidState fluidState, CallbackInfo ci) {

        overrideVertex.set(EnvironmentColorOverrider.FluidOverrider.shouldOverride(fluidState.getFluid()));
    }

    @Inject(method = "vertex", at = @At("HEAD"), cancellable = true)
    private void onVertex(VertexConsumer vertexConsumer, float x, float y, float z, float red, float green, float blue, float u, float v, int light, CallbackInfo info) {
        if (overrideVertex.get()) {
            Color color = new Color(EnvironmentColorOverrider.FluidOverrider.getColor(Fluids.LAVA));
            overrideVertex(vertexConsumer, x, y, z, color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha(), u, v, light);
            info.cancel();
        }
    }


    @Inject(method = "onResourceReload", at = @At("TAIL"))
    public void onUpdateTextures(CallbackInfo ci) {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().lavaEnabled) {
            Sprite originalStillLava = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.LAVA.getDefaultState()).getParticleSprite();
            this.lavaSprites[0] = NativeImageManager.applyGrayscale(originalStillLava);

            Sprite originalFlowingLava = ModelLoader.LAVA_FLOW.getSprite();
            this.lavaSprites[1] = NativeImageManager.applyGrayscale(originalFlowingLava);

            this.waterSprites[0] = MinecraftClient.getInstance().getBakedModelManager().getBlockModels().getModel(Blocks.WATER.getDefaultState()).getParticleSprite();
            this.waterSprites[1] = ModelLoader.WATER_FLOW.getSprite();
            this.waterOverlaySprite = ModelLoader.WATER_OVERLAY.getSprite();
        }
    }



    @Unique
    private void overrideVertex(VertexConsumer vertexConsumer, float x, float y, float z, int red, int green, int blue, int alpha, float u, float v, int light) {
        vertexConsumer.vertex(x, y, z).texture(u, v).color(red, green, blue, alpha).light(light).normal(0.0f, 1.0f, 0.0f);
    }
}
