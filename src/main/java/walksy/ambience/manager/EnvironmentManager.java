package walksy.ambience.manager;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

public class EnvironmentManager {
    public static EnvironmentManager INSTANCE = new EnvironmentManager();

    public void setWaterColor(CallbackInfoReturnable<Integer> cir)
    {
        Color waterColor = ConfigIntegration.CONFIG.instance().waterColor;
        cir.setReturnValue(waterColor.getRGB());
    }

    public void setSkyColor(CallbackInfoReturnable<Vec3d> cir)
    {
        if (this.getPlayerDimension() == 2) {
            Color skyColor = ConfigIntegration.CONFIG.instance().skyColor;
            Vec3d newSkyColor = new Vec3d(skyColor.getRed() / 255f, skyColor.getGreen() / 255f, skyColor.getBlue() / 255f);
            cir.setReturnValue(newSkyColor);
        } else {

        }
    }

    public void setCloudColor(CallbackInfoReturnable<Vec3d> cir)
    {
        Color cloudColor = ConfigIntegration.CONFIG.instance().cloudColor;
        Vec3d newCloudColor = new Vec3d(cloudColor.getRed() / 255f, cloudColor.getGreen() / 255f, cloudColor.getBlue() / 255f);
        cir.setReturnValue(newCloudColor);
    }

    public void setLavaColor()
    {

    }

    public void setGrassColor(CallbackInfoReturnable<Integer> cir)
    {
        Color grassColor = ConfigIntegration.CONFIG.instance().grassColor;
        cir.setReturnValue(grassColor.getRGB());
    }

    /**
     * 0 - Nether
     * 1 - End
     * 2 - Overworld
     */
    private int getPlayerDimension()
    {
        MinecraftClient client = MinecraftClient.getInstance();
        return switch (client.world.getRegistryKey().getValue().getPath())
        {
            case "the_nether" -> 0;
            case "the_end" -> 1;
            default -> 2;
        };
    }
}
