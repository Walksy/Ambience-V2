package walksy.ambience.config;

import main.walksy.lib.api.WalksyLibConfig;
import main.walksy.lib.core.config.impl.LocalConfig;
import main.walksy.lib.core.config.local.Category;
import main.walksy.lib.core.config.local.OptionDescription;
import main.walksy.lib.core.config.local.options.BooleanOption;
import main.walksy.lib.core.config.local.options.ColorOption;
import main.walksy.lib.core.config.local.options.NumericalOption;
import main.walksy.lib.core.config.local.options.groups.OptionGroup;
import main.walksy.lib.core.config.local.options.type.WalksyLibColor;
import main.walksy.lib.core.utils.PathUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.world.World;
import org.joml.Vector4f;
import walksy.ambience.Ambience;

public class Config implements WalksyLibConfig {

    /**
     * General
     */
    public static boolean modEnabled = true;
    public static boolean overrideTime = true;
    public static int overridedTime = 18000;

    /**
     * Environment
     */
    public static boolean skyColorEnabled = false;
    public static WalksyLibColor skyColor = new WalksyLibColor(255, 255, 255);
    public static boolean overWorldFogColorEnabled = false;
    public static WalksyLibColor overworldFogColor = new WalksyLibColor(255, 255, 255);
    public static boolean netherFogColorEnabled = false;
    public static WalksyLibColor netherFogColor = new WalksyLibColor(255, 255, 255);
    public static boolean endFogColorEnabled = false;
    public static WalksyLibColor endFogColor = new WalksyLibColor(255, 255, 255);
    public static boolean fogDistanceEnabled = true;
    public static double fogDistance = 33;

    public static boolean waterColorEnabled = false;
    public static WalksyLibColor waterColor = new WalksyLibColor(255, 255, 255);
    public static boolean lavaTintEnabled = false;
    public static WalksyLibColor lavaTint = new WalksyLibColor(255, 255, 255);

    public static boolean cloudColorEnabled = false;
    public static WalksyLibColor cloudColor = new WalksyLibColor(255, 255, 255);

    /**
     * Vegetation
     */
    public static boolean grassBlockColorEnabled = false;
    public static WalksyLibColor grassBlockColor = new WalksyLibColor(255, 255, 255);
    public static boolean grassColorEnabled = false;
    public static WalksyLibColor grassColor = new WalksyLibColor(255, 255, 255);
    public static boolean fernColorEnabled = false;
    public static WalksyLibColor fernColor = new WalksyLibColor(255, 255, 255);
    public static boolean vineColorEnabled = false;
    public static WalksyLibColor vineColor = new WalksyLibColor(255, 255, 255);
    public static boolean lilyPadColorEnabled = false;
    public static WalksyLibColor lilyPadColor = new WalksyLibColor(255, 255, 255);
    public static boolean seagrassColorEnabled = false;
    public static WalksyLibColor seagrassColor = new WalksyLibColor(255, 255, 255);
    public static boolean leafColorEnabled = false;
    public static WalksyLibColor leafColor = new WalksyLibColor(255, 255, 255);

