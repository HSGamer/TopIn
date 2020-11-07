package me.hsgamer.topin.storage.type;

import me.hsgamer.hscore.bukkit.config.PluginConfig;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.storage.Storage;
import org.simpleyaml.configuration.file.FileConfiguration;

import java.io.File;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The YAML storage
 */
public class YamlStorage extends Storage {

    private final PluginConfig config;

    public YamlStorage(String name) {
        super(name);
        config = new PluginConfig(new File(TopIn.getDataDir(), name + ".yml"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<UUID, BigDecimal> load() {
        Map<UUID, BigDecimal> map = new HashMap<>();
        FileConfiguration configuration = config.getConfig();
        configuration.getValues(false)
                .forEach((k, v) -> map.put(UUID.fromString(k), new BigDecimal(String.valueOf(v))));
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Map<UUID, BigDecimal> map) {
        FileConfiguration configuration = config.getConfig();
        map.forEach((uuid, bigDecimal) -> configuration.set(uuid.toString(), bigDecimal.toString()));
        config.saveConfig();
    }

}
