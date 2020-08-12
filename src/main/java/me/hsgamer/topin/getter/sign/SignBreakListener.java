package me.hsgamer.topin.getter.sign;

import java.util.Arrays;
import java.util.List;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public final class SignBreakListener implements Listener {

  private static final List<BlockFace> checkBlockFaceList = Arrays.asList(
      BlockFace.UP,
      BlockFace.DOWN,
      BlockFace.WEST,
      BlockFace.EAST,
      BlockFace.NORTH,
      BlockFace.SOUTH
  );
  private final SignGetter signGetter;

  public SignBreakListener(SignGetter getter) {
    this.signGetter = getter;
  }

  @EventHandler
  public void onBreak(BlockBreakEvent event) {
    Location location = event.getBlock().getLocation();
    Player player = event.getPlayer();
    if (signGetter.containsSign(location)) {
      if (!player.hasPermission(Permissions.SIGN_BREAK) || !player.isSneaking()) {
        event.setCancelled(true);
        return;
      }
      signGetter.removeSign(location);
      MessageUtils.sendMessage(player, MessageConfig.SIGN_REMOVED.getValue());
    }
  }

  @EventHandler
  public void onBlockExplode(BlockExplodeEvent event) {
    event.blockList().removeIf(block ->
        signGetter.containsSign(block.getLocation()) || checkBlockFaceList.stream().anyMatch(
            blockFace -> signGetter.containsSign(block.getRelative(blockFace).getLocation()))
    );
  }

  @EventHandler
  public void onEntityExplode(EntityExplodeEvent event) {
    event.blockList().removeIf(block ->
        signGetter.containsSign(block.getLocation()) || checkBlockFaceList.stream().anyMatch(
            blockFace -> signGetter.containsSign(block.getRelative(blockFace).getLocation()))
    );
  }
}
