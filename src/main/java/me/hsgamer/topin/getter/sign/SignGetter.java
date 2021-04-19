package me.hsgamer.topin.getter.sign;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.path.IntegerConfigPath;
import me.hsgamer.topin.getter.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.hsgamer.topin.TopIn.getInstance;

public final class SignGetter implements Getter {

    public static final IntegerConfigPath UPDATE_PERIOD = new IntegerConfigPath("update", 20);
    private final SignCommand signCommand = new SignCommand(this);
    private final List<TopSign> topSignList = new ArrayList<>();
    private final SignBreakListener listener = new SignBreakListener(this);
    private BukkitConfig signConfig;
    private BukkitTask updateTask;

    @Override
    public void register() {
        signConfig = new BukkitConfig(getInstance(), "sign.yml");
        UPDATE_PERIOD.setConfig(signConfig);
        signConfig.save();
        loadSign();

        final BukkitRunnable updateRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                topSignList.forEach(TopSign::update);
            }
        };
        updateTask = updateRunnable.runTaskTimer(getInstance(), 0, UPDATE_PERIOD.getValue());
        getInstance().getCommandManager().register(signCommand);
        Bukkit.getPluginManager().registerEvents(listener, getInstance());
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(listener);
        updateTask.cancel();
        saveSign();
        getInstance().getCommandManager().unregister(signCommand);
    }

    private void loadSign() {
        if (signConfig.contains("data")) {
            // noinspection unchecked
            topSignList.addAll(
                    ((List<Map<String, Object>>) signConfig.getNormalized("data")).stream().map(TopSign::deserialize).collect(Collectors.toList())
            );
        }
    }

    private void saveSign() {
        signConfig.set("data", topSignList.stream().map(TopSign::serialize).collect(Collectors.toList()));
        signConfig.save();
    }

    public void addSign(TopSign topSign) {
        removeSign(topSign.getLocation());
        topSignList.add(topSign);
    }

    public void removeSign(Location location) {
        topSignList.removeIf(topSign -> topSign.getLocation().equals(location));
    }

    public boolean containsSign(Location location) {
        return topSignList.stream().anyMatch(topSign -> topSign.getLocation().equals(location));
    }

    @Override
    public String getName() {
        return "Sign";
    }
}
