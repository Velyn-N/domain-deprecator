package de.nmadev.domain.deprecator.actions;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Optional;

public final class JoinMessageAction implements DomainAction {

    private Component text;
    private long delayTicks;

    public Component getText() {
        return text;
    }

    public long getDelayTicks() {
        return delayTicks;
    }

    @Override
    public Optional<DomainAction> readFromConfig(ConfigurationSection config) {
        ConfigurationSection joinMsgSection = config.getConfigurationSection("joinmessage");
        if (joinMsgSection == null) {
            return Optional.empty();
        }
        String text = joinMsgSection.getString("text");
        if (text == null) {
            return Optional.empty();
        }
        this.text = MiniMessage.miniMessage().deserialize(text);
        this.delayTicks = joinMsgSection.getLong("delay") * 20;
        return Optional.of(this);
    }
}
