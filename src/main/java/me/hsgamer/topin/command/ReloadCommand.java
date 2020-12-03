package me.hsgamer.topin.command;

import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.bukkit.utils.MessageUtils;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Collections;
import java.util.Objects;

import static me.hsgamer.topin.TopIn.getInstance;

public final class ReloadCommand extends BukkitCommand {

    public ReloadCommand() {
        super("reloadplugin", "Reload the plugin", "/reloadplugin",
                Collections.singletonList("rlplugin"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission(Permissions.RELOAD)) {
            MessageUtils.sendMessage(sender, Objects.requireNonNull(MessageConfig.NO_PERMISSION.getValue()));
            return false;
        }

        getInstance().getDataListManager().saveAll();
        getInstance().getMainConfig().reloadConfig();
        getInstance().getMessageConfig().reloadConfig();
        getInstance().getDisplayNameConfig().reloadConfig();
        getInstance().getSuffixConfig().reloadConfig();
        getInstance().getFormatConfig().reloadConfig();
        getInstance().getAddonManager().callReload();
        CommandManager.syncCommand();
        getInstance().getSaveTaskManager().startNewSaveTask();
        MessageUtils.sendMessage(sender, Objects.requireNonNull(MessageConfig.SUCCESS.getValue()));
        return true;
    }
}
