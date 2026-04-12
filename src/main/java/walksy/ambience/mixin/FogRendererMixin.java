package walksy.ambience.mixin;

import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.FogRenderer;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.material.FogType;
import org.joml.Vector4f;
import org.joml.Vector4fc;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import walksy.ambience.config.Config;

import java.util.List;

@Mixin(value = FogRenderer.class, priority = 1400)
public class FogRendererMixin {

    @Shadow
    @Final
    private static List<FogEnvironment> FOG_ENVIRONMENTS;

    @Unique
    private Vector4f currentFogColor = null;


    @Inject(method = "setupFog", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/fog/FogData;renderDistanceEnd:F", opcode = Opcodes.PUTFIELD), locals = LocalCapture.CAPTURE_FAILHARD)
    public void applyFog(Camera camera, int renderDistanceInChunks, DeltaTracker deltaTracker, float darkenWorldAmount, ClientLevel level, CallbackInfoReturnable<FogData> cir, float partialTickTime, float renderDistanceInBlocks, FogType fogType, Entity entity, FogData fog, float renderDistanceFogSpan) {
        if (!Config.modEnabled || !Config.fogDistanceEnabled) {
            return;
        }

        for (FogEnvironment fogModifier : FOG_ENVIRONMENTS) {
            if (fogModifier.isApplicable(fogType, entity)) {
                fogModifier.setupFog(fog, camera, level, (float) (Config.fogDistance * 16), deltaTracker);
                break;
            }
        }
    }

    @ModifyArg(method = "updateBuffer(Ljava/nio/ByteBuffer;ILorg/joml/Vector4f;FFFFFF)V", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/buffers/Std140Builder;putVec4(Lorg/joml/Vector4fc;)Lcom/mojang/blaze3d/buffers/Std140Builder;"))
    public Vector4fc applyFogToRenderPass(Vector4fc vec) {
        if (!Config.modEnabled) {
            return vec;
        }
        Vector4f fogColor = Config.getFogColor();
        if (fogColor != null) {
            this.currentFogColor = fogColor;
            return this.currentFogColor;
        }
        return vec;
    }

    @Inject(method = "computeFogColor", at = @At("TAIL"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void applyFog(Camera camera, float partialTicks, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f dest, CallbackInfo ci, FogType fogType, Entity entity, FogEnvironment colorSourceEnvironment, FogEnvironment darknessModifyingEnvironment, int color, float voidDarknessOnsetRange, float darkness, float fogRed, float fogGreen, float fogBlue, float brightenFactor) {
        if (!Config.modEnabled || this.currentFogColor == null || colorSourceEnvironment == null) {
            return;
        }

        dest.set(this.currentFogColor);
    }
}
