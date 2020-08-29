package me.hsgamer.topin.data.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.topin.data.list.DataList;
import me.hsgamer.topin.data.value.PairDecimal;

public class PlayerOnlineHours extends DataList {

  private static final BigDecimal UNIT = new BigDecimal(3600);
  private final PlayerOnlineTime playerOnlineTime;

  public PlayerOnlineHours(PlayerOnlineTime playerOnlineTime) {
    this.playerOnlineTime = playerOnlineTime;
  }

  @Override
  public void set(UUID uuid, BigDecimal value) {
    if (value == null) {
      playerOnlineTime.add(uuid);
    } else {
      playerOnlineTime.set(uuid, value.multiply(UNIT));
    }
  }

  @Override
  public PairDecimal createPairDecimal(UUID uuid) {
    return null;
  }

  @Override
  public void updateAll() {
    // IGNORED
  }

  @Override
  public Optional<PairDecimal> getPair(UUID uuid) {
    return playerOnlineTime.getPair(uuid);
  }

  @Override
  public List<PairDecimal> getTopRange(int from, int to) {
    return playerOnlineTime.getTopRange(from, to);
  }

  @Override
  public PairDecimal getPair(int index) {
    return playerOnlineTime.getPair(index);
  }

  @Override
  public Optional<Integer> getTopIndex(UUID uuid) {
    return playerOnlineTime.getTopIndex(uuid);
  }

  @Override
  public void loadData() {
    // IGNORED
  }

  @Override
  public void saveData() {
    // IGNORED
  }

  @Override
  public String getName() {
    return "player_online_hours";
  }

  @Override
  public int getSize() {
    return playerOnlineTime.getSize();
  }

  @Override
  public String getDefaultDisplayName() {
    return "Online Hours";
  }

  @Override
  public String getDefaultSuffix() {
    return "hours";
  }

  @Override
  public String getDefaultFormat() {
    return "#";
  }

  @Override
  public String formatValue(BigDecimal value) {
    return new DecimalFormat(getFormat()).format(value.divide(UNIT, RoundingMode.FLOOR));
  }
}
