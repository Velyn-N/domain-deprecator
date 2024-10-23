package me.velyn.domain.dotd.command;

import io.papermc.paper.plugin.configuration.PluginMeta;
import me.velyn.domain.dotd.DotDMain;
import me.velyn.domain.dotd.PlayerDomainCache;
import me.velyn.domain.dotd.PlayerDomainCache.PlayerDomainMapping;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DotDCommand extends Command {
    public static final String PERMISSION = "dotd.admin";

    private final DotDMain plugin;
    private final PlayerDomainCache playerDomainCache;

    public DotDCommand(DotDMain plugin, PlayerDomainCache playerDomainCache) {
        super("dotd");
        this.plugin = plugin;
        this.playerDomainCache = playerDomainCache;
        setPermission(PERMISSION);
        setAliases(List.of("dotd", plugin.getName().replace("-", "")));
    }

    @Override
    public @NotNull List<String> tabComplete(@NotNull CommandSender sender, @NotNull String alias, @NotNull String[] args) throws IllegalArgumentException {
        return List.of("reload", "info", "players");
    }

    @Override
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
            case "players" -> {
                playerInfo(commandSender);
                yield true;
            }
            case "info" -> {
                pluginInfo(commandSender);
                yield true;
            }
            default -> {
                commandSender.sendMessage(Component.text("Invalid sub command", NamedTextColor.RED));
                yield false;
            }
        };
    }

    private void playerInfo(CommandSender commandSender) {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> lines = new ArrayList<>();
        lines.add(miniMessage.deserialize("<gold>----- Domains used by Players -----"));

        Map<String, List<PlayerDomainMapping>> playersByDomain = playerDomainCache.getCachedValues()
                .stream()
                .collect(Collectors.groupingBy(PlayerDomainMapping::hostName));

        int longestDomain = Math.max(20, playersByDomain.keySet().stream().map(String::length).max(Integer::compareTo).orElse(0));

        Map<String, Component> playerLists = playersByDomain.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e ->
                miniMessage.deserialize(
                        playersByDomain.get(e.getKey())
                                .stream()
                                .map(p -> String.format("<green>%s", p.player().getName()))
                                .collect(Collectors.joining("<gold>, ")))));

        playersByDomain.entrySet()
                .stream()
                .map(e -> miniMessage.deserialize(String.format("<green>%s <gold>| <green>%d <gold>Players",
                                fillWithSpaces(e.getKey(), longestDomain), e.getValue().size()))
                                     .clickEvent(ClickEvent.callback(audience ->
                                             audience.sendMessage(playerLists.get(e.getKey()))))
                                     .hoverEvent(HoverEvent.showText(playerLists.get(e.getKey()))))
                .forEach(lines::add);

        Component component = Component.empty();
        for (int i = 0; i < lines.size(); i++) {
            component = component.append(lines.get(i));
            if (i < lines.size() - 1) {
                component = component.append(Component.newline());
            }
        }
        commandSender.sendMessage(component);
    }

    private String fillWithSpaces(String text, int length) {
        int spaces = length - text.length();
        return text + " ".repeat(spaces);
    }

    @SuppressWarnings("UnstableApiUsage")
    private void pluginInfo(CommandSender commandSender) {
        PluginMeta pluginMeta = plugin.getPluginMeta();
        String miniMsg = String.format(
                "<gold><bold>%s</bold> - Plugin Information<newline>" +
                "<gold>Plugin-Version: </gold><green>%s<newline>" +
                "<gold>Authors: </gold><green>%s<newline>",
                pluginMeta.getName(),
                pluginMeta.getVersion(),
                String.join(", ", pluginMeta.getAuthors()));
        commandSender.sendMessage(MiniMessage.miniMessage().deserialize(miniMsg));
    }
}
