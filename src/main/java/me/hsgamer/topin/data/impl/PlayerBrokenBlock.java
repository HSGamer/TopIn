package me.hsgamer.topin.data.impl;

import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerBrokenBlock extends AutoUpdateSimpleDataList implements Listener {

    private final Map<UUID, Integer> blockCaches = new ConcurrentHashMap<>();

    public PlayerBrokenBlock() {
        super(40);
    }

    @Override
    public void register() {
        Bukkit.getPluginManager().registerEvents(this, TopIn.getInstance());
        super.register();
    }

    @Override
    public void unregister() {
        HandlerList.unregisterAll(this);
        super.unregister();
    }

    @Override
    public PairDecimal createPairDecimal(UUID uuid) {
        return new PairDecimal(uuid) {
            @Override
            public void update() {
                if (blockCaches.containsKey(getUniqueId())) {
                    setValue(getValue().add(BigDecimal.valueOf(blockCaches.remove(getUniqueId()))));
                }
            }
        };
    }

    @Override
    public String getName() {
        return "player_broken_block";
    }

    @Override
    public String getDefaultDisplayName() {
        return "Broken Blocks";
    }

    @Override
    public String getDefaultSuffix() {
        return "blocks";
    }

    @Override
    public String getDefaultFormat() {
        return "#";
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }

        blockCaches.merge(event.getPlayer().getUniqueId(), 1, Integer::sum);
    }
}
