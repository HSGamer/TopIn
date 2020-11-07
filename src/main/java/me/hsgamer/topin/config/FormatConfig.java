package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FormatConfig extends PluginConfig {

    public FormatConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "format.yml"));
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
