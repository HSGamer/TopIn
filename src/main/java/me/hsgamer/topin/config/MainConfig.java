package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.BooleanConfigPath;
import me.hsgamer.hscore.bukkit.config.path.IntegerConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

public class MainConfig extends PluginConfig {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update.period", 600);
  public static final BooleanConfigPath UPDATE_ASYNC = new BooleanConfigPath("update.async", false);
  public static final BooleanConfigPath UPDATE_SILENT = new BooleanConfigPath("update.silent",
      false);

  public MainConfig(JavaPlugin plugin) {
    super(plugin, "config.yml");
    getConfig().options().copyDefaults(true);
    setDefaultPath();
    saveConfig();
  }

  private void setDefaultPath() {
    UPDATE_PERIOD.setConfig(this);
    UPDATE_ASYNC.setConfig(this);
    UPDATE_SILENT.setConfig(this);
  }
}
