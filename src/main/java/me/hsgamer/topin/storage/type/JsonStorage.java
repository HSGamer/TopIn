package me.hsgamer.topin.storage.type;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import me.hsgamer.hscore.json.JSONUtils;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.storage.Storage;
import org.json.simple.JSONObject;

/**
 * The JSON storage
 */
public class JsonStorage extends Storage {

  protected File dataFile;

  public JsonStorage(String name) {
    super(name);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public Map<UUID, BigDecimal> load() {
    if (isDataFileFailedToCreate() || dataFile.length() == 0) {
      return Collections.emptyMap();
    }

    Map<UUID, BigDecimal> map = new HashMap<>();
    Object object = JSONUtils.getJSON(dataFile);
    if (object != null) {
      ((JSONObject) object).forEach((key, value) -> map.put(UUID.fromString(String.valueOf(key)),
          new BigDecimal(String.valueOf(value))));
    }
    return map;
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  @Override
  public void save(Map<UUID, BigDecimal> map) {
    if (isDataFileFailedToCreate()) {
      return;
    }

    JSONObject data = new JSONObject();
    map.forEach((uuid, bigDecimal) -> data.put(uuid.toString(), bigDecimal.toString()));
    try {
      JSONUtils.writeToFile(dataFile, data);
    } catch (IOException e) {
      TopIn.getInstance().getLogger()
          .log(Level.WARNING, e, () -> "Error when saving data for " + name);
    }
  }

  protected boolean isDataFileFailedToCreate() {
    if (dataFile == null) {
      try {
        dataFile = JSONUtils.createFile(name, TopIn.getDataDir());
      } catch (IOException e) {
        TopIn.getInstance().getLogger()
            .log(Level.WARNING, e, () -> "Error when creating data file for " + name);
        return true;
      }
    }
    return false;
  }
}
