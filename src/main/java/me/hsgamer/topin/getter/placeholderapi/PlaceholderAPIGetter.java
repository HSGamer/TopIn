package me.hsgamer.topin.getter.placeholderapi;

import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;

public final class PlaceholderAPIGetter implements Getter {

    private final Expansion expansion = new Expansion();

    @Override
    public boolean canRegister() {
        return Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    @Override
    public void register() {
        expansion.register();
    }

    @Override
    public void unregister() {
        expansion.unregister();
    }

    @Override
    public String getName() {
        return "PlaceholderAPI";
    }
}
