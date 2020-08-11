package me.hsgamer.topin.getter.skull;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.hscore.bukkit.config.path.BooleanConfigPath;
import me.hsgamer.hscore.bukkit.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class SkullGetter extends Getter {

  public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update.period", 20);
  public static final BooleanConfigPath UPDATE_ASYNC = new BooleanConfigPath("update.async", true);
  private final SkullCommand skullCommand = new SkullCommand();
  private PluginConfig skullConfig;
  private final List<TopSkull> topSkullList = new ArrayList<>();
  private BukkitTask updateTask;

  @Override
  public boolean register() {
    ConfigurationSerialization.registerClass(TopSkull.class);
    skullConfig = new PluginConfig(getInstance(), "skull.yml");
    skullConfig.getConfig().options().copyDefaults(true);
    UPDATE_PERIOD.setConfig(skullConfig);
    UPDATE_ASYNC.setConfig(skullConfig);
    skullConfig.saveConfig();
    loadSkull();

    final BukkitRunnable updateRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        topSkullList.forEach(TopSkull::update);
      }
    };
    if (UPDATE_ASYNC.getValue().equals(Boolean.TRUE)) {
      updateTask = updateRunnable.runTaskTimerAsynchronously(getInstance(), 0, UPDATE_PERIOD.getValue());
    } else {
      updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
    }
    getInstance().getCommandManager().register(skullCommand);
    return true;
  }

  @Override
  public void unregister() {
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

  @Override
  public String getName() {
    return "Skull";
  }
}
