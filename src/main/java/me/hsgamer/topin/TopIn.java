package me.hsgamer.topin;

import me.hsgamer.topin.command.GetTopTenCommand;
import me.hsgamer.topin.config.impl.MainConfig;
import me.hsgamer.topin.config.impl.MessageConfig;
import me.hsgamer.topin.data.impl.PlayerExpData;
import me.hsgamer.topin.data.impl.PlayerLevelData;
import me.hsgamer.topin.listener.JoinListener;
import me.hsgamer.topin.manager.CommandManager;
import me.hsgamer.topin.manager.DataListManager;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class TopIn extends JavaPlugin {

  private static TopIn instance;
  private final DataListManager dataListManager = new DataListManager(this);
  private final CommandManager commandManager = new CommandManager(this);
  private final MainConfig mainConfig = new MainConfig(this);
  private final MessageConfig messageConfig = new MessageConfig(this);

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
  }

  @Override
  public void onDisable() {
    dataListManager.saveAll();
    HandlerList.unregisterAll(this);
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
