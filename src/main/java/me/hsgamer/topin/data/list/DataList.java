package me.hsgamer.topin.data.list;

import me.hsgamer.hscore.config.path.StringConfigPath;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.DisplayNameConfig;
import me.hsgamer.topin.config.FormatConfig;
import me.hsgamer.topin.config.SuffixConfig;
import me.hsgamer.topin.data.value.PairDecimal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * The abstract class for all data lists
 */
public abstract class DataList {

    protected StringConfigPath displayName;
    protected StringConfigPath suffix;
    protected StringConfigPath format;

    /**
     * Register necessary config path
     */
    public void registerConfigPath() {
        displayName = new StringConfigPath(getName(), getDefaultDisplayName());
        suffix = new StringConfigPath(getName(), getDefaultSuffix());
        format = new StringConfigPath(getName(), getDefaultFormat());
        DisplayNameConfig displayNameConfig = TopIn.getInstance().getDisplayNameConfig();
        SuffixConfig suffixConfig = TopIn.getInstance().getSuffixConfig();
        FormatConfig formatConfig = TopIn.getInstance().getFormatConfig();
        displayName.setConfig(displayNameConfig);
        suffix.setConfig(suffixConfig);
        format.setConfig(formatConfig);
        displayNameConfig.saveConfig();
        suffixConfig.saveConfig();
        formatConfig.saveConfig();
    }

    /**
     * Check if this data list can be registered
     *
     * @return whether this data list can be registered
     */
    public boolean canRegister() {
        return true;
    }

    /**
     * Called when registering
     */
    public void register() {
        // LOGIC
    }

    /**
     * Called when unregistering
     */
    public void unregister() {
        // LOGIC
    }

    /**
     * Set the value of the unique id in the list
     *
     * @param uuid  the unique
     * @param value the value, nullable
     */
    public abstract void set(UUID uuid, BigDecimal value);

    /**
     * Add an unique id to the list
     *
     * @param uuid the unique id
     */
    public void add(UUID uuid) {
        set(uuid, null);
    }

    /**
     * Create a pair
     *
     * @param uuid the unique id
     * @return the pair
     */
    public abstract PairDecimal createPairDecimal(UUID uuid);

    /**
     * Update all values of the list
     */
    public abstract void updateAll();

    /**
     * Get value from the pair
     *
     * @param uuid the unique id
     * @return the value
     */
    public Optional<BigDecimal> getValue(UUID uuid) {
        return getPair(uuid).map(PairDecimal::getValue);
    }

    /**
     * Get the pair of the unique id
     *
     * @param uuid the unique id
     * @return the pair
     */
    public abstract Optional<PairDecimal> getPair(UUID uuid);

    /**
     * Get the top list
     *
     * @param from start of the top
     * @param to   end of the top (exclusive)
     * @return the top list contains the pairs
     */
    public abstract List<PairDecimal> getTopRange(int from, int to);

    /**
     * Get the top list
     *
     * @param bound end of the top
     * @return the top list contains the pairs
     */
    public List<PairDecimal> getTop(int bound) {
        return getTopRange(0, bound);
    }

    /**
     * Get the pair from the list
     *
     * @param index the index
     * @return the pair
     */
    public abstract PairDecimal getPair(int index);

    /**
     * Get the index of the unique id
     *
     * @param uuid the unique id
     * @return the index
     */
    public abstract Optional<Integer> getTopIndex(UUID uuid);

    /**
     * Load data from configuration file
     */
    public abstract void loadData();

    /**
     * Save data to configuration file
     */
    public abstract void saveData();

    /**
     * Get the technical name of the data list
     *
     * @return the display name
     */
    public abstract String getName();

    /**
     * Get the size of the data list
     *
     * @return the size
     */
    public abstract int getSize();

    /**
     * Get the default display name of the data list
     *
     * @return the display name
     */
    public abstract String getDefaultDisplayName();

    /**
     * Get the default suffix of the value
     *
     * @return the suffix
     */
    public abstract String getDefaultSuffix();

    /**
     * Get the default format of the value
     *
     * @return the suffix
     */
    public abstract String getDefaultFormat();

    /**
     * Get the suffix of the value
     *
     * @return the suffix
     */
    public String getFormat() {
        return format != null ? format.getValue() : getDefaultFormat();
    }

    /**
     * Get the display name of the data list
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName != null ? displayName.getValue() : getDefaultDisplayName();
    }

    /**
     * Get the suffix of the value
     *
     * @return the suffix
     */
    public String getSuffix() {
        return suffix != null ? suffix.getValue() : getDefaultSuffix();
    }

    /**
     * Format the value
     *
     * @param value the value
     * @return the formatted value
     */
    public abstract String formatValue(BigDecimal value);
}
