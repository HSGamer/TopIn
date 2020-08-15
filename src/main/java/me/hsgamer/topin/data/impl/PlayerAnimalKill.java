package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerAnimalKill extends AutoUpdateSimpleDataList implements Listener {

  private final Map<UUID, Integer> killCaches = new ConcurrentHashMap<>();

  public PlayerAnimalKill() {
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
        if (killCaches.containsKey(getUniqueId())) {
          setValue(getValue().add(BigDecimal.valueOf(killCaches.remove(getUniqueId()))));
        }
      }
    };
  }

  @Override
  public String getName() {
    return "player_animal_kills";
  }

  @Override
  public String getDefaultDisplayName() {
    return "Animal Kills";
  }

  @Override
  public String getDefaultSuffix() {
    return "kills";
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onDamage(EntityDamageByEntityEvent event) {
    Entity entity = event.getEntity();
    if (!(entity instanceof Animals) || !entity.isDead()) {
      return;
    }

    Entity damager = event.getDamager();
    if (damager instanceof Player) {
      killCaches.merge(damager.getUniqueId(), 1, Integer::sum);
    }
  }
}
