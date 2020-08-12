package me.hsgamer.topin.getter.skull;

import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class SkullBreakListener implements Listener {

  private final SkullGetter skullGetter;

  public SkullBreakListener(SkullGetter getter) {
    this.skullGetter = getter;
  }

  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    Location location = event.getBlock().getLocation();
    Player player = event.getPlayer();
    if (skullGetter.containsSkull(location)) {
      if (!player.hasPermission(Permissions.SKULL_BREAK) || !player.isSneaking()) {
        event.setCancelled(true);
        return;
      }
      skullGetter.removeSkull(location);
      MessageUtils.sendMessage(player, MessageConfig.SKULL_REMOVED.getValue());
    }
  }

  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    event.blockList().removeIf(block -> skullGetter.containsSkull(block.getLocation()));
  }

  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    event.blockList().removeIf(block -> skullGetter.containsSkull(block.getLocation()));
  }
}
