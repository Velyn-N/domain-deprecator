package de.nmadev.domain.deprecator;

import org.bukkit.Bukkit;

public class Scheduler {

    private final DomainDeprecator plugin;

    public Scheduler(DomainDeprecator plugin) {
        this.plugin = plugin;
    }

    public void runAsyncLater(Runnable runnable, long ticks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, ticks);
    }
}
