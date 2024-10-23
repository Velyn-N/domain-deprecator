package me.velyn.domain.dotd.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
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

    @Override
    public String toString() {
        return "MotdAction{" +
                "motd=" + PlainTextComponentSerializer.plainText().serialize(motd) +
                '}';
    }
}
