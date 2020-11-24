package me.hsgamer.topin;

import me.hsgamer.hscore.addon.AddonManager;
import me.hsgamer.hscore.bukkit.addon.PluginAddonManager;
import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.hscore.checker.spigotmc.SimpleVersionChecker;
import me.hsgamer.hscore.config.ConfigProvider;
import me.hsgamer.simplebukkityaml.configuration.file.YamlConfiguration;
import me.hsgamer.topin.command.*;
import me.hsgamer.topin.config.*;
import me.hsgamer.topin.data.impl.*;
import me.hsgamer.topin.getter.placeholderapi.PlaceholderAPIGetter;
import me.hsgamer.topin.getter.sign.SignGetter;
import me.hsgamer.topin.getter.skull.SkullGetter;
import me.hsgamer.topin.listener.JoinListener;
import me.hsgamer.topin.manager.DataListManager;
import me.hsgamer.topin.manager.GetterManager;
import me.hsgamer.topin.manager.SaveTaskManager;
import org.bstats.bukkit.MetricsLite;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public final class TopIn extends JavaPlugin {

    private static TopIn instance;
    private static File dataDir;

    private final CommandManager commandManager = new CommandManager(this);
    private final MainConfig mainConfig = new MainConfig(this);
    private final MessageConfig messageConfig = new MessageConfig(this);
    private final DisplayNameConfig displayNameConfig = new DisplayNameConfig(this);
    private final SuffixConfig suffixConfig = new SuffixConfig(this);
    private final FormatConfig formatConfig = new FormatConfig(this);

    private final DataListManager dataListManager = new DataListManager();
    private final GetterManager getterManager = new GetterManager();
    private final SaveTaskManager saveTaskManager = new SaveTaskManager(this);

    private final AddonManager addonManager = new PluginAddonManager(this) {
        @Override
        public @NotNull String getAddonConfigFileName() {
            return "addon.yml";
        }

        @Override
        protected @NotNull ConfigProvider<?> getConfigProvider() {
            return YamlConfiguration::loadConfiguration;
        }
    };

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
    public void onLoad() {
        instance = this;
        MessageUtils.setPrefix(MessageConfig.PREFIX::getValue);
    }

    @Override
    public void onEnable() {
        if (getDescription().getVersion().contains("SNAPSHOT")) {
            getLogger().warning("You are using the development version");
            getLogger().warning("This is not ready for production");
            getLogger().warning("Use in your own risk");
        } else {
            new SimpleVersionChecker(83017).getVersion().thenAccept(output -> {
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

        loadCommands();
        registerListener();
        registerDefaultGetters();
        addonManager.loadAddons();
        commandManager.syncCommand();

        Bukkit.getScheduler().scheduleSyncDelayedTask(this, () -> {
            registerDefaultDataList();
            saveTaskManager.startNewSaveTask();
            addonManager.enableAddons();
            addonManager.callPostEnable();
            commandManager.syncCommand();
        });

        if (Boolean.TRUE.equals(MainConfig.METRICS.getValue())) {
            new MetricsLite(this, 8584);
        }
    }

    @Override
    public void onDisable() {
        saveTaskManager.stopSaveTask();
        dataListManager.saveAll();
        addonManager.disableAddons();
        getterManager.unregisterAll();
        dataListManager.unregisterAll();
        dataListManager.clearAll();
        HandlerList.unregisterAll(this);
        instance = null;
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
        dataListManager.register(new PlayerExp());
        dataListManager.register(new PlayerLevel());
        dataListManager.register(new PlayerTotalDamage());
        dataListManager.register(new PlayerDeath());
        dataListManager.register(new PlayerTotalKill());
        dataListManager.register(new PlayerEnemyPlayerKill());
        dataListManager.register(new PlayerMonsterKill());
        dataListManager.register(new PlayerAnimalKill());
        dataListManager.register(new PlayerPlacedBlock());
        dataListManager.register(new PlayerBrokenBlock());
        dataListManager.register(new VaultMoney());

        PlayerOnlineTime playerOnlineTime = new PlayerOnlineTime();
        dataListManager.register(playerOnlineTime);
        dataListManager.register(new PlayerOnlineMinutes(playerOnlineTime));
        dataListManager.register(new PlayerOnlineHours(playerOnlineTime));

        if (Boolean.TRUE.equals(MainConfig.ENABLE_PAPI_DATA_LIST.getValue())) {
            Objects.requireNonNull(MainConfig.PAPI_DATA_LIST.getValue()).forEach((name, placeholder) ->
                    dataListManager.register(new PlaceholderApiData(name, placeholder.trim())));
        }
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
        commandManager.register(new MainCommand(getName().toLowerCase()));
        commandManager.register(new ReloadCommand());
        commandManager.register(new GetGettersCommand());
        commandManager.register(new GetDataListCommand());
        commandManager.register(new GetTopCommand());
        commandManager.register(new SearchDataListCommand());
        commandManager.register(new GetAddonsCommand());
    }

    /**
     * Register the listeners
     */
    private void registerListener() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
    }

    /**
     * Get the command manager
     *
     * @return the manager
     */
    public CommandManager getCommandManager() {
        return commandManager;
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
     * Get the addon manager
     *
     * @return the addon manager
     */
    public AddonManager getAddonManager() {
        return addonManager;
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
