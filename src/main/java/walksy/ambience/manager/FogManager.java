package walksy.ambience.manager;

import net.minecraft.client.render.*;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

public class FogManager {

    public static FogManager INSTANCE = new FogManager();

    public void overrideFog(BackgroundRenderer.FogType fogType, CallbackInfoReturnable<Fog> cir)
    {
        if (!fogType.equals(BackgroundRenderer.FogType.FOG_SKY)) return;
        Fog original = cir.getReturnValue();
        Color newColor = ConfigIntegration.CONFIG.instance().overworldSkyGradientColor;
        float red = newColor.getRed() / 255.0f;
        float green = newColor.getGreen() / 255.0f;
        float blue = newColor.getBlue() / 255.0f;
        Vector4f c = new Vector4f(red, green, blue, original.alpha());
        cir.setReturnValue(new Fog(original.start(), original.end(), original.shape(), c.x, c.y, c.z, c.w));
    }

    public void overrideRenderSystemFog(Args args)
    {
        Color newColor = ConfigIntegration.CONFIG.instance().overworldSkyGradientColor;
        float red = newColor.getRed() / 255.0f;
        float green = newColor.getGreen() / 255.0f;
        float blue = newColor.getBlue() / 255.0f;
        args.set(0, red);
        args.set(1, green);
        args.set(2, blue);
    }



    /*
    public void renderSky()
    {
        Tessellator tessellator = Tessellator.getInstance();
        this.renderSkyGradient(new MatrixStack(), tessellator);
    }

    private void renderSkyGradient(MatrixStack matrices, Tessellator tesselator)
    {
        int color = ConfigIntegration.CONFIG.instance().overworldSkyGradientColor.getRGB();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        matrices.push();
        matrices.translate(0.0, ConfigIntegration.CONFIG.instance().overworldSkyGradientHeight, 0.0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(108F));
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        BufferBuilder bufferBuilder = tesselator.begin(VertexFormat.DrawMode.TRIANGLE_FAN, VertexFormats.POSITION_COLOR);
        float g = ColorHelper.floatFromChannel(ColorHelper.getAlpha(color));
        bufferBuilder.vertex(matrix4f, 0.0F, 100.0F, 0.0F).color(color);
        int i = ColorHelper.zeroAlpha(color);

        for (int k = 0; k <= ConfigIntegration.CONFIG.instance().overworldSkyGradientBrightness; k++) {
            float h = (float)k * (float) (Math.PI * 2) / 16.0F;
            float l = MathHelper.sin(h);
            float m = MathHelper.cos(h);
            bufferBuilder.vertex(matrix4f, l * 120.0F, m * 120.0F, -m * 40.0F * g).color(i);
        }

        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
        matrices.pop();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
    }
     */
}
