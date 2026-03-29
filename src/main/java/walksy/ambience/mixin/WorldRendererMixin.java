package walksy.ambience.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.Config;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @ModifyArg(method = "method_62205", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/CloudRenderer;renderClouds(ILnet/minecraft/client/option/CloudRenderMode;FLnet/minecraft/util/math/Vec3d;JF)V"))
    private int modifyColor(int original) {
        if (Config.modEnabled && Config.cloudColorEnabled) {
            return Config.cloudColor.getRGB();
        }
        return original;
    }
}
