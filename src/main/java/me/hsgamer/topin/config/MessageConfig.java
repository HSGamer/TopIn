package me.hsgamer.topin.config;

import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.PathableConfig;
import me.hsgamer.hscore.config.path.StringConfigPath;
import me.hsgamer.topin.config.path.StringListConfigPath;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Collections;

public final class MessageConfig extends PathableConfig {

    public static final StringConfigPath PREFIX = new StringConfigPath("prefix", "&f[&aTopIn&f] ");
    public static final StringConfigPath SAVE = new StringConfigPath("save", "&eThe database has been saved");
    public static final StringConfigPath SUCCESS = new StringConfigPath("success", "&aSuccess");
    public static final StringConfigPath NO_PERMISSION = new StringConfigPath("no-permission", "&cYou don't have permission to do this");
    public static final StringConfigPath PLAYER_ONLY = new StringConfigPath("player-only", "&cYou should be a player to do this");
    public static final StringConfigPath GETTER_REGISTERED = new StringConfigPath("getter-registered", "&aThe getter '<getter>' is successfully registered");
    public static final StringConfigPath GETTER_UNREGISTERED = new StringConfigPath("getter-unregistered", "&aThe getter '<getter>' is successfully unregistered");
    public static final StringConfigPath NUMBER_REQUIRED = new StringConfigPath("number-required", "&cNumber is required");
    public static final StringConfigPath ILLEGAL_FROM_TO_NUMBER = new StringConfigPath("illegal-from-to-number", "&cThe 'to' number must be higher than the 'from' number");
    public static final StringConfigPath OUT_OF_BOUND = new StringConfigPath("out-of-bound", "&cA number is out-of-bound");
    public static final StringConfigPath SKULL_REMOVED = new StringConfigPath("skull-removed", "&aThe skull is removed");
    public static final StringConfigPath SKULL_REQUIRED = new StringConfigPath("skull-required", "&cA skull is required");
    public static final StringConfigPath DATA_LIST_NOT_FOUND = new StringConfigPath("data-list-not-found", "&cThe data list is not found");
    public static final StringListConfigPath SIGN_LINES = new StringListConfigPath("sign-lines",
            Arrays.asList(
                    "&6&m               ",
                    "&b#<index> &a<name>",
                    "&a<value> <suffix>",
                    "&6&m               "
            ));
    public static final StringConfigPath SIGN_REMOVED = new StringConfigPath("sign-removed", "&aThe sign is removed");
    public static final StringConfigPath SIGN_REQUIRED = new StringConfigPath("sign-required", "&cA sign is required");
    public static final StringListConfigPath TOP_LIST_HEADER = new StringListConfigPath("top-list.header", Collections.singletonList("&6=========== &e<data_list> &6==========="));
    public static final StringListConfigPath TOP_LIST_FOOTER = new StringListConfigPath("top-list.footer", Collections.singletonList("&6=========== &e<data_list> &6==========="));
    public static final StringListConfigPath TOP_LIST_BODY = new StringListConfigPath("top-list.body", Collections.singletonList("&a&l#<index> &b<name> &f: &e<value> <suffix>"));

    public MessageConfig(JavaPlugin plugin) {
        super(new BukkitConfig(plugin, "messages.yml"));
    }
}
