package me.hsgamer.topin.data.list;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import me.hsgamer.hscore.bukkit.utils.BukkitUtils;
import me.hsgamer.topin.data.value.PairDecimal;
import me.hsgamer.topin.storage.Storage;
import me.hsgamer.topin.storage.StorageCreator;

/**
 * A simple data list with an synchronized ArrayList
 */
public abstract class SimpleDataList extends DataList {

  protected final List<PairDecimal> list = Collections.synchronizedList(new ArrayList<>());
  protected final Map<UUID, Integer> indexMap = new HashMap<>();
  protected Storage storage;

  /**
   * {@inheritDoc}
   */
  @Override
  public void set(UUID uuid, BigDecimal value) {
    PairDecimal pairDecimal = getPair(uuid).orElseGet(() -> {
      PairDecimal newPair = createPairDecimal(uuid);
      if (newPair != null) {
        list.add(newPair);
      }
      return newPair;
    });

    if (pairDecimal != null && value != null) {
      pairDecimal.setValue(value);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateAll() {
    list.forEach(PairDecimal::update);
    sort();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<PairDecimal> getPair(UUID uuid) {
    return list.parallelStream()
        .filter(pairDecimal -> pairDecimal.getUniqueId().equals(uuid))
        .findFirst();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<PairDecimal> getTopRange(int from, int to) {
    return list.subList(from, Math.min(list.size(), to));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public PairDecimal getPair(int index) {
    return list.get(index);
  }

  /**
   * Sort the list from high to low
   */
  protected void sort() {
    list.sort(Comparator.reverseOrder());
    for (int i = 0; i < list.size(); i++) {
      indexMap.put(list.get(i).getUniqueId(), i);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void loadData() {
    BukkitUtils.getAllUniqueIds().forEach(this::add);
    loadStorage();
    storage.load().forEach(this::set);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void saveData() {
    loadStorage();
    Map<UUID, BigDecimal> map = new HashMap<>();
    for (PairDecimal pair : list) {
      map.put(pair.getUniqueId(), pair.getValue());
    }
    storage.save(map);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getSize() {
    return list.size();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Optional<Integer> getTopIndex(UUID uuid) {
    return Optional.ofNullable(indexMap.get(uuid));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String formatValue(BigDecimal value) {
    return new DecimalFormat(getFormat()).format(value);
  }

  /**
   * Load the storage
   */
  private void loadStorage() {
    if (storage == null) {
      storage = StorageCreator.createStorage(getName());
    }
  }
}
