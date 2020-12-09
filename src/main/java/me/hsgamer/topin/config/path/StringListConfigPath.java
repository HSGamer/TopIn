package me.hsgamer.topin.config.path;

import me.hsgamer.hscore.common.CollectionUtils;
import me.hsgamer.hscore.config.BaseConfigPath;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class StringListConfigPath extends BaseConfigPath<List<String>> {
    /**
     * Create a config path
     *
     * @param path          the path to the value
     * @param def           the default value if it's not found
     */
    public StringListConfigPath(@NotNull String path, @Nullable List<String> def) {
        super(path, def, o -> CollectionUtils.createStringListFromObject(o, false));
    }
}
