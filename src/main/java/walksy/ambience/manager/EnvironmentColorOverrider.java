package walksy.ambience.manager;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FireBlock;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockRenderView;
import walksy.ambience.config.ConfigIntegration;

public class EnvironmentColorOverrider {

    public static class SkyOverrider
    {

        public static boolean shouldOverrideSky() {
            return !getColor().equals(new Vec3d(0, 0, 0)) && ConfigIntegration.CONFIG.instance().modEnabled;
        }


        public static boolean shouldOverrideClouds()
        {
            return ConfigIntegration.CONFIG.instance().cloudEnabled && ConfigIntegration.CONFIG.instance().modEnabled;
        }


        public static Vec3d getCloudColor() {
            int rgb = ConfigIntegration.CONFIG.instance().cloudColor.getRGB();
            return new Vec3d(
                    ((rgb >> 16) & 0xFF) / 255.0,
                    ((rgb >> 8) & 0xFF) / 255.0,
                    (rgb & 0xFF) / 255.0
            );
        }

        public static Vec3d getColor() {
            boolean overworld = ConfigIntegration.CONFIG.instance().overworldSkyEnabled;
            boolean end = ConfigIntegration.CONFIG.instance().endSkyEnabled;
            boolean nether = ConfigIntegration.CONFIG.instance().netherSkyEnabled;

            int rgb = switch (getPlayerDimension()) {
                case 2 -> overworld ? ConfigIntegration.CONFIG.instance().overworldSkyColor.getRGB() : -1;
                case 1 -> end ? ConfigIntegration.CONFIG.instance().endSkyColor.getRGB() : -1;
                case 0 -> nether ? ConfigIntegration.CONFIG.instance().netherSkyColor.getRGB() : -1;
                default -> -1;
            };

            if (rgb == -1) return new Vec3d(0, 0, 0);

            return new Vec3d(
                    ((rgb >> 16) & 0xFF) / 255.0,
                    ((rgb >> 8) & 0xFF) / 255.0,
                    (rgb & 0xFF) / 255.0
            );
        }



        /**
         * 0 - Nether
         * 1 - End
         * 2 - Overworld
         */
        private static int getPlayerDimension()
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


    public static class FluidOverrider
    {
        public static int getColor()
        {
            return ConfigIntegration.CONFIG.instance().waterColor.getRGB();

        }
    }
}
