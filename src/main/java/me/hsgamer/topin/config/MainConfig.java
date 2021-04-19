package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.ConfigPath;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.SerializableMapConfigPath;
import me.hsgamer.hscore.config.path.BooleanConfigPath;
import me.hsgamer.hscore.config.path.IntegerConfigPath;
import me.hsgamer.hscore.config.path.StringConfigPath;
import me.hsgamer.topin.config.path.StringListConfigPath;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MainConfig extends PathableConfig {

    public static final BooleanConfigPath METRICS = new BooleanConfigPath("metrics", true);
    public static final IntegerConfigPath SAVE_PERIOD = new IntegerConfigPath("save.period", 600);
    public static final BooleanConfigPath SAVE_ASYNC = new BooleanConfigPath("save.async", false);
    public static final BooleanConfigPath SAVE_SILENT = new BooleanConfigPath("save.silent", false);
    public static final BooleanConfigPath UPDATE_ASYNC = new BooleanConfigPath("update.async", false);
    public static final IntegerConfigPath DISPLAY_TOP_START_INDEX = new IntegerConfigPath("display-top-start-index", 1);
    public static final StringListConfigPath IGNORED_DATA_LIST = new StringListConfigPath("ignored-data-list", Collections.emptyList());
    public static final StringConfigPath STORAGE_TYPE = new StringConfigPath("storage-type", "JSON");
    public static final ConfigPath<Map<String, String>> PAPI_DATA_LIST = new SerializableMapConfigPath<Map<String, String>>(
            "papi-data-list", Collections.emptyMap()
    ) {
        @Override
        public @NotNull Map<String, String> convert(@NotNull Map<String, Object> rawValue) {
            Map<String, String> map = new HashMap<>();
            rawValue.forEach((k, v) -> map.put(k, String.valueOf(v)));
            return map;
        }

        @Override
        public @NotNull Map<String, Object> convertToRaw(@NotNull Map<String, String> value) {
            return new HashMap<>(value);
        }
    };

    public MainConfig(JavaPlugin plugin) {
        super(new BukkitConfig(plugin, "config.yml"));
    }
}
