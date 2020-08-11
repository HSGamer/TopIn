package me.hsgamer.topin;

import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.command.GetGettersCommand;
import me.hsgamer.topin.command.GetTopTenCommand;
import me.hsgamer.topin.command.MainCommand;
import me.hsgamer.topin.command.ReloadCommand;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.data.impl.PlayerExpData;
import me.hsgamer.topin.data.impl.PlayerLevelData;
import me.hsgamer.topin.data.impl.PlayerOnlineTime;
import me.hsgamer.topin.getter.placeholderapi.PlaceholderAPIGetter;
import me.hsgamer.topin.getter.skull.SkullGetter;
import me.hsgamer.topin.listener.JoinListener;
import me.hsgamer.topin.manager.DataListManager;
import me.hsgamer.topin.manager.GetterManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public final class TopIn extends JavaPlugin {

  private static TopIn instance;
  private final DataListManager dataListManager = new DataListManager(this);
  private final CommandManager commandManager = new CommandManager(this);
  private final MainConfig mainConfig = new MainConfig(this);
  private final MessageConfig messageConfig = new MessageConfig(this);
  private final GetterManager getterManager = new GetterManager();
  private BukkitTask saveTask;

  /**
   * Get the instance
   *
   * @return the instance
   */
  public static TopIn getInstance() {
    return instance;
  }

  @Override
  public void onLoad() {
    instance = this;
    MessageUtils.setPrefix(MessageConfig.PREFIX::getValue);
  }

  @Override
  public void onEnable() {
    registerDefaultDataList();
    loadCommands();
    commandManager.syncCommand();
    registerListener();
    registerDefaultGetters();
    startNewSaveTask();
  }

  @Override
  public void onDisable() {
    stopSaveTask();
    getterManager.unregisterAll();
    dataListManager.saveAll();
    dataListManager.clearAll();
    HandlerList.unregisterAll(this);
  }

  /**
   * Start new save task
   */
  public void startNewSaveTask() {
    stopSaveTask();

    final BukkitRunnable saveRunnable = new BukkitRunnable() {
      @Override
      public void run() {
        dataListManager.saveAll();
        if (MainConfig.SAVE_SILENT.getValue().equals(Boolean.FALSE)) {
          MessageUtils.sendMessage(getServer().getConsoleSender(), MessageConfig.SAVE.getValue());
        }
      }
    };
    int period = MainConfig.SAVE_PERIOD.getValue();
    if (period >= 0) {
      if (MainConfig.SAVE_ASYNC.getValue().equals(Boolean.TRUE)) {
        saveTask = saveRunnable.runTaskTimerAsynchronously(this, 0, period);
      } else {
        saveTask = saveRunnable.runTaskTimer(this, 0, period);
      }
    }
  }

  /**
   * Stop the save task
   */
  public void stopSaveTask() {
    if (saveTask != null && !saveTask.isCancelled()) {
      saveTask.cancel();
    }
  }

  /**
   * Get the data list manager
   *
   * @return the manager
   */
  public DataListManager getDataListManager() {
    return dataListManager;
  }

  /**
   * Register default data list
   */
  private void registerDefaultDataList() {
    dataListManager.register("player_exp", new PlayerExpData());
    dataListManager.register("player_level", new PlayerLevelData());
    dataListManager.register("player_online_time", new PlayerOnlineTime());
  }

  /**
   * Register default getters
   */
  private void registerDefaultGetters() {
    Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
      getterManager.register(new PlaceholderAPIGetter());
      getterManager.register(new SkullGetter());
    });
  }

  /**
   * Register the default commands
   */
  private void loadCommands() {
    commandManager.register(new GetTopTenCommand());
    commandManager.register(new MainCommand(getName().toLowerCase()));
    commandManager.register(new ReloadCommand());
    commandManager.register(new GetGettersCommand());
  }

  /**
   * Register the listeners
   */
  private void registerListener() {
    getServer().getPluginManager().registerEvents(new JoinListener(), this);
  }

  /**
   * Get the command manager
   *
   * @return the manager
   */
  public CommandManager getCommandManager() {
    return commandManager;
  }

  /**
   * Get the main config
   *
   * @return the main config
   */
  public MainConfig getMainConfig() {
    return mainConfig;
  }

  /**
   * Get the message config
   *
   * @return the message config
   */
  public MessageConfig getMessageConfig() {
    return messageConfig;
  }

  /**
   * Get the getter manager
   *
   * @return the getter manager
   */
  public GetterManager getGetterManager() {
    return getterManager;
  }
}
