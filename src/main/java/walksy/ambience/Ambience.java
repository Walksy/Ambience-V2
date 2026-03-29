package walksy.ambience;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public class Ambience implements ModInitializer {

    @Override
    public void onInitialize() {

    }

    /**
     * Forces loaded chunks to be rebuilt, along with cached color data in
     * {@link ClientWorld#colorCache}
     */
    public static void reloadWorld() {
        WorldRenderer worldRenderer = MinecraftClient.getInstance().worldRenderer;
        if (worldRenderer != null) {
            worldRenderer.reload();
        }
    }

    public static boolean checkDimension(RegistryKey<World> dimension) {
        ClientWorld world = MinecraftClient.getInstance().world;
        //if the world is null the player is likely on the title screen
        return world == null || world.getRegistryKey().equals(dimension);
    }
}
