package me.hsgamer.topin.getter.skull;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class SkullGetter implements Getter {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
  private final SkullCommand skullCommand = new SkullCommand(this);
  private final List<TopSkull> topSkullList = new ArrayList<>();
  private final SkullBreakListener listener = new SkullBreakListener(this);
  private PluginConfig skullConfig;
  private BukkitTask updateTask;

  @Override
  public void register() {
    ConfigurationSerialization.registerClass(TopSkull.class);
    skullConfig = new PluginConfig(getInstance(), "skull.yml");
    skullConfig.getConfig().options().copyDefaults(true);
    UPDATE_PERIOD.setConfig(skullConfig);
    skullConfig.saveConfig();
    loadSkull();

    final BukkitRunnable updateRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        topSkullList.forEach(TopSkull::update);
      }
    };
    updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
    getInstance().getCommandManager().register(skullCommand);
    Bukkit.getPluginManager().registerEvents(listener, getInstance());
  }

  @Override
  public void unregister() {
    HandlerList.unregisterAll(listener);
    updateTask.cancel();
    saveSkull();
    ConfigurationSerialization.unregisterClass(TopSkull.class);
    getInstance().getCommandManager().unregister(skullCommand);
  }

  @SuppressWarnings("unchecked")
  private void loadSkull() {
    FileConfiguration config = skullConfig.getConfig();
    if (config.isSet("data")) {
      topSkullList.addAll((Collection<? extends TopSkull>) config.getList("data"));
    }
  }

  private void saveSkull() {
    FileConfiguration config = skullConfig.getConfig();
    config.set("data", topSkullList);
    skullConfig.saveConfig();
  }

  public void addSkull(TopSkull topSkull) {
    removeSkull(topSkull.getLocation());
    topSkullList.add(topSkull);
  }

  public void removeSkull(Location location) {
    topSkullList.removeIf(topSkull -> topSkull.getLocation().equals(location));
  }

  public boolean containsSkull(Location location) {
    return topSkullList.stream().anyMatch(topSkull -> topSkull.getLocation().equals(location));
  }

  @Override
  public String getName() {
    return "Skull";
  }
}
