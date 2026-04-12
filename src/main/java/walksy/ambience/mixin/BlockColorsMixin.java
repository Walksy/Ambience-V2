package walksy.ambience.mixin;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.block.BlockTintSource;
import net.minecraft.client.color.block.BlockTintSources;
import net.minecraft.client.renderer.block.BlockAndTintGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.IntSupplier;

@Mixin(BlockColors.class)
public class BlockColorsMixin {

    @Inject(method = "createDefault", at = @At("RETURN"))
    private static void create(CallbackInfoReturnable<BlockColors> cir) {
        BlockColors instance = cir.getReturnValue();

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.grassBlockColorEnabled,
                        () -> Config.grassBlockColor.getRGB(), BlockTintSources.grassBlock())
        ), Blocks.GRASS_BLOCK);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.grassColorEnabled,
                        () -> Config.grassColor.getRGB(), BlockTintSources.doubleTallGrass())
        ), Blocks.TALL_GRASS, Blocks.LARGE_FERN);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.grassColorEnabled,
                        () -> Config.grassColor.getRGB(), BlockTintSources.grass())
        ), Blocks.SHORT_GRASS);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.fernColorEnabled,
                        () -> Config.fernColor.getRGB(), BlockTintSources.grass())
        ), Blocks.FERN);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.vineColorEnabled,
                        () -> Config.vineColor.getRGB(), BlockTintSources.foliage())
        ), Blocks.VINE);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.lilyPadColorEnabled,
                        () -> Config.lilyPadColor.getRGB(), BlockTintSources.constant(-9321636, -14647248))
        ), Blocks.LILY_PAD);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.seagrassColorEnabled,
                        () -> Config.seagrassColor.getRGB(), BlockTintSources.constant(-1))
        ), Blocks.SEAGRASS, Blocks.TALL_SEAGRASS);

        instance.register(List.of(
                configSource(() -> Config.modEnabled && Config.leafColorEnabled,
                        () -> Config.leafColor.getRGB(), BlockTintSources.foliage())
        ), getLeaves());
    }

    @Unique
    private static BlockTintSource configSource(BooleanSupplier condition, IntSupplier color, BlockTintSource fallback) {
        return new BlockTintSource() {
            @Override
            public int color(BlockState state) {
                return condition.getAsBoolean() ? color.getAsInt() : fallback.color(state);
            }

            @Override
            public int colorInWorld(BlockState state, BlockAndTintGetter level, BlockPos pos) {
                return condition.getAsBoolean() ? color.getAsInt() : fallback.colorInWorld(state, level, pos);
            }

            @Override
            public Set<Property<?>> relevantProperties() {
                return fallback.relevantProperties();
            }
        };
    }

    @Unique
    private static Block[] getLeaves() {
        List<Block> leaves = new ArrayList<>();
        for (Block block : BuiltInRegistries.BLOCK) {
            if (block instanceof LeavesBlock) leaves.add(block);
        }
        return leaves.toArray(new Block[0]);
    }
}