package walksy.ambience.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.YACLScreen;
import dev.isxander.yacl3.gui.controllers.ColorController;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class ConfigIntegration {

    public static final ConfigClassHandler<ConfigIntegration> CONFIG = ConfigClassHandler.createBuilder(ConfigIntegration.class)
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve("walksyambience.json"))
            .build())
        .build();

    /**
     * General Settings Entries
     */
    @SerialEntry
    public boolean modEnabled = true;
    @SerialEntry
    public boolean overrideTime = false;

    /**
     * Sky Settings Entries
     */
    @SerialEntry
    public boolean overworldSkyEnabled = false;
    @SerialEntry
    public Color overworldSkyColor = Color.WHITE;
    @SerialEntry
    public boolean netherSkyEnabled = false;
    @SerialEntry
    public Color netherSkyColor = Color.WHITE;
    @SerialEntry
    public boolean endSkyEnabled = false;
    @SerialEntry
    public Color endSkyColor = Color.WHITE;

    /**
     * Water Settings Entries
     */
    @SerialEntry
    public boolean waterEnabled = false;
    @SerialEntry
    public Color waterColor = Color.WHITE;

    /**
     * Grass Settings Entries
     */
    @SerialEntry
    public boolean grassEnabled = false;
    @SerialEntry
    public Color grassColor = Color.WHITE;

    /**
     * Cloud Settings Entries
     */
    @SerialEntry
    public boolean cloudEnabled = false;
    @SerialEntry
    public Color cloudColor = Color.WHITE;

    /**
     * Lava Settings Entries
     */
    @SerialEntry
    public boolean lavaEnabled = false;
    @SerialEntry
    public Color lavaColor = Color.WHITE;

    /**
     * Foliage Settings Entries
     */
    @SerialEntry
    public boolean foliageEnabled = false;
    @SerialEntry
    public Color foliageColor = Color.WHITE;

    @SuppressWarnings("deprecation")
    public static Screen createConfigScreen(Screen parent)
    {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
            .title(Text.literal("Ambience Config Screen"))
            /**
             * General Settings Category
             */
            .category(ConfigCategory.createBuilder()
                .name(Text.literal("General Settings"))
                .option(Option.createBuilder(boolean.class)
                    .name(Text.literal("Ambience Mod Enabled"))
                    .description(OptionDescription.of(Text.literal("Should the ambience mod be enabled")))
                    .binding(defaults.modEnabled, () -> config.modEnabled, newVal -> config.modEnabled = newVal)
                    .controller(BooleanControllerBuilder::create)
                    .build())
                .option(Option.createBuilder(boolean.class)
                    .name(Text.literal("Override To Midnight"))
                    .description(OptionDescription.of(Text.literal("Sets the client time to midnight, allows for more vibrant colors")))
                    .binding(defaults.overrideTime, () -> config.overrideTime, newVal -> config.overrideTime = newVal)
                    .controller(BooleanControllerBuilder::create)
                    .build())
                .build())
            /**
             * Color Settings Category
             */
            .category(ConfigCategory.createBuilder()
                .name(Text.literal("Color Settings"))
                .group(OptionGroup.createBuilder()
                        /**
                         * Sky Color - Overworld, Nether, End
                         */
                    .name(Text.literal("Sky"))
                    .description(OptionDescription.of(Text.literal("All the settings for the overworld sky")))
                        //Overworld
                    .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Custom Overworld Sky Enabled"))
                        .description(OptionDescription.of(Text.literal("Should a custom overworld sky color be used")))
                        .binding(defaults.overworldSkyEnabled, () -> config.overworldSkyEnabled, newVal -> config.overworldSkyEnabled = newVal)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                    .option(Option.<Color>createBuilder()
                            .name(Text.literal("Overworld Sky Color"))
                            .description(OptionDescription.of(Text.literal("Color of the overworld sky")))
                            .binding(defaults.overworldSkyColor, () -> config.overworldSkyColor, value -> config.overworldSkyColor = value)
                            .customController(opt -> new ColorController(opt, false))
                            .build())
                        //Nether
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Custom Nether Sky Enabled"))
                                .description(OptionDescription.of(Text.literal("Should a custom nether sky color be used")))
                                .binding(defaults.netherSkyEnabled, () -> config.netherSkyEnabled, newVal -> config.netherSkyEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("Nether Sky Color"))
                                .description(OptionDescription.of(Text.literal("Color of the nether sky")))
                                .binding(defaults.netherSkyColor, () -> config.netherSkyColor, value -> config.netherSkyColor = value)
                                .customController(opt -> new ColorController(opt, false))
                                .build())
                        //End
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Custom End Sky Enabled"))
                                .description(OptionDescription.of(Text.literal("Should a custom end sky color be used")))
                                .binding(defaults.endSkyEnabled, () -> config.endSkyEnabled, newVal -> config.endSkyEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("End Sky Color"))
                                .description(OptionDescription.of(Text.literal("Color of the end sky")))
                                .binding(defaults.endSkyColor, () -> config.endSkyColor, value -> config.endSkyColor = value)
                                .customController(opt -> new ColorController(opt, false))
                                .build())
                        .build())
                    /**
                     * Water Color
                     */
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Water"))
                            .description(OptionDescription.of(Text.literal("All the settings for the water")))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.literal("Custom Water Enabled"))
                                    .description(OptionDescription.of(Text.literal("Should a custom water color be used")))
                                    .binding(defaults.waterEnabled, () -> config.waterEnabled, newVal -> config.waterEnabled = newVal)
                                    .controller(BooleanControllerBuilder::create)
                                    .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Water Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the sky")))
                                    .binding(defaults.waterColor, () -> config.waterColor, value -> config.waterColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                            .build())
                    /**
                     * Lava Color
                     */
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Lava"))
                            .description(OptionDescription.of(Text.literal("All the settings for the lava")))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.literal("Custom Lava Enabled"))
                                    .description(OptionDescription.of(Text.literal("Should custom lava color be used")))
                                    .binding(defaults.lavaEnabled, () -> config.lavaEnabled, newVal -> config.lavaEnabled = newVal)
                                    .controller(BooleanControllerBuilder::create)
                                    .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Lava Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the lava")))
                                    .binding(defaults.lavaColor, () -> config.lavaColor, value -> config.lavaColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                            .build())
                    /**
                     * Cloud Color
                     */
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Clouds"))
                            .description(OptionDescription.of(Text.literal("All the settings for the clouds")))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.literal("Custom Clouds Enabled"))
                                    .description(OptionDescription.of(Text.literal("Should custom clouds color be used")))
                                    .binding(defaults.cloudEnabled, () -> config.cloudEnabled, newVal -> config.cloudEnabled = newVal)
                                    .controller(BooleanControllerBuilder::create)
                                    .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Cloud Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the clouds")))
                                    .binding(defaults.cloudColor, () -> config.cloudColor, value -> config.cloudColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                            .build())
                    /**
                     * Grass Color
                     */
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Grass"))
                            .description(OptionDescription.of(Text.literal("All the settings for the grass")))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.literal("Custom Grass Enabled"))
                                    .description(OptionDescription.of(Text.literal("Should custom grass color be used")))
                                    .binding(defaults.grassEnabled, () -> config.grassEnabled, newVal -> config.grassEnabled = newVal)
                                    .controller(BooleanControllerBuilder::create)
                                    .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Grass Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the grass")))
                                    .binding(defaults.grassColor, () -> config.grassColor, value -> config.grassColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                            .build())
                    /**
                     * Foliage Color
                     */
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Foliage"))
                            .description(OptionDescription.of(Text.literal("All the settings for the foliage")))
                            .option(Option.createBuilder(boolean.class)
                                    .name(Text.literal("Custom Foliage Enabled"))
                                    .description(OptionDescription.of(Text.literal("Should custom foliage color be used")))
                                    .binding(defaults.foliageEnabled, () -> config.foliageEnabled, newVal -> config.foliageEnabled = newVal)
                                    .controller(BooleanControllerBuilder::create)
                                    .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Foliage Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the foliage")))
                                    .binding(defaults.foliageColor, () -> config.foliageColor, value -> config.foliageColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                            .build())
                .build())
        )).generateScreen(parent);
    }
}
