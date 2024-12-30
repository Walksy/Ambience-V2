package walksy.ambience.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.ambience.config.ConfigIntegration;

@Mixin(EntityRenderDispatcher.class)
public class EntityRenderDispatcherMixin {

    @Inject(method = "drawFireVertex", at = @At("HEAD"), cancellable = true)
    private static void overrideEntityFireVertex(MatrixStack.Entry entry, VertexConsumer vertices, float x, float y, float z, float u, float v, CallbackInfo ci) {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().fireEnabled) {
            vertices.vertex(entry, x, y, z)
                    .color(ConfigIntegration.CONFIG.instance().fireColor.getRGB())
                    .texture(u, v)
                    .overlay(0, 10)
                    .light(LightmapTextureManager.MAX_BLOCK_LIGHT_COORDINATE)
                    .normal(entry, 0.0F, 1.0F, 0.0F);

            ci.cancel();
        }
    }
}
