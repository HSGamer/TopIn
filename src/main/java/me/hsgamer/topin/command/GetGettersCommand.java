package me.hsgamer.topin.command;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Arrays;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.manager.GetterManager;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class GetGettersCommand extends BukkitCommand {

  public GetGettersCommand() {
    super("getgetters", "Get all registered getters", "/getgetters",
        Arrays.asList("getter", "getters", "getgetter"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.GETTERS)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }

    GetterManager getterManager = TopIn.getInstance().getGetterManager();
    sendMessage(sender, "&a&lRegistered getters: ");
    for (String getter : getterManager.getGetters()) {
      sendMessage(sender, "  &f- &b" + getter);
    }
    return true;
  }
}
