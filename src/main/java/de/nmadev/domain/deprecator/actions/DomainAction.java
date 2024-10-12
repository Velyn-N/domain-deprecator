package de.nmadev.domain.switcher.actions;

import org.bukkit.configuration.ConfigurationSection;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public sealed interface DomainAction permits MotdAction, JoinMessageAction {

    Optional<DomainAction> readFromConfig(ConfigurationSection config);

    static List<DomainAction> parseConfig(ConfigurationSection config) {
        return Stream.of(
                new MotdAction(),
                new JoinMessageAction()
               ).map(impl -> impl.readFromConfig(config))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}
