package walksy.ambience.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.config.ConfigEntry;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.gui.YACLScreen;
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
    public boolean skyEnabled = false;
    @SerialEntry
    public Color skyColor = Color.WHITE;

    /**
     * Water Settings Entries
     */
    @SerialEntry
    public boolean waterEnabled = false;
    @SerialEntry
    public Color waterColor = Color.WHITE;

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
                    .name(Text.literal("Sky Settings"))
                    .description(OptionDescription.of(Text.literal("All the settings for the sky")))
                    .option(Option.createBuilder(boolean.class)
                        .name(Text.literal("Custom Sky Enabled"))
                        .description(OptionDescription.of(Text.literal("Should a custom sky color be used")))
                        .binding(defaults.skyEnabled, () -> config.skyEnabled, newVal -> config.skyEnabled = newVal)
                        .controller(BooleanControllerBuilder::create)
                        .build())
                    .build())
                .build())
        )).generateScreen(parent);
    }
}
