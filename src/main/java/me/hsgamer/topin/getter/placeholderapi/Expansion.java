package me.hsgamer.topin.getter.placeholderapi;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.Optional;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hsgamer.topin.data.list.DataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Expansion extends PlaceholderExpansion {

  private static final String PLAYER_PREFIX = "player_";
  private static final String VALUE_PREFIX = "value_";

  @Override
  public @NotNull String getIdentifier() {
    return "topin";
  }

  @Override
  public @NotNull String getAuthor() {
    return getInstance().getDescription().getAuthors().toString();
  }

  @Override
  public @NotNull String getVersion() {
    return getInstance().getDescription().getVersion();
  }

  @Override
  public String onPlaceholderRequest(Player player, @NotNull String params) {
    if (params.startsWith(PLAYER_PREFIX)) {
      PairDecimal pairDecimal = getTopPair(params.substring(PLAYER_PREFIX.length()).trim());
      if (pairDecimal != null) {
        return Bukkit.getOfflinePlayer(pairDecimal.getUniqueId()).getName();
      }
    } else if (params.startsWith(VALUE_PREFIX)) {
      PairDecimal pairDecimal = getTopPair(params.substring(VALUE_PREFIX.length()).trim());
      if (pairDecimal != null) {
        return pairDecimal.getValue().toPlainString();
      }
    }
    return null;
  }

  private PairDecimal getTopPair(String params) {
    int firstIndex = params.indexOf("_");
    if (firstIndex < 0) {
      return null;
    }

    int topIndex;
    try {
      topIndex = Integer.parseInt(params.substring(0, firstIndex).toLowerCase());
    } catch (NumberFormatException e) {
      return null;
    }
    Optional<DataList> optional = getInstance().getDataListManager()
        .getDataList(params.substring(firstIndex + 1));
    if (optional.isPresent()) {
      DataList dataList = optional.get();
      if (topIndex < dataList.getSize()) {
        return dataList.getPair(topIndex);
      }
    }
    return null;
  }
}
