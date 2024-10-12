package de.nmadev.domain.deprecator.listener;

import de.nmadev.domain.deprecator.ConfigManager;
import de.nmadev.domain.deprecator.Scheduler;
import de.nmadev.domain.deprecator.actions.JoinMessageAction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetSocketAddress;
import java.util.Optional;

public class JoinListener implements Listener {

    private final ConfigManager configManager;
    private final Scheduler scheduler;

    public JoinListener(ConfigManager configManager, Scheduler scheduler) {
        this.configManager = configManager;
        this.scheduler = scheduler;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        InetSocketAddress socketAddress = player.getAddress();
        if (socketAddress == null) {
            return;
        }
        Optional<JoinMessageAction> actionOpt = configManager.getDomainAction(socketAddress.getHostName(), JoinMessageAction.class);
        if (actionOpt.isEmpty()) {
            return;
        }
        JoinMessageAction action = actionOpt.get();
        if (action.getDelayTicks() <= 0) {
            sendMsg(player, action);
        } else {
            scheduler.runAsyncLater(() -> sendMsg(player, action), action.getDelayTicks());
        }
    }

    private static void sendMsg(Player player, JoinMessageAction action) {
        player.sendMessage(action.getText());
    }
}
