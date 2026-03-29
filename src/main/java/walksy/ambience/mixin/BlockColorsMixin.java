package walksy.ambience.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.block.enums.DoubleBlockHalf;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.GrassColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.Config;

import java.util.ArrayList;
import java.util.List;

@Mixin(BlockColors.class)
public class BlockColorsMixin {

    @Inject(method = "create", at = @At("RETURN"))
    private static void create(CallbackInfoReturnable<BlockColors> cir) {
        BlockColors instance = cir.getReturnValue();

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.grassBlockColorEnabled) {
                return Config.grassBlockColor.getRGB();
            }
            return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
        }, Blocks.GRASS_BLOCK);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.grassColorEnabled) {
                return Config.grassColor.getRGB();
            }
            return world != null && pos != null
                ? BiomeColors.getGrassColor(world, state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos)
                : GrassColors.getDefaultColor();
        }, Blocks.TALL_GRASS);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.grassColorEnabled) {
                return Config.grassColor.getRGB();
            }
            return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
        }, Blocks.SHORT_GRASS);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.fernColorEnabled) {
                return Config.fernColor.getRGB();
            }
            return world != null && pos != null
                ? BiomeColors.getGrassColor(world, state.get(Properties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER ? pos.down() : pos)
                : GrassColors.getDefaultColor();
        }, Blocks.LARGE_FERN);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.fernColorEnabled) {
                return Config.fernColor.getRGB();
            }
            return world != null && pos != null ? BiomeColors.getGrassColor(world, pos) : GrassColors.getDefaultColor();
        }, Blocks.FERN);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.vineColorEnabled) {
                return Config.vineColor.getRGB();
            }
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : -12012264;
        }, Blocks.VINE);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.lilyPadColorEnabled) {
                return Config.lilyPadColor.getRGB();
            }
            return world != null && pos != null ? -14647248 : -9321636;
        }, Blocks.LILY_PAD);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.seagrassColorEnabled) {
                return Config.seagrassColor.getRGB();
            }
            return -1;
        }, Blocks.SEAGRASS, Blocks.TALL_SEAGRASS);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (Config.modEnabled && Config.leafColorEnabled) {
                return Config.leafColor.getRGB();
            }
            return world != null && pos != null ? BiomeColors.getFoliageColor(world, pos) : -12012264;
        }, getLeaves());
    }

    @Unique
    private static Block[] getLeaves() {
        List<Block> leavesBlocks = new ArrayList<>();
        for (Identifier id : Registries.BLOCK.getIds()) {
            Block block = Registries.BLOCK.get(id);
            if (block instanceof LeavesBlock) {
                leavesBlocks.add(block);
            }
        }
        return leavesBlocks.toArray(new Block[0]);
    }
}
