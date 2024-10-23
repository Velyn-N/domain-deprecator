package me.velyn.domain.dotd.listener;

import me.velyn.domain.dotd.PlayerDomainCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final PlayerDomainCache playerDomainCache;

    public QuitListener(PlayerDomainCache playerDomainCache) {
        this.playerDomainCache = playerDomainCache;
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        playerDomainCache.remove(event.getPlayer());
    }
}
