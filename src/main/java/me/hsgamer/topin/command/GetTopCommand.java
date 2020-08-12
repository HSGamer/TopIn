package me.hsgamer.topin.command;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Arrays;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class GetTopCommand extends BukkitCommand {

  public GetTopCommand() {
    super("gettop", "Get Top List", "/gettop <data_list> [<from_index> <to_index>]",
        Arrays.asList("toplist", "gettoplist"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.TOP)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }
    if (args.length < 1) {
      sendMessage(sender, "&c" + getUsage());
      return false;
    }
    if (!TopIn.getInstance().getDataListManager().getDataList(args[0]).isPresent()) {
      sendMessage(sender, MessageConfig.DATA_LIST_NOT_FOUND.getValue());
      return false;
    }
    return true;
  }
}
