package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SuffixConfig extends PluginConfig {

    public SuffixConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "suffix.yml"));
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
