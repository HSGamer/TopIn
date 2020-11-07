package me.hsgamer.topin.addon;

import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.config.Config;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class TopInAddon extends PluginAddon {
    @Override
    protected @NotNull Config createConfig() {
        return new PluginConfig(new File(getDataFolder(), "config.yml"));
    }
}
