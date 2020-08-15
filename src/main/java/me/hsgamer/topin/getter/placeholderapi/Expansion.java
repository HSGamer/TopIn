package me.hsgamer.topin.getter.placeholderapi;

import static me.hsgamer.topin.TopIn.getInstance;

import java.util.Optional;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.data.list.DataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public final class Expansion extends PlaceholderExpansion {

  private static final String PLAYER_PREFIX = "player_";
  private static final String VALUE_PREFIX = "value_";
  private static final String UUID_PREFIX = "uuid_";
  private static final String CURRENT_INDEX = "current_top_";
  private static final String CURRENT_VALUE = "current_value_";
  private static final String SUFFIX = "suffix_";
  private static final String DISPLAY_NAME = "display_name_";

  @Override
  public @NotNull String getIdentifier() {
    return getInstance().getName().toLowerCase();
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
  public String onRequest(OfflinePlayer offlinePlayer, @NotNull String params) {
    if (params.startsWith(PLAYER_PREFIX)) {
      PairDecimal pairDecimal = getTopPair(params.substring(PLAYER_PREFIX.length()).trim());
      return pairDecimal != null ? Bukkit.getOfflinePlayer(pairDecimal.getUniqueId()).getName()
          : "";
    } else if (params.startsWith(VALUE_PREFIX)) {
      PairDecimal pairDecimal = getTopPair(params.substring(VALUE_PREFIX.length()).trim());
      return pairDecimal != null ? pairDecimal.getValue().toPlainString() : "";
    } else if (params.startsWith(UUID_PREFIX)) {
      PairDecimal pairDecimal = getTopPair(params.substring(UUID_PREFIX.length()).trim());
      return pairDecimal != null ? pairDecimal.getUniqueId().toString() : "";
    } else if (params.startsWith(SUFFIX)) {
      return getDataList(params.substring(SUFFIX.length()).trim()).map(DataList::getSuffix)
          .orElse("");
    } else if (params.startsWith(DISPLAY_NAME)) {
      return getDataList(params.substring(DISPLAY_NAME.length()).trim())
          .map(DataList::getDisplayName).orElse("");
    }

    if (offlinePlayer == null) {
      return null;
    }

    if (params.startsWith(CURRENT_INDEX)) {
      return String.valueOf(
          getDataList(params.substring(CURRENT_INDEX.length()).trim())
              .flatMap(dataList -> dataList.getTopIndex(offlinePlayer.getUniqueId()))
              .map(integer -> integer + MainConfig.DISPLAY_TOP_START_INDEX.getValue())
              .orElse(-1)
      );
    } else if (params.startsWith(CURRENT_VALUE)) {
      return getDataList(params.substring(CURRENT_VALUE.length()).trim())
          .flatMap(dataList -> dataList.getPair(offlinePlayer.getUniqueId()))
          .map(decimal -> decimal.getValue().toPlainString())
          .orElse("");
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
    Optional<DataList> optional = getDataList(params.substring(firstIndex + 1));
    if (optional.isPresent()) {
      DataList dataList = optional.get();
      if (topIndex < dataList.getSize()) {
        return dataList.getPair(topIndex);
      }
    }
    return null;
  }

  private Optional<DataList> getDataList(String name) {
    return getInstance().getDataListManager().getDataList(name);
  }
}
