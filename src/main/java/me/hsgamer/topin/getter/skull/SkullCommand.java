package me.hsgamer.topin.getter.skull;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public final class SkullCommand extends BukkitCommand {

  private final SkullGetter skullGetter;

  public SkullCommand(SkullGetter skullGetter) {
    super("settopskull", "Set the skull for top players", "/settopskull <data_list> <index>",
        Collections.singletonList("topskull"));
    this.skullGetter = skullGetter;
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!(sender instanceof Player)) {
      sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
      return false;
    }
    if (!sender.hasPermission(Permissions.SKULL)) {
      sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
      return false;
    }
    if (args.length < 2) {
      sendMessage(sender, "&c" + getUsage());
      return false;
    }
    if (!TopIn.getInstance().getDataListManager().getDataList(args[0]).isPresent()) {
      sendMessage(sender, MessageConfig.DATA_LIST_NOT_FOUND.getValue());
      return false;
    }
    int index;
    try {
      index = Integer.parseInt(args[1]);
    } catch (NumberFormatException e) {
      sendMessage(sender, MessageConfig.NUMBER_REQUIRED.getValue());
      return false;
    }
    Block block = ((Player) sender).getTargetBlock(null, 5);
    if (block == null || !(block.getState() instanceof Skull)) {
      sendMessage(sender, MessageConfig.SKULL_REQUIRED.getValue());
      return false;
    }

    skullGetter.addSkull(new TopSkull(block.getLocation(), args[0], index));
    sendMessage(sender, MessageConfig.SUCCESS.getValue());
    return true;
  }

  @Override
  public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
    List<String> list = new ArrayList<>();
    if (args.length == 1) {
      list.addAll(TopIn.getInstance().getDataListManager().getSuggestedDataListNames(args[0]));
    }
    return list;
  }
}
