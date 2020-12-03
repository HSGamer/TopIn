package me.hsgamer.topin.config.path;

import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.AdvancedConfigPath;
import me.hsgamer.hscore.config.Config;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StringListConfigPath extends AdvancedConfigPath<Object, List<String>> {

    /**
     * Create a config path
     *
     * @param path the path to the value
     * @param def  the default value if it's not found
     */
    public StringListConfigPath(String path, List<String> def) {
        super(path, def);
    }

    @Override
    public @Nullable Object getFromConfig(@NotNull Config config) {
        return config.getConfig().get(getPath());
    }

    @Override
    public @Nullable List<String> convert(@NotNull Object rawValue) {
        return CollectionUtils.createStringListFromObject(rawValue, false);
    }

    @Override
    public @Nullable Object convertToRaw(@NotNull List<String> value) {
        return value;
    }
}
