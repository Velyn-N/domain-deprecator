package de.nmadev.domain.switcher;

import de.nmadev.domain.switcher.command.DomainSwitcherCommand;
import de.nmadev.domain.switcher.listener.JoinListener;
import de.nmadev.domain.switcher.listener.MotdListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class DomainDeprecator extends JavaPlugin {

    private Logger log;

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        super.onEnable();
        log = getLogger();

        saveDefaultConfig();

        configManager = new ConfigManager(log);
        configManager.applyConfigValues(getConfig());

        Scheduler scheduler = new Scheduler(this);

        getServer().getPluginManager().registerEvents(new MotdListener(configManager), this);
        getServer().getPluginManager().registerEvents(new JoinListener(configManager, scheduler), this);

        getServer().getCommandMap().register(this.getName(), new DomainSwitcherCommand(this));
    }

    public void reload() {
        log.info("Reloading...");
        reloadConfig();
        configManager.applyConfigValues(getConfig());
        log.info("Reloaded!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}
