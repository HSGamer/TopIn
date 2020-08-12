package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.StringConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

public final class MessageConfig extends PluginConfig {

  public static final StringConfigPath PREFIX = new StringConfigPath("prefix", "&f[&aTopIn&f] ");
  public static final StringConfigPath SAVE = new StringConfigPath("save",
      "&eThe database has been saved");
  public static final StringConfigPath SUCCESS = new StringConfigPath("success", "&aSuccess");
  public static final StringConfigPath NO_PERMISSION = new StringConfigPath("no-permission",
      "&cYou don't have permission to do this");
  public static final StringConfigPath PLAYER_ONLY = new StringConfigPath("player-only",
      "&cYou should be a player to do this");
  public static final StringConfigPath GETTER_REGISTERED = new StringConfigPath("getter-registered",
      "&aThe getter '<getter>' is successfully registered");
  public static final StringConfigPath GETTER_UNREGISTERED = new StringConfigPath(
      "getter-unregistered",
      "&aThe getter '<getter>' is successfully unregistered");
  public static final StringConfigPath NUMBER_REQUIRED = new StringConfigPath("number-required",
      "&cNumber is required");
  public static final StringConfigPath SKULL_REMOVED = new StringConfigPath("skull-removed",
      "&aThe skull is removed");
  public static final StringConfigPath SKULL_REQUIRED = new StringConfigPath("skull-required",
      "&cA skull is required");
  public static final StringConfigPath DATA_LIST_NOT_FOUND = new StringConfigPath(
      "data-list-not-found", "&cThe data list is not found");

  public MessageConfig(JavaPlugin plugin) {
    super(plugin, "messages.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    PREFIX.setConfig(this);
    SAVE.setConfig(this);
    SUCCESS.setConfig(this);
    NO_PERMISSION.setConfig(this);
    PLAYER_ONLY.setConfig(this);
    GETTER_REGISTERED.setConfig(this);
    GETTER_UNREGISTERED.setConfig(this);
    NUMBER_REQUIRED.setConfig(this);
    SKULL_REMOVED.setConfig(this);
    SKULL_REQUIRED.setConfig(this);
    DATA_LIST_NOT_FOUND.setConfig(this);
  }
}
