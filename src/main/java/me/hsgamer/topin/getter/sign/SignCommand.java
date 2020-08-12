package me.hsgamer.topin.getter.sign;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public final class SignCommand extends BukkitCommand {

  private final SignGetter signGetter;

  public SignCommand(SignGetter signGetter) {
    super("settopsign", "Set the sign for top players", "/settopsign <data_list> <index>",
        Collections.singletonList("topsign"));
    this.signGetter = signGetter;
  }

  @Override
  public boolean execute(CommandSender sender, String commandLabel, String[] args) {
    if (!(sender instanceof Player)) {
      sendMessage(sender, MessageConfig.PLAYER_ONLY.getValue());
      return false;
    }
    if (!sender.hasPermission(Permissions.SIGN)) {
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
    if (block == null || !(block.getState() instanceof Sign)) {
      sendMessage(sender, MessageConfig.SIGN_REQUIRED.getValue());
      return false;
    }

    signGetter.addSign(new TopSign(block.getLocation(), args[0], index));
    sendMessage(sender, MessageConfig.SUCCESS.getValue());
    return true;
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
