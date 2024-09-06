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
        cir.setReturnValue(waterColor.getRGB()); //might need to be packed (.getPacked())
    }

    public void setSkyColor(CallbackInfoReturnable<Vec3d> cir)
    {
        if (this.getSkyColor() == null) return;
        Color c = this.getSkyColor();
        Vec3d rtrn = new Vec3d(c.getRed() / 255F, c.getGreen() / 255F, c.getBlue() / 255F);
        cir.setReturnValue(rtrn);
        cir.cancel();
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
        cir.setReturnValue(grassColor.getRGB()); //might need to be packed (.getPacked())
    }

    public int getFoliageColor(int defaultz)
    {
        if (ConfigIntegration.CONFIG.instance().modEnabled && ConfigIntegration.CONFIG.instance().foliageEnabled)
        {
            return ConfigIntegration.CONFIG.instance().foliageColor.getRGB();
        }
        return defaultz;
    }

    private Color getSkyColor()
    {
        boolean overworld = ConfigIntegration.CONFIG.instance().overworldSkyEnabled;
        boolean end = ConfigIntegration.CONFIG.instance().endSkyEnabled;
        boolean nether = ConfigIntegration.CONFIG.instance().netherSkyEnabled;
        return switch (this.getPlayerDimension()) {
            case 2 -> overworld ? ConfigIntegration.CONFIG.instance().overworldSkyColor : null;

            case 1 -> end ? ConfigIntegration.CONFIG.instance().endSkyColor : null;

            case 0 -> nether ? ConfigIntegration.CONFIG.instance().netherSkyColor : null;
            default -> null;
        };
    }

    private int getColorPacked(Color color)
    {
        return -1;
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
