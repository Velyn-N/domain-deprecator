package de.nmadev.domain.deprecator.command;

import de.nmadev.domain.deprecator.DomainDeprecator;
import io.papermc.paper.plugin.configuration.PluginMeta;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DomainDeprecatorCommand extends Command {
    public static final String PERMISSION = "domain-deprecator.admin";

    private final DomainDeprecator plugin;

    public DomainDeprecatorCommand(DomainDeprecator plugin) {
        super(plugin.getName());
        this.plugin = plugin;
        setPermission(PERMISSION);
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return List.of("reload", "info");
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] args) {
        if (!commandSender.hasPermission(PERMISSION)) {
            commandSender.sendMessage(Component.text("You don't have enough permissions.", NamedTextColor.RED));
            return false;
        }
        if (args.length == 0) {
            commandSender.sendMessage(Component.text("Please specify a valid sub command.", NamedTextColor.RED));
            return false;
        }
        String subCmd = args[0];
        return switch (subCmd) {
            case "reload" -> {
                commandSender.sendMessage(Component.text("Reloading...", NamedTextColor.GOLD));
                plugin.reload();
                commandSender.sendMessage(Component.text("Reloaded!", NamedTextColor.GREEN));
                yield true;
            }
            case "info" -> {
                PluginMeta pluginMeta = plugin.getPluginMeta();
                String miniMsg = String.format(
                        "<gold><bold>%s</bold> - Plugin Information<newline>" +
                        "<gold>Plugin-Version: </gold><green>%s<newline>" +
                        "<gold>Authors: </gold><green>%s<newline>",
                        pluginMeta.getName(),
                        pluginMeta.getVersion(),
                        String.join(", ", pluginMeta.getAuthors()));
                commandSender.sendMessage(MiniMessage.miniMessage().deserialize(miniMsg));
                yield true;
            }
            default -> {
                commandSender.sendMessage(Component.text("Invalid sub command", NamedTextColor.RED));
                yield false;
            }
        };
    }
}
