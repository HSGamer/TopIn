package me.hsgamer.topin.listener;

import me.hsgamer.topin.TopIn;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public final class JoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();
    if (!player.hasPlayedBefore()) {
      TopIn.getInstance().getDataListManager().addNew(player.getUniqueId());
    }
  }
}
