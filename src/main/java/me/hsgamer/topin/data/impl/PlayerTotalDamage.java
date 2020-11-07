package me.hsgamer.topin.data.impl;

import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerTotalDamage extends AutoUpdateSimpleDataList implements Listener {

    private final Map<UUID, Double> damageCaches = new ConcurrentHashMap<>();

    public PlayerTotalDamage() {
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
                if (damageCaches.containsKey(getUniqueId())) {
                    setValue(getValue().add(BigDecimal.valueOf(damageCaches.remove(getUniqueId()))));
                }
            }
        };
    }

    @Override
    public String getName() {
        return "player_total_damage";
    }

    @Override
    public String getDefaultDisplayName() {
        return "Total Damage";
    }

    @Override
    public String getDefaultSuffix() {
        return "damage";
    }

    @Override
    public String getDefaultFormat() {
        return "#.#";
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.isCancelled()) {
            return;
        }

        Entity damager = event.getDamager();
        if (damager instanceof Player) {
            damageCaches.merge(damager.getUniqueId(), event.getFinalDamage(), Double::sum);
        }
    }
}
