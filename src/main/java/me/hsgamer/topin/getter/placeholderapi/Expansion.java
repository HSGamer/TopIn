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

  private static final String PLAYER = "player_";
  private static final String VALUE = "value_";
  private static final String FORMATTED_VALUE = "formatted_value_";
  private static final String UUID = "uuid_";
  private static final String CURRENT_INDEX = "current_top_";
  private static final String CURRENT_VALUE = "current_value_";
  private static final String CURRENT_FORMATTED_VALUE = "current_formatted_value_";
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
    if (params.startsWith(PLAYER)) {
      PairDecimal pairDecimal = getTopPair(params.substring(PLAYER.length()).trim());
      return pairDecimal != null ? Bukkit.getOfflinePlayer(pairDecimal.getUniqueId()).getName()
          : "";
    } else if (params.startsWith(VALUE)) {
      PairDecimal pairDecimal = getTopPair(params.substring(VALUE.length()).trim());
      return pairDecimal != null ? pairDecimal.getValue().toPlainString() : "";
    } else if (params.startsWith(UUID)) {
      PairDecimal pairDecimal = getTopPair(params.substring(UUID.length()).trim());
      return pairDecimal != null ? pairDecimal.getUniqueId().toString() : "";
    } else if (params.startsWith(SUFFIX)) {
      return getDataList(params.substring(SUFFIX.length()).trim()).map(DataList::getSuffix)
          .orElse("");
    } else if (params.startsWith(DISPLAY_NAME)) {
      return getDataList(params.substring(DISPLAY_NAME.length()).trim())
          .map(DataList::getDisplayName).orElse("");
    } else if (params.startsWith(FORMATTED_VALUE)) {
      return formatPairValue(params.substring(FORMATTED_VALUE.length()).trim());
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
    } else if (params.startsWith(CURRENT_FORMATTED_VALUE)) {
      Optional<DataList> optionalDataList = getDataList(
          params.substring(CURRENT_FORMATTED_VALUE.length()).trim());
      if (optionalDataList.isPresent()) {
        DataList dataList = optionalDataList.get();
        return dataList.getPair(offlinePlayer.getUniqueId())
            .map(pairDecimal -> dataList.formatValue(pairDecimal.getValue()))
            .orElse("");
      }
      return "";
    }
    return null;
  }

  private String formatPairValue(String params) {
    int firstIndex = params.indexOf("_");
    if (firstIndex < 0) {
      return "";
    }

    int topIndex;
    try {
      topIndex = Integer.parseInt(params.substring(0, firstIndex).toLowerCase());
    } catch (NumberFormatException e) {
      return "";
    }

    Optional<DataList> optional = getDataList(params.substring(firstIndex + 1));
    if (optional.isPresent()) {
      DataList dataList = optional.get();
      if (topIndex < dataList.getSize()) {
        return dataList.formatValue(dataList.getPair(topIndex).getValue());
      }
    }
    return "";
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
