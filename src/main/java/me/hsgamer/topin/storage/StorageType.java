package me.hsgamer.topin.storage;

import java.util.function.Function;
import me.hsgamer.topin.storage.type.JsonStorage;
import me.hsgamer.topin.storage.type.YamlStorage;

/**
 * The type of storage
 */
public enum StorageType {
  YAML(YamlStorage::new),
  JSON(JsonStorage::new)
  ;

  private final Function<String, Storage> createStorageFunction;

  StorageType(Function<String, Storage> createStorageFunction) {
    this.createStorageFunction = createStorageFunction;
  }

  /**
   * Create a storage
   *
   * @param name the name of the storage
   * @return the storage
   */
  public Storage createStorage(String name) {
    return createStorageFunction.apply(name);
  }
}
