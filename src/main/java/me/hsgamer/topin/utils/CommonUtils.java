package me.hsgamer.topin.utils;

import me.hsgamer.topin.config.impl.MessageConfig;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class CommonUtils {

  private CommonUtils() {
    // EMPTY
  }

  /**
   * Convert color codes
   *
   * @param input the message
   * @return the converted message
   */
  public static String colorize(String input) {
    if (input == null || input.trim().isEmpty()) {
      return input;
    }

    return ChatColor.translateAlternateColorCodes('&', input);
  }

  /**
   * Send message
   *
   * @param sender  the receiver
   * @param message the message
   */
  public static void sendMessage(CommandSender sender, String message) {
    sendMessage(sender, message, true);
  }

  /**
   * Send message with prefix
   *
   * @param sender  the receiver
   * @param message the message
   * @param prefix  whether the prefix should be included
   */
  public static void sendMessage(CommandSender sender, String message, boolean prefix) {
    if (prefix) {
      message = MessageConfig.PREFIX.getValue() + message;
    }
    sender.sendMessage(colorize(message));
  }
}
