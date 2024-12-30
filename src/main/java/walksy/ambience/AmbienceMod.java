package walksy.ambience;

import net.fabricmc.api.ModInitializer;
import walksy.ambience.config.ConfigIntegration;

public class AmbienceMod implements ModInitializer {

    @Override
    public void onInitialize()
    {
        ConfigIntegration.CONFIG.load();
    }
}
