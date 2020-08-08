package me.hsgamer.topin.data.list;

import me.hsgamer.topin.TopIn;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * {@link SimpleDataList SimpleDataList} but can update itself
 */
public abstract class AutoSortSimpleDataList extends SimpleDataList {

  /**
   * Create an auto-update data list
   *
   * @param delay  the delay before the first update call
   * @param period the delay for each update call
   */
  public AutoSortSimpleDataList(int delay, int period) {
    super();
    new BukkitRunnable() {
      @Override
      public void run() {
        updateAll();
      }
    }.runTaskTimer(TopIn.getInstance(), delay, period);
  }

  /**
   * Create an auto-update data list
   *
   * @param period the delay for each update call
   */
  public AutoSortSimpleDataList(int period) {
    this(period, period);
  }
}
