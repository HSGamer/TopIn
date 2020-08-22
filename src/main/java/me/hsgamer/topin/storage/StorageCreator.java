package me.hsgamer.topin.storage;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import me.hsgamer.hscore.map.CaseInsensitiveStringMap;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.storage.type.JsonStorage;
import me.hsgamer.topin.storage.type.YamlStorage;

/**
 * The storage creator
 */
public class StorageCreator {

  private static final Map<String, Function<String, Storage>> createStorageMap = new CaseInsensitiveStringMap<>();

  static {
    registerStorageCreator("yaml", YamlStorage::new);
    registerStorageCreator("json", JsonStorage::new);
  }

  private Function<String, Storage> createStorageFunction;

  /**
   * Register the "create storage" function
   *
   * @param name           the name of the function
   * @param createFunction the "create storage" function
   */
  public static void registerStorageCreator(String name, Function<String, Storage> createFunction) {
    createStorageMap.put(name, createFunction);
  }

  /**
   * Load the default storage type
   */
  public void loadType() {
    createStorageFunction = Optional.ofNullable(createStorageMap
        .get(MainConfig.STORAGE_TYPE.getValue().trim())).orElse(JsonStorage::new);
  }

  /**
   * Create a storage
   *
   * @param name the name of the storage
   * @return the storage
   */
  public Storage createStorage(String name) {
    if (createStorageFunction == null) {
      loadType();
    }
    return createStorageFunction.apply(name);
  }
}
