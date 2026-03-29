package walksy.ambience.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.fog.AtmosphericFogModifier;
import net.minecraft.client.render.fog.FogData;
import net.minecraft.client.render.fog.FogModifier;
import net.minecraft.client.render.fog.FogRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.Config;

import java.util.List;

@Mixin(value = FogRenderer.class, priority = 1400)
public class FogRendererMixin {

    @Shadow
    @Final
    private static List<FogModifier> FOG_MODIFIERS;
    @Unique
    private FogModifier currentFogModifier = null;

    @Unique
    private Vector4f currentFogColor = null;

    @Redirect(method = "getFogColor", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/fog/FogModifier;getFogColor(Lnet/minecraft/client/world/ClientWorld;Lnet/minecraft/client/render/Camera;IF)I"))
    public int getFogColor(FogModifier instance, ClientWorld world, Camera camera, int viewDistance, float skyDarkness) {
        this.currentFogModifier = instance;
        return instance.getFogColor(world, camera, viewDistance, skyDarkness);
    }

    @Inject(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/CommandEncoder;mapBuffer(Lcom/mojang/blaze3d/buffers/GpuBuffer;ZZ)Lcom/mojang/blaze3d/buffers/GpuBuffer$MappedView;"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void applyFog(Camera camera, int viewDistance, RenderTickCounter renderTickCounter, float f, ClientWorld clientWorld, CallbackInfoReturnable<Vector4f> cir, float g, Vector4f vector4f, float h, CameraSubmersionType cameraSubmersionType, Entity entity, FogData fogData) {
        if (!Config.modEnabled || !Config.fogDistanceEnabled) {
            return;
        }

        for (FogModifier fogModifier : FOG_MODIFIERS) {
            if (fogModifier.shouldApply(cameraSubmersionType, entity)) {
                fogModifier.applyStartEndModifier(fogData, camera, clientWorld, (float) (Config.fogDistance * 16), renderTickCounter);
                break;
            }
        }
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

    @ModifyReturnValue(method = "applyFog(Lnet/minecraft/client/render/Camera;ILnet/minecraft/client/render/RenderTickCounter;FLnet/minecraft/client/world/ClientWorld;)Lorg/joml/Vector4f;", at = @At("RETURN"))
    public Vector4f applyFog(Vector4f original) {
        if (!Config.modEnabled || this.currentFogColor == null) {
            return original;
        }
        return this.currentFogColor;
    }
}
