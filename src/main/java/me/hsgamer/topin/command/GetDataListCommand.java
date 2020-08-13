package me.hsgamer.topin.command;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Arrays;
import java.util.Collections;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public final class GetDataListCommand extends BukkitCommand {

  public GetDataListCommand() {
    super("getdatalist", "Get all data lists", "/getdatalist",
        Collections.singletonList("datalist"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.DATALIST)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }

    boolean shortList =
        args.length > 0 && Arrays.stream(args).anyMatch(s -> s.equalsIgnoreCase("short"));

    sendMessage(sender, "&a&lData List: ");
    TopIn.getInstance().getDataListManager()
        .getDataLists()
        .forEach(dataList -> {
          sendMessage(sender, "  &f- &b" + dataList.getName());
          if (!shortList) {
            sendMessage(sender, "      &eDisplay Name: &f" + dataList.getDisplayName());
            sendMessage(sender, "      &eSuffix: &f" + dataList.getSuffix());
            sendMessage(sender, "      &eSize: &f" + dataList.getSize());
          }
        });
    return true;
  }
}
