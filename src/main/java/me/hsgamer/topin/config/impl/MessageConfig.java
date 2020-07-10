package me.hsgamer.topin.config.impl;

import me.hsgamer.topin.config.ConfigPath;
import me.hsgamer.topin.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageConfig extends PluginConfig {

  public static final ConfigPath<String> PREFIX = new ConfigPath<>(String.class, "prefix",
      "&f[&aTopIn&f] ");
  public static final ConfigPath<String> UPDATE = new ConfigPath<>(String.class, "update",
      "&eThe database has been saved");
  public static final ConfigPath<String> SUCCESS = new ConfigPath<>(String.class, "success",
      "&aSuccess");
  public static final ConfigPath<String> NO_PERMISSION = new ConfigPath<>(String.class,
      "no-permission", "&cYou don't have permission to do this");
  public static final ConfigPath<String> PLAYER_ONLY = new ConfigPath<>(String.class, "player-only",
      "&cYou should be a player to do this");

  public MessageConfig(JavaPlugin plugin) {
    super(plugin, "messages.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    PREFIX.setConfig(this);
    UPDATE.setConfig(this);
    SUCCESS.setConfig(this);
    NO_PERMISSION.setConfig(this);
    PLAYER_ONLY.setConfig(this);
  }
}
