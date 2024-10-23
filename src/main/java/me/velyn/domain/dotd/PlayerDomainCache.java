package me.velyn.domain.dotd;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PlayerDomainCache {

    private final Map<Player, String> cache = new HashMap<>();

    public void add(Player player, String hostName) {
        if (player == null || hostName == null || hostName.isEmpty()) {
            return;
        }
        cache.put(player, hostName);
    }

    public Optional<String> get(Player player) {
        if (player == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(cache.get(player));
    }

    public List<PlayerDomainMapping> getCachedValues() {
        return cache.entrySet()
                    .stream()
                    .map(e -> new PlayerDomainMapping(e.getKey(), e.getValue()))
                    .toList();
    }

    public void remove(Player player) {
        if (player != null) {
            cache.remove(player);
        }
    }

    public record PlayerDomainMapping(Player player, String hostName) {}
}
