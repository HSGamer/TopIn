package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerExpData extends AutoUpdateSimpleDataList {

  public PlayerExpData() {
    super(200);
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return new PairDecimal(uuid) {
      @Override
      public void update() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.isOnline()) {
          setValue(BigDecimal.valueOf(offlinePlayer.getPlayer().getTotalExperience()));
        }
      }
    };
  }

  @Override
  public String getDisplayName() {
    return "Total EXP";
  }

  @Override
  public String getDisplaySuffix() {
    return "exp";
  }
}
