package me.hsgamer.topin.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.DataList;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class GetTopTenCommand extends BukkitCommand {

  public GetTopTenCommand() {
    super("gettopten");
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (args.length <= 0) {
      return false;
    }

    Optional<DataList> optional = TopIn.getInstance().getDataListManager().getDataList(args[0]);
    if (optional.isPresent()) {
      DataList dataList = optional.get();
      sender.sendMessage(dataList.getDisplayName());
      dataList.getTop(10).forEach(pairDecimal -> sender.sendMessage(
          Bukkit.getOfflinePlayer(pairDecimal.getUniqueId()).getName() + " : " + pairDecimal
              .getValue().toString()));
      return true;
    } else {
      return false;
    }
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
    List<String> list = new ArrayList<>();
    if (args.length == 1) {
      list.addAll(TopIn.getInstance().getDataListManager().getDataListNames());
    }
    return list;
  }
}
