package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.util.UUID;
import me.clip.placeholderapi.PlaceholderAPI;
import me.hsgamer.topin.data.list.AutoUpdateSimpleDataList;
import me.hsgamer.topin.data.value.PairDecimal;
import org.bukkit.Bukkit;

public class PlaceholderApiData extends AutoUpdateSimpleDataList {

  private final String placeholder;

  public PlaceholderApiData(String placeholder) {
    super(20);
    this.placeholder = placeholder;
  }

  @Override
  public boolean canRegister() {
    return Bukkit.getServer().getPluginManager().isPluginEnabled("PlaceholderAPI");
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return new PairDecimal(uuid) {
      @Override
      public void update() {
        try {
          setValue(
              new BigDecimal(
                  PlaceholderAPI.setPlaceholders(Bukkit.getOfflinePlayer(getUniqueId()),
                      "%" + placeholder + "%")
              )
          );
        } catch (Exception e) {
          // IGNORED
        }
      }
    };
  }

  @Override
  public String getName() {
    return "placeholderapi_" + placeholder;
  }

  @Override
  public String getDefaultDisplayName() {
    return placeholder + "'s Top";
  }

  @Override
  public String getDefaultSuffix() {
    return "";
  }
}
