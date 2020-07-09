package me.hsgamer.topin.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.AutoSortSimpleDataList;
import me.hsgamer.topin.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class PlayerExpData extends AutoSortSimpleDataList {

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
}
