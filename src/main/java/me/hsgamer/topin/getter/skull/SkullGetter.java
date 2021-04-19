package me.hsgamer.topin.getter.skull;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.hsgamer.topin.TopIn.getInstance;

public final class SkullGetter implements Getter {

    public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
    private final SkullCommand skullCommand = new SkullCommand(this);
    private final List<TopSkull> topSkullList = new ArrayList<>();
    private final SkullBreakListener listener = new SkullBreakListener(this);
    private BukkitConfig skullConfig;
    private BukkitTask updateTask;

    @Override
    public void register() {
        skullConfig = new BukkitConfig(new File(getInstance().getDataFolder(), "skull.yml"));
        UPDATE_PERIOD.setConfig(skullConfig);
        skullConfig.save();
        loadSkull();

        final BukkitRunnable updateRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                topSkullList.forEach(TopSkull::update);
            }
        };
        updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
        getInstance().getCommandManager().register(skullCommand);
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(listener);
        updateTask.cancel();
        saveSkull();
        getInstance().getCommandManager().unregister(skullCommand);
    }

    private void loadSkull() {
        if (skullConfig.contains("data")) {
            // noinspection unchecked
            topSkullList.addAll(
                    ((List<Map<String, Object>>) skullConfig.getNormalized("data")).stream().map(TopSkull::deserialize).collect(Collectors.toList())
            );
        }
    }

    private void saveSkull() {
        skullConfig.set("data", topSkullList.stream().map(TopSkull::serialize).collect(Collectors.toList()));
        skullConfig.save();
    }

    public void addSkull(TopSkull topSkull) {
        removeSkull(topSkull.getLocation());
        topSkullList.add(topSkull);
    }

    public void removeSkull(Location location) {
        topSkullList.removeIf(topSkull -> topSkull.getLocation().equals(location));
    }

    public boolean containsSkull(Location location) {
        return topSkullList.stream().anyMatch(topSkull -> topSkull.getLocation().equals(location));
    }

    @Override
    public String getName() {
        return "Skull";
    }
}
