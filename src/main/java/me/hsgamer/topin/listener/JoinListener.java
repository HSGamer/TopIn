package me.hsgamer.topin.listener;

import me.hsgamer.topin.TopIn;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

  @EventHandler
  public void onJoin(PlayerJoinEvent event) {
    TopIn.getInstance().getDataListManager().addNew(event.getPlayer().getUniqueId());
  }
}
