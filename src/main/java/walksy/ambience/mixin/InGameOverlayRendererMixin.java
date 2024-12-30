package walksy.ambience.mixin;

import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

@Mixin(InGameOverlayRenderer.class)
public class InGameOverlayRendererMixin {

    @ModifyArgs(method = "renderFireOverlay", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumer;color(FFFF)Lnet/minecraft/client/render/VertexConsumer;"))
    private static void overrideInGameFireOverlay(Args args) {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().fireEnabled) {
            Color c = ConfigIntegration.CONFIG.instance().fireColor;

            //Must be casted to a float
            args.set(0, (float)c.getRed() / 255); //Set red vertex color
            args.set(1, (float)c.getGreen() / 255); //Set green vertex color
            args.set(2, (float)c.getBlue() / 255); //Set blue vertex color
        }
    }
}
