package me.hsgamer.topin.data;

import me.hsgamer.topin.TopIn;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class AutoSortSimpleDataList extends SimpleDataList {

  public AutoSortSimpleDataList(int period) {
    super();
    new BukkitRunnable() {
      @Override
      public void run() {
        updateAll();
      }
    }.runTaskTimer(TopIn.getInstance(), 0, period);
  }
}
