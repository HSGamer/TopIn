package me.hsgamer.topin.command;

import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.TopIn;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;

public class GetAddonsCommand extends BukkitCommand {

    public GetAddonsCommand() {
        super("addons", "Get the loaded addons", "/addons",
                Arrays.asList("topinaddons", "gettopinaddons"));
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!sender.hasPermission(Permissions.ADDONS)) {
            sendMessage(sender, MessageConfig.NO_PERMISSION.getValue());
            return false;
        }
        sendMessage(sender, "&a&lAddons: ");
        TopIn.getInstance().getAddonManager()
                .getLoadedAddons()
                .keySet()
                .forEach(name -> sendMessage(sender, "  &f- &b" + name));
        return true;
    }
}
