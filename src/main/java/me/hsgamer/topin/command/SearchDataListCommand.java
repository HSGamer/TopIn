package me.hsgamer.topin.command;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Collection;
import java.util.Collections;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import me.hsgamer.topin.data.list.DataList;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public final class SearchDataListCommand extends BukkitCommand {

  public SearchDataListCommand() {
    super("searchdatalist", "Search data lists", "/searchdatalist [<search_name>]",
        Collections.singletonList("searchshortdatalist"));
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!sender.hasPermission(Permissions.DATALIST)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }

    boolean shortList = commandLabel.equalsIgnoreCase("searchshortdatalist");
    Collection<DataList> dataLists = TopIn.getInstance().getDataListManager().getDataLists();
    if (args.length > 0 && !args[0].isEmpty()) {
      dataLists.removeIf(dataList -> !dataList.getName().contains(args[0]));
    }

    sendMessage(sender, "&a&lData List: ");
    dataLists.forEach(dataList -> {
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
