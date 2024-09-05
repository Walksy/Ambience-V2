package walksy.ambience.manager;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.ConfigIntegration;

import java.awt.*;

public class EnvironmentManager {
    public static EnvironmentManager INSTANCE = new EnvironmentManager();

    public void setWaterColor(CallbackInfoReturnable<Integer> cir)
    {
        Color newWaterColor = ConfigIntegration.CONFIG.instance().waterColor;
        cir.setReturnValue(newWaterColor.getRGB());
    }

    public void setSkyColor()
    {

    }

    public void setLavaColor()
    {

    }

    public void setGrassColor()
    {

    }
}
