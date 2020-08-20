package me.hsgamer.topin.data.list;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import me.hsgamer.hscore.bukkit.utils.BukkitUtils;
import me.hsgamer.hscore.json.JSONUtils;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.data.value.PairDecimal;
import org.json.simple.JSONObject;

/**
 * A simple data list with an synchronized ArrayList
 */
public abstract class SimpleDataList extends DataList {

  protected final List<PairDecimal> list = Collections.synchronizedList(new ArrayList<>());
  protected final Map<UUID, Integer> indexMap = new HashMap<>();
  protected File dataFile;

  /**
   * {@inheritDoc}
   */
  @Override
  public void set(UUID uuid, BigDecimal value) {
    PairDecimal pairDecimal = getPair(uuid).orElseGet(() -> {
      PairDecimal newPair = createPairDecimal(uuid);
      list.add(newPair);
      return newPair;
    });

    if (value != null) {
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
  @SuppressWarnings("unchecked")
  @Override
  public void loadData() {
    BukkitUtils.getAllUniqueIds().forEach(this::add);

    if (isDataFileFailedToCreate() || dataFile.length() == 0) {
      return;
    }

    Object object = JSONUtils.getJSON(dataFile);
    if (object != null) {
      ((JSONObject) object).forEach((key, value) -> set(UUID.fromString(String.valueOf(key)),
          new BigDecimal(String.valueOf(value))));
    }
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void saveData() {
    if (isDataFileFailedToCreate()) {
      return;
    }

    JSONObject data = new JSONObject();
    for (PairDecimal pair : list) {
      data.put(pair.getUniqueId().toString(), pair.getValue().toString());
    }
    try {
      JSONUtils.writeToFile(dataFile, data);
    } catch (IOException e) {
      TopIn.getInstance().getLogger()
          .log(Level.WARNING, e, () -> "Error when saving data for " + getName());
    }
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

  protected boolean isDataFileFailedToCreate() {
    if (dataFile == null) {
      try {
        dataFile = JSONUtils.createFile(getName(), getDataDir());
      } catch (IOException e) {
        TopIn.getInstance().getLogger()
            .log(Level.WARNING, e, () -> "Error when creating data file for " + getName());
        return true;
      }
    }
    return false;
  }
}
