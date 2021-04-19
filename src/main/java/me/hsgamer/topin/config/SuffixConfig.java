package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SuffixConfig extends BukkitConfig {

    public SuffixConfig(JavaPlugin plugin) {
        super(new File(plugin.getDataFolder(), "suffix.yml"));
    }
}
