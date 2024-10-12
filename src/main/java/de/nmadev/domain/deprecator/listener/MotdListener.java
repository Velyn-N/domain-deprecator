package de.nmadev.domain.deprecator.listener;

import de.nmadev.domain.deprecator.ConfigManager;
import de.nmadev.domain.deprecator.actions.MotdAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdListener implements Listener {

    private final ConfigManager configManager;

    public MotdListener(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onMOTDRequest(ServerListPingEvent event) {
        configManager.getDomainAction(event.getAddress().getHostName(), MotdAction.class)
                     .ifPresent(motdAction -> event.motd(motdAction.getMotd()));
    }
}
