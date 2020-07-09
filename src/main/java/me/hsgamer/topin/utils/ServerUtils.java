package me.hsgamer.topin.utils;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class ServerUtils {

  private ServerUtils() {
    // EMPTY
  }

  /**
   * Get all unique ids
   *
   * @return the unique ids
   */
  public static List<UUID> getAllUniqueIds() {
    return Arrays.stream(Bukkit.getOfflinePlayers())
        .parallel()
        .map(OfflinePlayer::getUniqueId)
        .collect(Collectors.toList());
  }
}
