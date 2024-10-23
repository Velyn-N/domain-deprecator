package me.velyn.domain.dotd;

import org.bukkit.Bukkit;

public class Scheduler {

    private final DotDMain plugin;

    public Scheduler(DotDMain plugin) {
        this.plugin = plugin;
    }

    public void runAsyncLater(Runnable runnable, long ticks) {
        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, runnable, ticks);
    }
}
