package de.nmadev.domain.deprecator.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

public final class MotdAction implements DomainAction {

    private Component motd;

    public Component getMotd() {
        return motd;
    }

    @Override
    public Optional<DomainAction> readFromConfig(ConfigurationSection config) {
        String motd = config.getString("motd");
        if (motd != null) {
            this.motd = MiniMessage.miniMessage().deserialize(motd);
            return Optional.of(this);
        }
        return Optional.empty();
    }
}
