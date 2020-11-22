package me.hsgamer.topin.manager;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Objects;

/**
 * Manage the saving task
 */
public final class SaveTaskManager {

    private final TopIn instance;
    private BukkitTask saveTask;

    public SaveTaskManager(TopIn instance) {
        this.instance = instance;
    }

    /**
     * Start new save task
     */
    public void startNewSaveTask() {
        stopSaveTask();

        final BukkitRunnable saveRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                instance.getDataListManager().saveAll();
                if (Objects.requireNonNull(MainConfig.SAVE_SILENT.getValue()).equals(Boolean.FALSE)) {
                    MessageUtils.sendMessage(Bukkit.getConsoleSender(), Objects.requireNonNull(MessageConfig.SAVE.getValue()));
                }
            }
        };
        int period = MainConfig.SAVE_PERIOD.getValue();
        if (period >= 0) {
            if (Objects.requireNonNull(MainConfig.SAVE_ASYNC.getValue()).equals(Boolean.TRUE)) {
                saveTask = saveRunnable.runTaskTimerAsynchronously(instance, 0, period);
            } else {
                saveTask = saveRunnable.runTaskTimer(instance, 0, period);
            }
        }
    }


    /**
     * Stop the save task
     */
    public void stopSaveTask() {
        if (saveTask != null) {
            saveTask.cancel();
        }
    }
}
