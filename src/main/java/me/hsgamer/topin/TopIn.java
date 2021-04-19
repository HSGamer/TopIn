package me.hsgamer.topin;

import me.hsgamer.hscore.bukkit.baseplugin.BasePlugin;
import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.checker.spigotmc.SpigotVersionChecker;
import me.hsgamer.topin.command.*;
import me.hsgamer.topin.config.*;
import me.hsgamer.topin.data.impl.PlaceholderApiData;
import me.hsgamer.topin.getter.placeholderapi.PlaceholderAPIGetter;
import me.hsgamer.topin.getter.sign.SignGetter;
import me.hsgamer.topin.getter.skull.SkullGetter;
import me.hsgamer.topin.listener.JoinListener;
import me.hsgamer.topin.manager.DataListManager;
import me.hsgamer.topin.manager.GetterManager;
import me.hsgamer.topin.manager.SaveTaskManager;
import org.bstats.bukkit.MetricsLite;

import java.io.File;
import java.util.Objects;

public final class TopIn extends BasePlugin {

    private static TopIn instance;
    private static File dataDir;

    private final MainConfig mainConfig = new MainConfig(this);
    private final MessageConfig messageConfig = new MessageConfig(this);
    private final DisplayNameConfig displayNameConfig = new DisplayNameConfig(this);
    private final SuffixConfig suffixConfig = new SuffixConfig(this);
    private final FormatConfig formatConfig = new FormatConfig(this);

    private final DataListManager dataListManager = new DataListManager();
    private final GetterManager getterManager = new GetterManager();
    private final SaveTaskManager saveTaskManager = new SaveTaskManager(this);

    /**
     * Get the data directory
     *
     * @return the data directory
     */
    public static File getDataDir() {
        if (dataDir == null) {
            dataDir = new File(TopIn.getInstance().getDataFolder(), "data");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }
        }
        return dataDir;
    }

    /**
     * Get the instance
     *
     * @return the instance
     */
    public static TopIn getInstance() {
        return instance;
    }

    @Override
    public void preLoad() {
        instance = this;
        MessageUtils.setPrefix(MessageConfig.PREFIX::getValue);
    }

    @Override
    public void load() {
        if (getDescription().getVersion().contains("SNAPSHOT")) {
            getLogger().warning("You are using the development version");
            getLogger().warning("This is not ready for production");
            getLogger().warning("Use in your own risk");
        } else {
            new SpigotVersionChecker(83017).getVersion().thenAccept(output -> {
                if (output.startsWith("Error when getting version:")) {
                    getLogger().warning(output);
                } else if (this.getDescription().getVersion().equalsIgnoreCase(output)) {
                    getLogger().info("You are using the latest version");
                } else {
                    getLogger().warning("There is an available update");
                    getLogger().warning("New Version: " + output);
                }
            });
        }
    }

    @Override
    public void enable() {
        loadCommands();
        registerListener(new JoinListener());
        registerDefaultGetters();
        CommandManager.syncCommand();

        if (Boolean.TRUE.equals(MainConfig.METRICS.getValue())) {
            new MetricsLite(this, 8584);
        }
    }

    @Override
    public void postEnable() {
        registerDefaultDataList();
        saveTaskManager.startNewSaveTask();
        CommandManager.syncCommand();
    }

    @Override
    public void disable() {
        saveTaskManager.stopSaveTask();
        dataListManager.saveAll();
        getterManager.unregisterAll();
        dataListManager.unregisterAll();
        dataListManager.clearAll();
    }

    /**
     * Get the data list manager
     *
     * @return the manager
     */
    public DataListManager getDataListManager() {
        return dataListManager;
    }

    /**
     * Register default data list
     */
    private void registerDefaultDataList() {
        Objects.requireNonNull(MainConfig.PAPI_DATA_LIST.getValue()).forEach((name, placeholder) -> dataListManager.register(new PlaceholderApiData(name, placeholder.trim())));
    }

    /**
     * Register default getters
     */
    private void registerDefaultGetters() {
        getterManager.register(new PlaceholderAPIGetter());
        getterManager.register(new SkullGetter());
        getterManager.register(new SignGetter());
    }

    /**
     * Register the default commands
     */
    private void loadCommands() {
        registerCommand(new MainCommand(getName().toLowerCase()));
        registerCommand(new ReloadCommand());
        registerCommand(new GetGettersCommand());
        registerCommand(new GetDataListCommand());
        registerCommand(new GetTopCommand());
        registerCommand(new SearchDataListCommand());
    }

    /**
     * Get the main config
     *
     * @return the main config
     */
    public MainConfig getMainConfig() {
        return mainConfig;
    }

    /**
     * Get the message config
     *
     * @return the message config
     */
    public MessageConfig getMessageConfig() {
        return messageConfig;
    }

    /**
     * Get the getter manager
     *
     * @return the getter manager
     */
    public GetterManager getGetterManager() {
        return getterManager;
    }

    /**
     * Get the display name config
     *
     * @return the display name config
     */
    public DisplayNameConfig getDisplayNameConfig() {
        return displayNameConfig;
    }

    /**
     * Get the suffix config
     *
     * @return the suffix config
     */
    public SuffixConfig getSuffixConfig() {
        return suffixConfig;
    }

    /**
     * Get the format config
     *
     * @return the format config
     */
    public FormatConfig getFormatConfig() {
        return formatConfig;
    }

    /**
     * Get the save task manager
     *
     * @return the save task manager
     */
    public SaveTaskManager getSaveTaskManager() {
        return saveTaskManager;
    }
}
