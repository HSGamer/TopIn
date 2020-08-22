package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class DisplayNameConfig extends PluginConfig {

  public DisplayNameConfig(JavaPlugin plugin) {
    super(plugin, "display_name.yml");
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
