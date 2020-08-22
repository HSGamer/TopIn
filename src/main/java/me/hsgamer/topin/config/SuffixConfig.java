package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public final class SuffixConfig extends PluginConfig {

  public SuffixConfig(JavaPlugin plugin) {
    super(plugin, "suffix.yml");
    getConfig().options().copyDefaults(true);
    saveConfig();
  }
}
