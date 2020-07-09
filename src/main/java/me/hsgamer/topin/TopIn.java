package me.hsgamer.topin;

import me.hsgamer.topin.command.GetTopTenCommand;
import me.hsgamer.topin.config.impl.MainConfig;
import me.hsgamer.topin.config.impl.MessageConfig;
import me.hsgamer.topin.impl.PlayerExpData;
import me.hsgamer.topin.manager.CommandManager;
import me.hsgamer.topin.manager.DataListManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TopIn extends JavaPlugin {

  private static TopIn instance;
  private final DataListManager dataListManager = new DataListManager(this);
  private final CommandManager commandManager = new CommandManager(this);
  private final MainConfig mainConfig = new MainConfig(this);
  private final MessageConfig messageConfig = new MessageConfig(this);

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
  }

  @Override
  public void onDisable() {
    dataListManager.saveAll();
  }

  public DataListManager getDataListManager() {
    return dataListManager;
  }

  private void registerDefaultDataList() {
    dataListManager.register("player_exp", new PlayerExpData());
  }

  private void loadCommands() {
    commandManager.register(new GetTopTenCommand());
  }

  public CommandManager getCommandManager() {
    return commandManager;
  }

  public MainConfig getMainConfig() {
    return mainConfig;
  }

  public MessageConfig getMessageConfig() {
    return messageConfig;
  }
}