    @Override
    public LocalConfig define() {
        return LocalConfig.createBuilder("Ambience")
            .path(PathUtils.ofConfigDir("ambience"))
            .onSave(() -> {
                if (modEnabled) { //no point in reloading the world if the mod isn't even enabled
                    Ambience.reloadWorld();
                }
            })
            .category(Category.createBuilder("General")
                .group(OptionGroup.createBuilder("Global")
                    .addOption(BooleanOption.createBuilder("Mod Enabled", () -> modEnabled, modEnabled, val -> modEnabled = val).build())
                    .build())
                .group(OptionGroup.createBuilder("World Time")
                    .addOption(BooleanOption.createBuilder("Override Time", () -> overrideTime, overrideTime, val -> overrideTime = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(NumericalOption.<Integer>createBuilder("Time Value", () -> overridedTime, overridedTime, val -> overridedTime = val)
                        .values(0, 24000, 100)
                        .availability(() -> modEnabled && overrideTime, "Enable 'Override Time' first")
                        .build())
                    .build())
                .build())
            .category(Category.createBuilder("Environment")
                .group(OptionGroup.createBuilder("Sky & Atmosphere")
                    .addOption(BooleanOption.createBuilder("Sky Color Enabled", () -> skyColorEnabled, skyColorEnabled, val -> skyColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Sky Color", () -> skyColor, skyColor, val -> skyColor = val)
                        .availability(() -> modEnabled && skyColorEnabled, "Enable 'Sky Color' first")
                        .build())
                    .build())
                .group(OptionGroup.createBuilder("Fog")
                    .addOption(BooleanOption.createBuilder("Overworld Fog Color Enabled", () -> overWorldFogColorEnabled, overWorldFogColorEnabled, val -> overWorldFogColorEnabled = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled, "")
                        .build())
                    .addOption(ColorOption.createBuilder("Overworld Fog Color", () -> overworldFogColor, overworldFogColor, val -> overworldFogColor = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled && overWorldFogColorEnabled, "Enable 'Overworld Fog Color Enabled' first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Nether Fog Color Enabled", () -> netherFogColorEnabled, netherFogColorEnabled, val -> netherFogColorEnabled = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled, "Ambience must be enabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Nether Fog Color", () -> netherFogColor, netherFogColor, val -> netherFogColor = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled && netherFogColorEnabled, "Enable 'Nether Fog Color Enabled' first")
                        .build())
                    .addOption(BooleanOption.createBuilder("End Fog Color Enabled", () -> endFogColorEnabled, endFogColorEnabled, val -> endFogColorEnabled = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled, "Ambience must be enabled")
                        .build())
                    .addOption(ColorOption.createBuilder("End Fog Color", () -> endFogColor, endFogColor, val -> endFogColor = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled && endFogColorEnabled, "Enable 'End Fog Color Enabled' first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Fog Distance Enabled", () -> fogDistanceEnabled, fogDistanceEnabled, val -> fogDistanceEnabled = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .availability(() -> modEnabled && Ambience.checkDimension(World.OVERWORLD), "Ambience must be enabled and be in the overworld")
                        .build())
                    .addOption(NumericalOption.createBuilder("Fog Distance", () -> fogDistance, fogDistance, val -> fogDistance = val)
                        .description(OptionDescription.ofOrderedString(() -> "Requires 'Global Fog' to be enabled via sodium"))
                        .values(0D, 33D, 1D)
                        .availability(() -> modEnabled && fogDistanceEnabled && Ambience.checkDimension(World.OVERWORLD), "Enable 'Fog Distance' and be in the overworld first")
                        .build())
                    .build())
                .group(OptionGroup.createBuilder("Fluid")
                    .addOption(BooleanOption.createBuilder("Water Color Enabled", () -> waterColorEnabled, waterColorEnabled, val -> waterColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Water Color", () -> waterColor, waterColor, val -> waterColor = val)
                        .availability(() -> modEnabled && waterColorEnabled, "Enable water toggle first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Lava Tint Enabled", () -> lavaTintEnabled, lavaTintEnabled, val -> lavaTintEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Lava Tint", () -> lavaTint, lavaTint, val -> lavaTint = val)
                        .availability(() -> modEnabled && lavaTintEnabled, "Enable lava toggle first")
                        .build())
                    .build())
                .group(OptionGroup.createBuilder("Clouds")
                    .addOption(BooleanOption.createBuilder("Cloud Color Enabled", () -> cloudColorEnabled, cloudColorEnabled, val -> cloudColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Cloud Color", () -> cloudColor, cloudColor, val -> cloudColor = val)
                        .availability(() -> modEnabled && cloudColorEnabled, "Enable cloud toggle first")
                        .build())
                    .build())
                .build())
            .category(Category.createBuilder("Vegetation")
                .group(OptionGroup.createBuilder("Grass Settings")
                    .addOption(BooleanOption.createBuilder("Grass Block Enabled", () -> grassBlockColorEnabled, grassBlockColorEnabled, val -> grassBlockColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Grass Block Color", () -> grassBlockColor, grassBlockColor, val -> grassBlockColor = val)
                        .availability(() -> modEnabled && grassBlockColorEnabled, "Enable grass block toggle first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Grass Color Enabled", () -> grassColorEnabled, grassColorEnabled, val -> grassColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Grass Color", () -> grassColor, grassColor, val -> grassColor = val)
                        .availability(() -> modEnabled && grassColorEnabled, "Enable grass toggle first")
                        .build())
                    .build())
                .group(OptionGroup.createBuilder("Leaf Settings")
                    .addOption(BooleanOption.createBuilder("Leaf Color Enabled", () -> leafColorEnabled, leafColorEnabled, val -> leafColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Leaf Color", () -> leafColor, leafColor, val -> leafColor = val)
                        .availability(() -> modEnabled && leafColorEnabled, "Enable leaf toggle first")
                        .build())
                    .build())
                .group(OptionGroup.createBuilder("Other Vegetation")
                    .addOption(BooleanOption.createBuilder("Fern Color Enabled", () -> fernColorEnabled, fernColorEnabled, val -> fernColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Fern Color", () -> fernColor, fernColor, val -> fernColor = val)
                        .availability(() -> modEnabled && fernColorEnabled, "Enable fern toggle first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Vine Color Enabled", () -> vineColorEnabled, vineColorEnabled, val -> vineColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Vine Color", () -> vineColor, vineColor, val -> vineColor = val)
                        .availability(() -> modEnabled && vineColorEnabled, "Enable vine toggle first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Lily Pad Color Enabled", () -> lilyPadColorEnabled, lilyPadColorEnabled, val -> lilyPadColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Lily Pad Color", () -> lilyPadColor, lilyPadColor, val -> lilyPadColor = val)
                        .availability(() -> modEnabled && lilyPadColorEnabled, "Enable lily pad toggle first")
                        .build())
                    .addOption(BooleanOption.createBuilder("Seagrass Color Enabled", () -> seagrassColorEnabled, seagrassColorEnabled, val -> seagrassColorEnabled = val)
                        .availability(() -> modEnabled, "Mod is disabled")
                        .build())
                    .addOption(ColorOption.createBuilder("Seagrass Color", () -> seagrassColor, seagrassColor, val -> seagrassColor = val)
                        .availability(() -> modEnabled && seagrassColorEnabled, "Enable seagrass toggle first")
                        .build())
                    .build())
                .build())
            .build();
    }

    /**
     * Returns the current sky color as a normalized RGBA vector.
     * <p>
     * {@link net.minecraft.client.render.fog.FogRenderer} uses {@link Vector4f} objects to handle color components
     *
     * @return a {@link Vector4f} containing the normalized sky color components
     */
    public static Vector4f getSkyColor() {
        float r = skyColor.getRed() / 255F;
        float g = skyColor.getGreen() / 255F;
        float b = skyColor.getBlue() / 255F;
        return new Vector4f(r, g, b, 1.0F);
    }

    /**
     * Returns the current fog color as a normalized RGBA vector based on the player's dimension.
     * <p>
     * {@link net.minecraft.client.render.fog.FogRenderer} uses {@link Vector4f} objects to handle color components
     *
     * @return a {@link Vector4f} containing the normalized fog color components, or null if disabled for the current dimension
     */
    public static Vector4f getFogColor() {
        WalksyLibColor color = overworldFogColor;

        if (MinecraftClient.getInstance() != null) {
            if (Ambience.checkDimension(World.NETHER)) {
                if (!netherFogColorEnabled) return null;
                color = netherFogColor;
            } else if (Ambience.checkDimension(World.END)) {
                if (!endFogColorEnabled) return null;
                color = endFogColor;
            } else {
                if (!overWorldFogColorEnabled) return null;
            }
        }

        float r = color.getRed() / 255F;
        float g = color.getGreen() / 255F;
        float b = color.getBlue() / 255F;
        return new Vector4f(r, g, b, 1.0F);
    }
}
