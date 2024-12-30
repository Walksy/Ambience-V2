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
import dev.isxander.yacl3.gui.controllers.slider.IntegerSliderController;
import dev.isxander.yacl3.gui.controllers.string.number.IntegerFieldController;
import me.flashyreese.mods.sodiumextra.client.SodiumExtraClientMod;
import me.flashyreese.mods.sodiumextra.client.gui.SodiumExtraGameOptionPages;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.*;

public class ConfigIntegration {

    public static final ConfigClassHandler<ConfigIntegration> CONFIG = ConfigClassHandler.createBuilder(ConfigIntegration.class)
        .serializer(config -> GsonConfigSerializerBuilder.create(config)
            .setPath(FabricLoader.getInstance().getConfigDir().resolve("walksyambienceV2.json"))
            .build())
        .build();

    /**
     * General Settings Entries
     */
    @SerialEntry
    public boolean modEnabled = true;
    @SerialEntry
    public boolean overrideTime = false;
    @SerialEntry
    public int overridedTime = 18000;
    /**
     * Sky Settings Entries
     */
    @SerialEntry
    public boolean overworldSkyEnabled = false;
    @SerialEntry
    public boolean overworldSkyGradientEnabled = false;
    @SerialEntry
    public Color overworldSkyColor = Color.WHITE;
    @SerialEntry
    public Color overworldSkyGradientColor = Color.WHITE;
    @SerialEntry
    public int overworldSkyGradientHeight = 10;
    @SerialEntry
    public int overworldSkyGradientBrightness = 16;
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
    public boolean grassBlockEnabled = false;
    @SerialEntry
    public Color grassBlockColor = Color.WHITE;
    @SerialEntry
    public boolean shortGrassEnabled = false;
    @SerialEntry
    public Color shortGrassColor = Color.WHITE;
    @SerialEntry
    public boolean tallGrassEnabled = false;
    @SerialEntry
    public Color tallGrassColor = Color.WHITE;
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
     * Fire Settings Entries
     */
    @SerialEntry
    public boolean fireEnabled = false;
    @SerialEntry
    public Color fireColor = Color.WHITE;

    /**
     * Foliage Settings Entries
     */
    @SerialEntry
    public boolean leavesEnabled = false;
    @SerialEntry
    public Color leavesColor = Color.WHITE;

