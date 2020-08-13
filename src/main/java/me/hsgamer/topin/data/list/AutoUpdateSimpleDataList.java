package me.hsgamer.topin.data.list;

import me.hsgamer.topin.TopIn;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

/**
 * {@link SimpleDataList SimpleDataList} but can update itself
 */
public abstract class AutoUpdateSimpleDataList extends SimpleDataList {

  private final int delay;
  private final int period;
  private BukkitTask task;

  /**
   * Create an auto-update data list
   *
   * @param delay  the delay before the first update call
   * @param period the delay for each update call
   */
  public AutoUpdateSimpleDataList(int delay, int period) {
    super();
    this.delay = Math.max(delay, 0);
    this.period = Math.max(period, 0);
  }

  /**
   * Create an auto-update data list
   *
   * @param period the delay for each update call
   */
  public AutoUpdateSimpleDataList(int period) {
    this(period, period);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void register() {
    task = new BukkitRunnable() {
      @Override
      public void run() {
        updateAll();
      }
    }.runTaskTimer(TopIn.getInstance(), delay, period);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void unregister() {
    task.cancel();
  }
}
