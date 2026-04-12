package walksy.ambience;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class Ambience implements ModInitializer {

    @Override
    public void onInitialize() {

    }

    /**
     * Forces loaded chunks to be rebuilt, along with cached color data in
     * {@link net.minecraft.client.multiplayer.ClientLevel#tintCaches}
     */
    public static void reloadWorld() {
        LevelRenderer levelRenderer = Minecraft.getInstance().levelRenderer;
        if (levelRenderer != null) {
            levelRenderer.allChanged();
        }
    }

    public static boolean checkDimension(ResourceKey<Level> dimension) {
        ClientLevel level = Minecraft.getInstance().level;
        return level == null || level.dimension().equals(dimension);
    }
}
