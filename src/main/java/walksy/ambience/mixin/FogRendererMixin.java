package walksy.ambience.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.fog.AtmosphericFogModifier;
import net.minecraft.client.render.fog.FogModifier;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.Config;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Unique
    private FogModifier currentFogModifier = null;

    @Unique
    private Vector4f currentFogColor = null;

    @Redirect(method = "getFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogModifier;getFogColor(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/render/Camera;IF)I"))
    public int getFogColor(FogModifier instance, ClientWorld world, Camera camera, int viewDistance, float skyDarkness) {
        this.currentFogModifier = instance;
        return instance.getFogColor(world, camera, viewDistance, skyDarkness);
    }

    @ModifyArgs(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogRenderer;applyFog(Ljava/nio/ByteBuffer;ILorg/joml/Vector4f;FFFFFF)V"))
    public void applyFogToRenderPass(Args args) {
        if (!Config.modEnabled) {
            return;
        }
        if (this.currentFogModifier instanceof AtmosphericFogModifier) {
            this.currentFogColor = args.get(2);
            Vector4f fogColor = Config.getFogColor();
            if (fogColor != null) {
                this.currentFogColor = fogColor;
            } /*else if (Config.skyColorEnabled) {
                this.currentFogColor = Config.getSkyColor();
            }*/
            args.set(2, this.currentFogColor);
        }
    }

    @ModifyArgs(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogModifier;applyStartEndModifier(Lnet/minecraft/client/render/fog/FogData;Lnet/minecraft/client/render/Camera;Lnet/minecraft/client/world/ClientWorld;FLnet/minecraft/client/render/RenderTickCounter;)V"))
    public void applyFogStartEndModifiers(Args args) {
        if (!Config.modEnabled) {
            return;
        }
        args.set(3, (float) Config.fogDistance * 16);
    }

    @ModifyReturnValue(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At("RETURN"))
    public Vector4f applyFog(Vector4f original) {
        if (!Config.modEnabled || this.currentFogColor == null) {
            return original;
        }
        return this.currentFogColor;
    }
}
