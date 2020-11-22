package me.hsgamer.topin.storage;

import me.hsgamer.hscore.map.CaseInsensitiveStringMap;
import me.hsgamer.topin.config.MainConfig;
import me.hsgamer.topin.storage.type.JsonStorage;
import me.hsgamer.topin.storage.type.YamlStorage;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The storage creator
 */
public class StorageCreator {

    private static final Map<String, Function<String, Storage>> createStorageMap = new CaseInsensitiveStringMap<>();

    static {
        registerStorageCreator("yaml", YamlStorage::new);
        registerStorageCreator("json", JsonStorage::new);
    }

    private StorageCreator() {
        // EMPTY
    }

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
     * Unregister the "create storage" function
     *
     * @param name the name of the function
     */
    public static void unregisterStorageCreator(String name) {
        createStorageMap.remove(name);
    }

    /**
     * Create a storage
     *
     * @param name the name of the storage
     * @return the storage
     */
    public static Storage createStorage(String name) {
        return createStorageMap
                .getOrDefault(Objects.requireNonNull(MainConfig.STORAGE_TYPE.getValue()).trim(), JsonStorage::new)
                .apply(name);
    }
}
