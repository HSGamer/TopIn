package me.hsgamer.topin.storage;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

/**
 * The storage for UUID and BigDecimal
 */
public abstract class Storage {

    protected final String name;

    /**
     * A new storage
     *
     * @param name the name of the storage
     */
    protected Storage(String name) {
        this.name = name;
    }

    /**
     * Load the map of UUID and BigDecimal
     *
     * @return the map
     */
    public abstract Map<UUID, BigDecimal> load();

    /**
     * Save the map to the file
     *
     * @param map the map of UUID and BigDecimal
     */
    public abstract void save(Map<UUID, BigDecimal> map);
}
