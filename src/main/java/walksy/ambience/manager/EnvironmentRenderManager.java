package walksy.ambience.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

public class EnvironmentRenderManager {

    public static EnvironmentRenderManager INSTANCE = new EnvironmentRenderManager();

    public void renderSky(BufferBuilderStorage bufferBuilders)
    {
        VertexConsumerProvider.Immediate immediate = bufferBuilders.getEntityVertexConsumers();
        this.renderSkyGradient(new MatrixStack(), immediate);
    }

    private void renderSkyGradient(MatrixStack matrices, VertexConsumerProvider.Immediate vertexConsumers)
    {
        int color = ConfigIntegration.CONFIG.instance().overworldSkyGradientColor.getRGB();
        matrices.push();
        matrices.translate(0.0, ConfigIntegration.CONFIG.instance().overworldSkyGradientHeight, 0.0);
        matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(108F));
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getSunriseSunset());
        float g = ColorHelper.getAlphaFloat(color);
        vertexConsumer.vertex(matrix4f, 0.0F, 100F, 0.0F).color(color);
        int i = ColorHelper.zeroAlpha(color);

        for (int k = 0; k <= ConfigIntegration.CONFIG.instance().overworldSkyGradientBrightness; k++) {
            float h = (float)k * (float) (Math.PI * 2) / 16.0F;
            float l = MathHelper.sin(h);
            float m = MathHelper.cos(h);
            vertexConsumer.vertex(matrix4f, l * 120.0F, m * 120.0F, -m * 40.0F * g).color(i);
        }

        matrices.pop();
    }
}
