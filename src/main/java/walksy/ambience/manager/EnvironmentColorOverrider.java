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

    public static class BlockOverrider
    {

        public static int getGrassColor(Block grassType, BlockRenderView world, BlockPos pos) {
            if (grassType.equals(Blocks.TALL_GRASS)) {
                return ConfigIntegration.CONFIG.instance().tallGrassEnabled ? ConfigIntegration.CONFIG.instance().tallGrassColor.getRGB() : BiomeColors.getFoliageColor(world, pos);
            } else if (grassType.equals(Blocks.SHORT_GRASS)) {
                return ConfigIntegration.CONFIG.instance().shortGrassEnabled ? ConfigIntegration.CONFIG.instance().shortGrassColor.getRGB() : BiomeColors.getFoliageColor(world, pos);
            }
            return ConfigIntegration.CONFIG.instance().grassBlockEnabled ? ConfigIntegration.CONFIG.instance().grassBlockColor.getRGB() : BiomeColors.getFoliageColor(world, pos);
        }

        public static int getBlockColor(Block block)
        {
            if (block instanceof FireBlock)
            {
                return ConfigIntegration.CONFIG.instance().fireEnabled ? ConfigIntegration.CONFIG.instance().fireColor.getRGB() : -1;
            } else if (block instanceof LeavesBlock || block.equals(Blocks.AZALEA_LEAVES) || block.equals(Blocks.FLOWERING_AZALEA_LEAVES))
            {
                return ConfigIntegration.CONFIG.instance().leavesEnabled ? ConfigIntegration.CONFIG.instance().leavesColor.getRGB() : -1;
            }

            return -1;
        }
    }

    public static class FluidOverrider
    {

        public static boolean shouldOverride(Fluid fluid)
        {
            boolean bl = (fluid.getDefaultState().isIn(FluidTags.WATER) && ConfigIntegration.CONFIG.instance().waterEnabled)
                    || (fluid.getDefaultState().isIn(FluidTags.LAVA) && ConfigIntegration.CONFIG.instance().lavaEnabled);
            return ConfigIntegration.CONFIG.instance().modEnabled && bl;
        }

        public static int getColor(Fluid fluid)
        {
            return fluid.equals(Fluids.WATER)
                    ? ConfigIntegration.CONFIG.instance().waterColor.getRGB()
                    : ConfigIntegration.CONFIG.instance().lavaColor.getRGB();
        }
    }
}
