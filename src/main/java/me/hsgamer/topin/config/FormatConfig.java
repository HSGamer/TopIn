package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class FormatConfig extends BukkitConfig {

    public FormatConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "format.yml"));
    }
}
