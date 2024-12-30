package walksy.ambience.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.LeavesBlock;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import walksy.ambience.manager.EnvironmentColorOverrider;

import java.util.ArrayList;
import java.util.List;


@Mixin(BlockColors.class)
public class BlockColorsMixin {


    @Inject(method = "create", at = @At("RETURN"))
    private static void injectFoliageColor(CallbackInfoReturnable<BlockColors> cir) {
        BlockColors instance = cir.getReturnValue();
        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return EnvironmentColorOverrider.BlockOverrider.getGrassColor(Blocks.GRASS_BLOCK, world, pos);
            } else {
                return -1;
            }
        }, Blocks.GRASS_BLOCK);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return EnvironmentColorOverrider.BlockOverrider.getGrassColor(Blocks.SHORT_GRASS, world, pos);
            } else {
                return -1;
            }
        }, Blocks.SHORT_GRASS);

        instance.registerColorProvider((state, world, pos, tintIndex) -> {
            if (world != null && pos != null) {
                return EnvironmentColorOverrider.BlockOverrider.getGrassColor(Blocks.TALL_GRASS, world, pos);
            } else {
                return -1;
            }
        }, Blocks.TALL_GRASS);
    }

    @Unique
    private static Block[] getAllLeaves() {
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
