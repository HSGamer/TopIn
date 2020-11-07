package me.hsgamer.topin.config.path;

import me.hsgamer.hscore.common.CommonUtils;
import me.hsgamer.hscore.config.BaseConfigPath;

import java.util.List;

public class StringListConfigPath extends BaseConfigPath<List<String>> {

    /**
     * Create a config path
     *
     * @param path the path to the value
     * @param def  the default value if it's not found
     */
    public StringListConfigPath(String path, List<String> def) {
        super(path, def, o -> CommonUtils.createStringListFromObject(o, false));
    }
}
