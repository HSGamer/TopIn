package me.hsgamer.topin.command;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Arrays;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public final class GetGettersCommand extends BukkitCommand {

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

    sendMessage(sender, "&a&lRegistered getters: ");
    TopIn.getInstance().getGetterManager()
        .getGetters()
        .forEach(getter -> sendMessage(sender, "  &f- &b" + getter));
    return true;
  }
}
