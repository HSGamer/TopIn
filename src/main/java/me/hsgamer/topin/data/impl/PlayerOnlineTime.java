package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

// TODO: Use built-in Statistics
public class PlayerOnlineTime extends AutoUpdateSimpleDataList {

  public PlayerOnlineTime() {
    super(20);
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return new PairDecimal(uuid) {
      @Override
      public void update() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.isOnline()) {
          setValue(getValue().add(BigDecimal.ONE));
        }
      }
    };
  }

  @Override
  public String getDisplayName() {
    return "Player's Online Time";
  }
}
