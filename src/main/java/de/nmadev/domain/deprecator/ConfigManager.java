package de.nmadev.domain.deprecator;

import de.nmadev.domain.deprecator.actions.DomainAction;
import org.bukkit.configuration.ConfigurationSection;

import java.util.*;
import java.util.logging.Logger;

public class ConfigManager {
    private static final String DEFAULT_DOMAIN = "default";

    private final Logger log;

    private final Map<String, List<DomainAction>> domainActions;

    public ConfigManager(Logger logger) {
        this.log = logger;
        domainActions = new HashMap<>();
    }

    public void applyConfigValues(ConfigurationSection config) {
        Set<String> domains = config.getKeys(false);
        domains.add(DEFAULT_DOMAIN);
        for (String domain : domains) {
            ConfigurationSection domainConfig = config.getConfigurationSection(domain);
            if (domainConfig == null) {
                log.info(String.format("Domain %s appears to be empty", domain));
                continue;
            }
            List<DomainAction> actions = DomainAction.parseConfig(domainConfig);
            if (actions.isEmpty()) {
                log.info(String.format("Domain %s appears to contain no valid Actions!", domain));
                continue;
            }
            domainActions.put(domain, actions);
        }
    }

    public List<DomainAction> getDomainActions(String domain) {
        return domainActions.getOrDefault(domain, domainActions.getOrDefault(DEFAULT_DOMAIN, new ArrayList<>()));
    }

    public <T> Optional<T> getDomainAction(String domain, Class<T> type) {
        return getDomainActions(domain)
                .stream()
                .filter(type::isInstance)
                .map(type::cast)
                .findAny();
    }
}
