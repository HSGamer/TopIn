package me.hsgamer.topin.config.path;

import java.util.List;
import me.hsgamer.hscore.bukkit.config.ConfigPath;
import me.hsgamer.hscore.common.CommonUtils;

public class StringListConfigPath extends ConfigPath<List<String>> {

  /**
   * Create a config path
   *
   * @param path the path to the value
   * @param def  the default value if it's not found
   */
  public StringListConfigPath(String path, List<String> def) {
    super(path, def, o -> CommonUtils.createStringListFromObject(o, true));
  }
}
