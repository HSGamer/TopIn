package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class FormatConfig extends PluginConfig {

  public FormatConfig(JavaPlugin plugin) {
    super(plugin, "format.yml");
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
