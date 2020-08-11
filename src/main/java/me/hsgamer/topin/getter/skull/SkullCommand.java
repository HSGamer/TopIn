package me.hsgamer.topin.getter.skull;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

import java.util.Collections;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class SkullCommand extends BukkitCommand {

  public SkullCommand() {
    super("settopskull", "Set the skull for top players", "/settopskull <top_name> <index>",
        Collections.singletonList("topskull"));
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


    sendMessage(sender, MessageConfig.SUCCESS.getValue());
    return true;
  }
}
