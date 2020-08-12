package me.hsgamer.topin.getter.placeholderapi;

import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;

public final class PlaceholderAPIGetter extends Getter {

  private Expansion expansion;

  @Override
  public boolean register() {
    if (!Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      return false;
    }

    expansion = new Expansion();
    expansion.register();

    return true;
  }

  @Override
  public void unregister() {
    if (expansion != null) {
      expansion.unregister();
    }
  }

  @Override
  public String getName() {
    return "PlaceholderAPI";
  }
}