    @SuppressWarnings("deprecation")
    public static Screen createConfigScreen(Screen parent)
    {
        return YetAnotherConfigLib.create(CONFIG, ((defaults, config, builder) -> builder
            .title(Text.literal("Ambience Config Screen"))
                .save(ConfigIntegration::runnableSave)
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
                    .name(Text.literal("Override Time"))
                    .description(OptionDescription.of(Text.literal("")))
                    .binding(defaults.overrideTime, () -> config.overrideTime, newVal -> config.overrideTime = newVal)
                    .controller(BooleanControllerBuilder::create)
                    .build())
                    .option(Option.<Integer>createBuilder()
                            .name(Text.literal("Time"))
                            .description(OptionDescription.of(Text.literal("Override the time")))
                            .binding(defaults.overridedTime, () -> config.overridedTime, value -> config.overridedTime = value)
                            .customController(opt -> new IntegerFieldController(opt, 0, 18000))
                            .build())
                .build())
            /**
             * Color Settings Category
             */
            .category(ConfigCategory.createBuilder()
                .name(Text.literal("Sky"))
                .group(OptionGroup.createBuilder()
                    .name(Text.literal("Toggle Options"))
                    .description(OptionDescription.of(Text.literal("All the settings for the overworld sky")))
                        //Overworld
                    .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Override Overworld Sky"))
                        .description(OptionDescription.of(Text.literal("Should a custom overworld sky color be used")))
                        .binding(defaults.overworldSkyEnabled, () -> config.overworldSkyEnabled, newVal -> config.overworldSkyEnabled = newVal)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Overworld Sky Gradient"))
                                .description(OptionDescription.of(Text.literal("Should a custom overworld sky gradient color be used")))
                                .binding(defaults.overworldSkyGradientEnabled, () -> config.overworldSkyGradientEnabled, newVal -> config.overworldSkyGradientEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Override Nether Sky"))
                                .description(OptionDescription.of(Text.literal("Should a custom nether sky color be used")))
                                .binding(defaults.netherSkyEnabled, () -> config.netherSkyEnabled, newVal -> config.netherSkyEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.createBuilder(boolean.class)
                                .name(Text.literal("Override End Sky"))
                                .description(OptionDescription.of(Text.literal("Should a custom end sky color be used")))
                                .binding(defaults.endSkyEnabled, () -> config.endSkyEnabled, newVal -> config.endSkyEnabled = newVal)
                                .controller(BooleanControllerBuilder::create)
                                .build())

                        .build())
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Color Options"))
                    .option(Option.<Color>createBuilder()
                            .name(Text.literal("Overworld Sky Color"))
                            .description(OptionDescription.of(Text.literal("Color of the overworld sky")))
                            .binding(defaults.overworldSkyColor, () -> config.overworldSkyColor, value -> config.overworldSkyColor = value)
                            .customController(opt -> new ColorController(opt, false))
                            .build())
                            .option(Option.<Color>createBuilder()
                                    .name(Text.literal("Overworld Sky Gradient Color"))
                                    .description(OptionDescription.of(Text.literal("Color of the overworld sky gradient")))
                                    .binding(defaults.overworldSkyGradientColor, () -> config.overworldSkyGradientColor, value -> config.overworldSkyGradientColor = value)
                                    .customController(opt -> new ColorController(opt, false))
                                    .build())
                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("Nether Sky Color"))
                                .description(OptionDescription.of(Text.literal("Color of the nether sky")))
                                .binding(defaults.netherSkyColor, () -> config.netherSkyColor, value -> config.netherSkyColor = value)
                                .customController(opt -> new ColorController(opt, false))
                                .build())
                        //End
                        .option(Option.<Color>createBuilder()
                                .name(Text.literal("End Sky Color"))
                                .description(OptionDescription.of(Text.literal("Color of the end sky")))
                                .binding(defaults.endSkyColor, () -> config.endSkyColor, value -> config.endSkyColor = value)
                                .customController(opt -> new ColorController(opt, false))
                                .build())
                        .build())
                    .group(OptionGroup.createBuilder()
                            .name(Text.literal("Dev Extras (Proceed with caution)"))
                            .option(Option.<Integer>createBuilder()
                                    .name(Text.literal("Sky Gradient Height Overrider"))
                                    .description(OptionDescription.of(Text.literal("Override the height of the sky gradient")))
                                    .binding(defaults.overworldSkyGradientHeight, () -> config.overworldSkyGradientHeight, value -> config.overworldSkyGradientHeight = value)
                                    .customController(opt -> new IntegerSliderController(opt, 0, 50, 1))
                                    .build())
                            .option(Option.<Integer>createBuilder()
                                    .name(Text.literal("Sky Gradient Iterative Value Overrider (Brightness)"))
                                    .description(OptionDescription.of(Text.literal("Override the brightness of the sky gradient")))
                                    .binding(defaults.overworldSkyGradientBrightness, () -> config.overworldSkyGradientBrightness, value -> config.overworldSkyGradientBrightness = value)
                                    .customController(opt -> new IntegerSliderController(opt, 0, 160, 16))
                                    .build())
                            .build())
                .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Clouds"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Toggle Options"))
                                .description(OptionDescription.of(Text.literal("All the toggleable settings for the clouds")))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Cloud Color"))
                                        .description(OptionDescription.of(Text.literal("Should custom clouds color be used")))
                                        .binding(defaults.cloudEnabled, () -> config.cloudEnabled, newVal -> config.cloudEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Color Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Cloud Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the clouds")))
                                        .binding(defaults.cloudColor, () -> config.cloudColor, value -> config.cloudColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Water"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Toggle Options"))
                                .description(OptionDescription.of(Text.literal("All the toggleable options settings for the water")))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Custom Water Enabled"))
                                        .description(OptionDescription.of(Text.literal("Should a custom water color be used")))
                                        .binding(defaults.waterEnabled, () -> config.waterEnabled, newVal -> config.waterEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Color Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Water Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the water")))
                                        .binding(defaults.waterColor, () -> config.waterColor, value -> config.waterColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())

                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Lava"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Toggle Options"))
                                .description(OptionDescription.of(Text.literal("All the settings for the lava")))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Custom Lava Enabled"))
                                        .description(OptionDescription.of(Text.literal("Should custom lava color be used")))
                                        .binding(defaults.lavaEnabled, () -> config.lavaEnabled, newVal ->
                                        {
                                            if (newVal)
                                            {
                                                if (isSodiumExtraLoaded()) {
                                                    //Ensures sodium extra ticks the lava sprites
                                                    SodiumExtraClientMod.options().animationSettings.lava = true;
                                                }
                                            }
                                            //Reload textures to apply/remove the desaturated lava sprites
                                            MinecraftClient.getInstance().reloadResources();
                                            config.lavaEnabled = newVal;
                                        })
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Color Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Lava Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the lava")))
                                        .binding(defaults.lavaColor, () -> config.lavaColor, value -> config.lavaColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Fire"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Toggle Options"))
                                .description(OptionDescription.of(Text.literal("All the toggleable options for the fire")))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Fire Color"))
                                        .description(OptionDescription.of(Text.literal("Should custom lava color be used")))
                                        .binding(defaults.fireEnabled, () -> config.fireEnabled, newVal ->
                                        {
                                            if (newVal)
                                            {
                                                if (isSodiumExtraLoaded()) {
                                                    //Ensures sodium extra ticks the lava sprites
                                                    SodiumExtraClientMod.options().animationSettings.fire = true;
                                                }
                                            }
                                            //Reload textures to apply/remove the desaturated fire sprites
                                            MinecraftClient.getInstance().reloadResources();
                                            config.fireEnabled = newVal;
                                        })
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Color Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Fire Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the fire")))
                                        .binding(defaults.fireColor, () -> config.fireColor, value -> config.fireColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())
                .category(ConfigCategory.createBuilder()
                        .name(Text.literal("Foliage"))
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Toggle Options"))
                                .description(OptionDescription.of(Text.literal("All the settings for the foliage")))
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Leave Color"))
                                        .description(OptionDescription.of(Text.literal("Should a custom color be used for leaves")))
                                        .binding(defaults.leavesEnabled, () -> config.leavesEnabled, newVal -> config.leavesEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Grass Block Color"))
                                        .description(OptionDescription.of(Text.literal("Should custom grass color be used")))
                                        .binding(defaults.grassBlockEnabled, () -> config.grassBlockEnabled, newVal -> config.grassBlockEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Short Grass Color"))
                                        .description(OptionDescription.of(Text.literal("Should custom grass color be used")))
                                        .binding(defaults.shortGrassEnabled, () -> config.shortGrassEnabled, newVal -> config.shortGrassEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .option(Option.createBuilder(boolean.class)
                                        .name(Text.literal("Override Tall Grass Color"))
                                        .description(OptionDescription.of(Text.literal("Should custom grass color be used")))
                                        .binding(defaults.tallGrassEnabled, () -> config.tallGrassEnabled, newVal -> config.tallGrassEnabled = newVal)
                                        .controller(BooleanControllerBuilder::create)
                                        .build())
                                .build())
                        .group(OptionGroup.createBuilder()
                                .name(Text.literal("Color Options"))
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Leave Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the leaves")))
                                        .binding(defaults.leavesColor, () -> config.leavesColor, value -> config.leavesColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Grass Block Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the grass")))
                                        .binding(defaults.grassBlockColor, () -> config.grassBlockColor, value -> config.grassBlockColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Short Grass Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the grass")))
                                        .binding(defaults.shortGrassColor, () -> config.shortGrassColor, value -> config.shortGrassColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .option(Option.<Color>createBuilder()
                                        .name(Text.literal("Tall Grass Color"))
                                        .description(OptionDescription.of(Text.literal("Color of the grass")))
                                        .binding(defaults.tallGrassColor, () -> config.tallGrassColor, value -> config.tallGrassColor = value)
                                        .customController(opt -> new ColorController(opt, false))
                                        .build())
                                .build())
                        .build())
        )).generateScreen(parent);
    }

    private static void runnableSave()
    {
        CONFIG.save();
        MinecraftClient.getInstance().worldRenderer.reload();
    }

    private static boolean isSodiumExtraLoaded()
    {
        return FabricLoader.getInstance().isModLoaded("sodium-extra");
    }
}
