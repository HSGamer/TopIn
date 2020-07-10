package me.hsgamer.topin.command;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.Collections;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.impl.MessageConfig;
import me.hsgamer.topin.utils.CommonUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class ReloadCommand extends BukkitCommand {

  public ReloadCommand() {
    super("reloadplugin", "Reload the plugin", "/reloadplugin",
        Collections.singletonList("rlplugin"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.RELOAD)) {
      CommonUtils.sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }

    getInstance().getMainConfig().reloadConfig();
    getInstance().getMessageConfig().reloadConfig();
    getInstance().getCommandManager().syncCommand();
    getInstance().startNewUpdateTask();
    CommonUtils.sendMessage(sender, MessageConfig.SUCCESS.getValue());
    return true;
  }
}
