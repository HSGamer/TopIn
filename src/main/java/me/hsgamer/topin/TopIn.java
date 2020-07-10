package me.hsgamer.topin;

import me.hsgamer.topin.command.GetTopTenCommand;
import me.hsgamer.topin.command.MainCommand;
import me.hsgamer.topin.command.ReloadCommand;
import me.hsgamer.topin.config.impl.MainConfig;
import me.hsgamer.topin.config.impl.MessageConfig;
import me.hsgamer.topin.data.impl.PlayerExpData;
import me.hsgamer.topin.data.impl.PlayerLevelData;
import me.hsgamer.topin.listener.JoinListener;
import me.hsgamer.topin.manager.CommandManager;
import me.hsgamer.topin.manager.DataListManager;
import me.hsgamer.topin.utils.CommonUtils;
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
  private final BukkitRunnable updateRunnable = new BukkitRunnable() {
    @Override
    public void run() {
      dataListManager.updateAll();
      dataListManager.saveAll();
      CommonUtils.sendMessage(getServer().getConsoleSender(), MessageConfig.UPDATE.getValue());
    }
  };
  private BukkitTask updateTask;

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
  }

  @Override
  public void onEnable() {
    registerDefaultDataList();
    loadCommands();
    commandManager.syncCommand();
    registerListener();
    startNewUpdateTask(MainConfig.UPDATE_PERIOD.getValue());
  }

  @Override
  public void onDisable() {
    stopUpdateTask();
    dataListManager.saveAll();
    HandlerList.unregisterAll(this);
  }

  /**
   * Start new update task
   *
   * @param period the delay time between each run
   */
  public void startNewUpdateTask(int period) {
    stopUpdateTask();

    if (period >= 0) {
      if (MainConfig.UPDATE_ASYNC.getValue().equals(Boolean.TRUE)) {
        updateTask = updateRunnable.runTaskTimerAsynchronously(this, period, period);
      } else {
        updateTask = updateRunnable.runTaskTimer(this, period, period);
      }
    }
  }

  /**
   * Stop the update task
   */
  public void stopUpdateTask() {
    if (updateTask != null && !updateTask.isCancelled()) {
      updateTask.cancel();
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
  }

  /**
   * Register the default commands
   */
  private void loadCommands() {
    commandManager.register(new GetTopTenCommand());
    commandManager.register(new MainCommand(getName().toLowerCase()));
    commandManager.register(new ReloadCommand());
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
   * Get the massage config
   *
   * @return the message config
   */
  public MessageConfig getMessageConfig() {
    return messageConfig;
  }
}
