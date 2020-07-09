package me.hsgamer.topin;

import me.hsgamer.topin.command.GetTopTenCommand;
import me.hsgamer.topin.impl.PlayerExpData;
import me.hsgamer.topin.manager.CommandManager;
import me.hsgamer.topin.manager.DataListManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class TopIn extends JavaPlugin {

  private static TopIn instance;
  private final DataListManager dataListManager = new DataListManager(this);
  private final CommandManager commandManager = new CommandManager(this);

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
}
