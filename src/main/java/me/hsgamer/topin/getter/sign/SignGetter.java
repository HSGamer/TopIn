package me.hsgamer.topin.getter.sign;

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

public final class SignGetter extends Getter {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
  private final SignCommand signCommand = new SignCommand(this);
  private final List<TopSign> topSignList = new ArrayList<>();
  private final SignBreakListener listener = new SignBreakListener(this);
  private PluginConfig signConfig;
  private BukkitTask updateTask;

  @Override
  public boolean register() {
    ConfigurationSerialization.registerClass(TopSign.class);
    signConfig = new PluginConfig(getInstance(), "sign.yml");
    signConfig.getConfig().options().copyDefaults(true);
    UPDATE_PERIOD.setConfig(signConfig);
    signConfig.saveConfig();
    loadSign();

    final BukkitRunnable updateRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        topSignList.forEach(TopSign::update);
      }
    };
    updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
    getInstance().getCommandManager().register(signCommand);
    Bukkit.getPluginManager().registerEvents(listener, getInstance());
    return true;
  }

  @Override
  public void unregister() {
    HandlerList.unregisterAll(listener);
    updateTask.cancel();
    saveSign();
    ConfigurationSerialization.unregisterClass(TopSign.class);
    getInstance().getCommandManager().unregister(signCommand);
  }

  @SuppressWarnings("unchecked")
  private void loadSign() {
    FileConfiguration config = signConfig.getConfig();
    if (config.isSet("data")) {
      topSignList.addAll((Collection<? extends TopSign>) config.getList("data"));
    }
  }

  private void saveSign() {
    FileConfiguration config = signConfig.getConfig();
    config.set("data", topSignList);
    signConfig.saveConfig();
  }

  public void addSign(TopSign topSign) {
    removeSign(topSign.getLocation());
    topSignList.add(topSign);
  }

  public void removeSign(Location location) {
    topSignList.removeIf(topSign -> topSign.getLocation().equals(location));
  }

  public boolean containsSign(Location location) {
    return topSignList.stream().anyMatch(topSign -> topSign.getLocation().equals(location));
  }

  @Override
  public String getName() {
    return "Sign";
  }
}