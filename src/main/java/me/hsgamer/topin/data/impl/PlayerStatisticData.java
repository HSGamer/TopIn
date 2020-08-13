package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;

public class PlayerStatisticData extends AutoUpdateSimpleDataList {

  private final Statistic statistic;

  public PlayerStatisticData(Statistic statistic) {
    super(20);
    this.statistic = statistic;
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return new PairDecimal(uuid) {
      @Override
      public void update() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
        if (offlinePlayer.isOnline()) {
          setValue(BigDecimal.valueOf(offlinePlayer.getPlayer().getStatistic(statistic)));
        }
      }
    };
  }

  @Override
  public String getName() {
    return "statistic_" + statistic.name().toLowerCase();
  }

  @Override
  public String getDefaultDisplayName() {
    return "Stats - " + statistic.name();
  }

  @Override
  public String getDefaultSuffix() {
    return "";
  }
}
