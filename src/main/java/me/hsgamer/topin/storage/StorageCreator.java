package me.hsgamer.topin.storage;

import me.hsgamer.topin.config.MainConfig;

/**
 * The storage creator
 */
public class StorageCreator {
  private StorageType storageType;

  /**
   * Load the default storage type
   */
  public void loadType() {
    try {
      storageType = StorageType.valueOf(MainConfig.STORAGE_TYPE.getValue().trim().toUpperCase());
    } catch (Exception e) {
      storageType = StorageType.JSON;
    }
  }

  /**
   * Create a storage
   *
   * @param name the name of the storage
   * @return the storage
   */
  public Storage createStorage(String name) {
    if (storageType == null) {
      loadType();
    }
    return storageType.createStorage(name);
  }
}
