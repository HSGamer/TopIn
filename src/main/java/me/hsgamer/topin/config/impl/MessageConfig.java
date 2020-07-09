package me.hsgamer.topin.config.impl;

import me.hsgamer.topin.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageConfig extends PluginConfig {

  public MessageConfig(JavaPlugin plugin) {
    super(plugin, "messages.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    // TODO
  }
}
