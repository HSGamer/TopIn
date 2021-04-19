package me.hsgamer.topin.storage.type;

import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;
import me.hsgamer.hscore.json.JSONUtils;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.storage.Storage;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

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
    @Override
    public Map<UUID, BigDecimal> load() {
        if (isDataFileFailedToCreate() || dataFile.length() == 0) {
            return Collections.emptyMap();
        }

        Map<UUID, BigDecimal> map = new HashMap<>();
        JsonValue object = JSONUtils.getJSON(dataFile);
        if (object != null && object.isObject()) {
            object.asObject().forEach(member -> map.put(UUID.fromString(member.getName()), new BigDecimal(String.valueOf(member.getValue().asString()))));
        }
        return map;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Map<UUID, BigDecimal> map) {
        if (isDataFileFailedToCreate()) {
            return;
        }

        JsonObject data = new JsonObject();
        map.forEach((uuid, bigDecimal) -> data.add(uuid.toString(), bigDecimal.toString()));
        try {
            JSONUtils.writeToFile(dataFile, data);
        } catch (IOException e) {
            TopIn.getInstance().getLogger().log(Level.WARNING, e, () -> "Error when saving data for " + name);
        }
    }

    private boolean isDataFileFailedToCreate() {
        if (dataFile == null) {
            try {
                dataFile = JSONUtils.createFile(name, TopIn.getDataDir());
            } catch (IOException e) {
                TopIn.getInstance().getLogger().log(Level.WARNING, e, () -> "Error when creating data file for " + name);
                return true;
            }
        }
        return false;
    }
}
