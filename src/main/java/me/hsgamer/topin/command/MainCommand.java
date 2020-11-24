package me.hsgamer.topin.command;

import me.hsgamer.hscore.bukkit.command.CommandManager;
import me.hsgamer.hscore.common.Validate;
import me.hsgamer.topin.Permissions;
import me.hsgamer.topin.config.MessageConfig;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static me.hsgamer.hscore.bukkit.utils.MessageUtils.sendMessage;
import static me.hsgamer.topin.TopIn.getInstance;

public final class MainCommand extends BukkitCommand {

    public MainCommand(String name) {
        super(name, "Show all available commands", "/" + name, Collections.emptyList());
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (!commandSender.hasPermission(Permissions.HELP)) {
            sendMessage(commandSender, Objects.requireNonNull(MessageConfig.NO_PERMISSION.getValue()));
            return false;
        }

        CommandManager manager = getInstance().getCommandManager();
        sendMessage(commandSender, "");
        sendMessage(commandSender, "&e&lAuthor: &f" + Arrays
                .toString(getInstance().getDescription().getAuthors().toArray()));
        sendMessage(commandSender,
                "&e&lVersion: &f" + getInstance().getDescription().getVersion());
        sendMessage(commandSender, "&9&lDiscord: &fhttps://discord.gg/9m4GdFD");
        sendMessage(commandSender, "");
        sendMessage(commandSender, "&b&lCommand: ");
        for (Command command : manager.getRegistered().values()) {
            sendMessage(commandSender, "  &6" + command.getUsage());

            String description = command.getDescription();
            if (!Validate.isNullOrEmpty(description.trim())) {
                sendMessage(commandSender, "    &bDesc: &f" + description);
            }

            List<String> aliases = command.getAliases();
            if (!aliases.isEmpty()) {
                sendMessage(commandSender, "    &cAlias: " + Arrays.toString(aliases.toArray()));
            }
        }
        sendMessage(commandSender, "");
        return true;
    }
}
