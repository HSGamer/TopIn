package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

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
  public String getName() {
    return "player_online_time";
  }

  @Override
  public String getDefaultDisplayName() {
    return "Online Time";
  }

  @Override
  public String getDefaultSuffix() {
    return "seconds";
  }

  @Override
  public String getDefaultFormat() {
    return "#";
  }
}
