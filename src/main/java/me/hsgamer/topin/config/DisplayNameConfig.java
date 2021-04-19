package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DisplayNameConfig extends BukkitConfig {

    public DisplayNameConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "display_name.yml"));
    }
}
