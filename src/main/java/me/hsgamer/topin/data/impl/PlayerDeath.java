package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath extends AutoUpdateSimpleDataList implements Listener {

  private final Map<UUID, Integer> deathCaches = new ConcurrentHashMap<>();

  public PlayerDeath() {
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
        if (deathCaches.containsKey(getUniqueId())) {
          setValue(getValue().add(BigDecimal.valueOf(deathCaches.remove(getUniqueId()))));
        }
      }
    };
  }

  @Override
  public String getName() {
    return "player_death";
  }

  @Override
  public String getDefaultDisplayName() {
    return "Death";
  }

  @Override
  public String getDefaultSuffix() {
    return "";
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onDamage(PlayerDeathEvent event) {
    deathCaches.merge(event.getEntity().getUniqueId(), 1, Integer::sum);
  }
}
